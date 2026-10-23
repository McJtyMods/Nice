# Nice

Nice Innovative Creative Enhancements — decorative blocks for Minecraft **26.3** with **Fabric Loader 0.19.5 or newer**. Requires **Java 25**, **Fabric API 0.164.0+26.3 or newer**, **Forge Config API Port 26.3.1 or newer**, and **McJtyLib 11.0.0 or newer for Fabric 26.3**.

Build with `./gradlew build`. The installable mod jar is `build/libs/nice-fabric-26.3-7.0.1-fabric.jar`. Install it alongside Fabric API, Forge Config API Port, and McJtyLib. The build selects Jenkins artifact `mcjtylib-fabric-26.3-11.0.0-fabric.1.jar`; the `.1` artifact suffix does not change the 11.0.0 minimum mod requirement.

The build uses the published `26.3_fabric/gradletools.gradle` from MultiWorkSpace, or the workspace root's script when included as a subproject. No project-local copy is needed.

Use `./gradlew runClient` or `./gradlew runServer` for development. Regenerate the complete resource set with `./gradlew runDatagen`: models, 26.3 item definitions, translucent materials, recipes, tags, language, and loot tables. The Fabric model loader uses the original cylinder OBJ geometry for both blocks and inventory items.

The port retains all 96 block/item IDs and the block entity's particle and visibility data. Blocks, block items, and block entities use McJtyLib's base classes, including client synchronization of particle type and visibility. Existing saved block entity data remains compatible. Forge Config API Port preserves `config/nice-client.toml`, including particle render distance and RGB brightness settings.

Run the in-world checks with `./gradlew -PportTests runGameTestServer`. They cover all block families, cylinder directions, particle interactions, visibility/collision, recoloring, and save/load round trips. Test classes and entrypoints are excluded from normal builds.

For the optional client rendering check, copy `runs/gameTestServer/world` to `runs/client/saves/nice-port-smoke`, then run `./gradlew -PportTests -PclientSmoke runClient`. This places a test scene in that disposable world, checks all 96 inventory models and 384 oriented cylinder models, saves `runs/client/screenshots/nice-port-smoke.png`, and exits automatically. Complete accessibility onboarding first on a fresh client. The scene replaces blocks near the origin of this test world.

Publication tasks retain the existing CurseForge and Modrinth project settings and declare Fabric API, Forge Config API Port, and McJtyLib as required dependencies.
