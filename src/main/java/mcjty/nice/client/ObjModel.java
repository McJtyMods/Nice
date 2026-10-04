package mcjty.nice.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.model.loading.v1.UnbakedModelDeserializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.cuboid.CuboidModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Loads NICE's quad-only OBJ meshes, retaining the original positions, winding and UVs. */
public final class ObjModel {
    public static void register() {
        UnbakedModelDeserializer.register(Identifier.fromNamespaceAndPath("nice", "obj"), ObjModel::deserialize);
    }

    private static UnbakedModel deserialize(JsonObject json, JsonDeserializationContext context) {
        Identifier path = Identifier.parse(json.get("model").getAsString());
        boolean flipV = json.has("flip_v") && json.get("flip_v").getAsBoolean();
        // Vanilla handles texture slots, parent inheritance and inventory transforms.
        CuboidModel base = context.deserialize(json, CuboidModel.class);
        return new CuboidModel((textures, baker, state, debugName) -> {
            var material = baker.materials().resolveSlot(textures, "buis", debugName);
            var sprite = material.sprite();
            var info = BakedQuad.MaterialInfo.of(material, sprite.transparency(), -1, true, 0);
            var result = new QuadCollection.Builder();
            for (Face face : read(path)) {
                Vector3f[] positions = new Vector3f[4];
                long[] uvs = new long[4];
                for (int i = 0; i < 4; i++) {
                    // Blockstate rotations are around the center of the block.
                    positions[i] = new Vector3f(face.positions.get(i)).sub(.5f, .5f, .5f);
                    state.transformation().getMatrix().transformPosition(positions[i]);
                    positions[i].add(.5f, .5f, .5f);
                    var uv = face.uvs.get(i);
                    uvs[i] = UVPair.pack(sprite.getU(uv.x), sprite.getV(flipV ? 1 - uv.y : uv.y));
                }
                Vector3f normal = new Vector3f(positions[1]).sub(positions[0])
                        .cross(new Vector3f(positions[2]).sub(positions[0])).normalize();
                Direction direction = Direction.getApproximateNearest(normal.x, normal.y, normal.z);
                result.addUnculledFace(new BakedQuad(positions[0], positions[1], positions[2], positions[3],
                        uvs[0], uvs[1], uvs[2], uvs[3], direction, info));
            }
            return result.build();
        }, base.guiLight(), base.ambientOcclusion(), base.transforms(), base.textureSlots(), base.parent());
    }

    private record Face(List<Vector3f> positions, List<Vector2f> uvs) {}

    private static List<Face> read(Identifier path) {
        List<Vector3f> positions = new ArrayList<>();
        List<Vector2f> uvs = new ArrayList<>();
        List<Face> faces = new ArrayList<>();
        try (BufferedReader reader = Minecraft.getInstance().getResourceManager().getResourceOrThrow(path).openAsReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                switch (parts[0]) {
                    case "v" -> positions.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
                    case "vt" -> uvs.add(new Vector2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
                    case "f" -> {
                        if (parts.length != 5) throw new IOException("NICE OBJ requires quad faces: " + path);
                        List<Vector3f> facePositions = new ArrayList<>(4);
                        List<Vector2f> faceUvs = new ArrayList<>(4);
                        for (int i = 1; i <= 4; i++) {
                            String[] indices = parts[i].split("/");
                            facePositions.add(positions.get(Integer.parseInt(indices[0]) - 1));
                            faceUvs.add(uvs.get(Integer.parseInt(indices[1]) - 1));
                        }
                        faces.add(new Face(facePositions, faceUvs));
                    }
                    default -> { /* Normals and MTL names are replaced by the model's texture slot. */ }
                }
            }
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Cannot load NICE OBJ " + path, e);
        }
        return faces;
    }
}
