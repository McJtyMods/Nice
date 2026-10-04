# Nice

Nice Innovative Creative Enhancements — decorative blocks for Minecraft **26.2** with **Fabric Loader 0.19.3 or newer**. Requires **Java 25**, **Fabric API 0.155.2+26.2 or newer**, and **Forge Config API Port 26.2.1 or newer**.

Build with `./gradlew build`. The installable mod jar is `build/libs/nice-fabric-26.2-6.0.0-fabric.jar`. Install it alongside Fabric API and Forge Config API Port.

Use `./gradlew runClient` or `./gradlew runServer` for development. Regenerate the complete resource set with `./gradlew runDatagen`: models, 26.2 item definitions, translucent materials, recipes, tags, language, and loot tables. The Fabric model loader uses the original cylinder OBJ geometry for both blocks and inventory items.

The port retains all 96 block/item IDs and the block entity's particle and visibility data. McJtyLib is not required. Forge Config API Port preserves `config/nice-client.toml`, including particle render distance and RGB brightness settings.

Run the in-world checks with `./gradlew -PportTests runGameTestServer`. They cover all block families, cylinder directions, particle interactions, visibility/collision, recoloring, and save/load round trips. Test classes and entrypoints are excluded from normal builds.

For the optional client rendering check, copy `runs/gameTestServer/world` to `runs/client/saves/nice-port-smoke`, then run `./gradlew -PportTests -PclientSmoke runClient`. This places a test scene in that disposable world, checks all 96 inventory models and 384 oriented cylinder models, saves `runs/client/screenshots/nice-port-smoke.png`, and exits automatically. Complete accessibility onboarding first on a fresh client. The scene replaces blocks near the origin of this test world.

Publication tasks retain the existing CurseForge and Modrinth project settings and declare Fabric API and Forge Config API Port as required dependencies.
