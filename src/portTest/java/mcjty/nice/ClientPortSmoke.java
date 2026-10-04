package mcjty.nice;

import mcjty.nice.blocks.GenericParticleTileEntity;
import mcjty.nice.particle.ParticleType;
import mcjty.nice.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.core.registries.BuiltInRegistries;

/** Optional visual smoke scene in a disposable test world. */
public final class ClientPortSmoke implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientPortSmoke::tick);
    }

    private static int ticks;

    public static void tick(Minecraft event) {
        if (!Boolean.getBoolean("nice.clientSmoke")) return;
        var minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        if (minecraft.level == null || minecraft.player == null || server == null) return;
        if (ticks > 0 && minecraft.gui.screen() != null) return;
        if (ticks++ == 0) {
            server.execute(() -> {
                var commands = server.getCommands();
                var source = server.createCommandSourceStack();
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 0; z++) server.overworld().getChunk(x, z);
                }
                commands.performPrefixedCommand(source, "time set noon");
                commands.performPrefixedCommand(source, "fill -3 3 -3 20 3 15 minecraft:stone");
                commands.performPrefixedCommand(source, "gamemode creative @a");
                commands.performPrefixedCommand(source, "tp @a 8 6 12 180 15");
                var families = java.util.List.of(Registration.SOLID_BLOCKS, Registration.PARTICLE_BLOCKS, Registration.CYLINDERS,
                        Registration.SMALL_CYLINDERS, Registration.SOLID_CYLINDERS, Registration.SOLID_SMALL_CYLINDERS);
                for (int f = 0; f < families.size(); f++) {
                    for (int d = 0; d < Direction.values().length; d++) {
                        BlockPos pos = new BlockPos(f * 3, 4, d);
                        var block = families.get(f).get(DyeColor.values()[(f * 6 + d) % 16]).get();
                        server.overworld().setBlock(pos, block.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.values()[d]), 3);
                        if (server.overworld().getBlockEntity(pos) instanceof GenericParticleTileEntity entity) {
                            entity.setType(ParticleType.values()[d % 5]);
                        }
                    }
                }
                for (var player : server.getPlayerList().getPlayers()) {
                    int slot = 0;
                    for (var items : java.util.List.of(Registration.SOLID_BLOCK_ITEMS, Registration.PARTICLE_BLOCK_ITEMS,
                            Registration.CYLINDER_ITEMS, Registration.SMALL_CYLINDER_ITEMS,
                            Registration.SOLID_CYLINDER_ITEMS, Registration.SOLID_SMALL_CYLINDER_ITEMS)) {
                        player.getInventory().setItem(slot++, new ItemStack(items.get(DyeColor.RED).get()));
                    }
                }
            });
        }
        if (ticks == 180) {
            int count = 0;
            for (var family : java.util.List.of(Registration.SOLID_BLOCK_ITEMS, Registration.PARTICLE_BLOCK_ITEMS,
                    Registration.CYLINDER_ITEMS, Registration.SMALL_CYLINDER_ITEMS,
                    Registration.SOLID_CYLINDER_ITEMS, Registration.SOLID_SMALL_CYLINDER_ITEMS)) {
                for (var item : family.values()) {
                    var state = new net.minecraft.client.renderer.item.ItemStackRenderState();
                    minecraft.getItemModelResolver().updateForTopItem(state, new ItemStack(item.get()),
                            net.minecraft.world.item.ItemDisplayContext.GUI, minecraft.level, minecraft.player, 0);
                    if (state.isEmpty()) throw new IllegalStateException("Missing item model: " + BuiltInRegistries.ITEM.getKey(item.get()));
                    count++;
                }
            }
            int checked = 0;
            for (var family : java.util.List.of(Registration.CYLINDERS, Registration.SMALL_CYLINDERS,
                    Registration.SOLID_CYLINDERS, Registration.SOLID_SMALL_CYLINDERS)) {
                for (var block : family.values()) {
                    for (Direction facing : Direction.values()) {
                        var blockState = block.get().defaultBlockState().setValue(BlockStateProperties.FACING, facing);
                        var parts = new java.util.ArrayList<net.minecraft.client.renderer.block.dispatch.BlockStateModelPart>();
                        minecraft.getModelManager().getBlockStateModelSet().get(blockState)
                                .collectParts(net.minecraft.util.RandomSource.create(0), parts);
                        int quads = parts.stream().mapToInt(part -> part.getQuads(null).size()).sum();
                        if (quads != 16) throw new IllegalStateException("Expected 16 OBJ quads for " + blockState + ", got " + quads);
                        for (var part : parts) {
                            for (var quad : part.getQuads(null)) {
                                if (!quad.materialInfo().sprite().contents().name().getNamespace().equals("nice"))
                                    throw new IllegalStateException("Missing cylinder texture: " + blockState);
                            }
                        }
                        checked++;
                    }
                }
            }
            System.out.println("NICE CLIENT SMOKE: verified " + checked + " oriented cylinder models");
            System.out.println("NICE CLIENT SMOKE: resolved " + count + " inventory models");
            net.minecraft.client.Screenshot.grab(minecraft.gameDirectory, "nice-port-smoke.png",
                    minecraft.gameRenderer.mainRenderTarget(), 1, message -> System.out.println(message.getString()));
        }
        if (ticks == 240) {
            System.out.println("NICE CLIENT SMOKE: rendered scene for 240 ticks");
            minecraft.stop();
        }
    }
}
