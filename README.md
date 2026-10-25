# Nice

Nice Innovative Creative Enhancements — decorative blocks for Minecraft **26.3** with **NeoForge 26.3.0.83 or newer**. Requires **Java 25** and **McJtyLib 26.3-11.0.0 or newer**.

Build with `./gradlew build`. The mod jar is written to `build/libs/nice-26.3-7.0.1.jar`.

The build uses the published `MultiWorkspace/26.3_neo/gradletools.gradle`, or the root shared script in an integrated workspace. No local copy is needed. The McJtyLib Maven artifact version comes directly from the shared script; the mod dependency requirement remains `26.3-11.0.0`. McJtyLib supplies the base blocks, block items, and block entities. Existing registry names, block states, tooltips, and particle save data are preserved. The shared `top()` and `jei()` helpers also include TOP and JEI for development.

Use `./gradlew runClient` or `./gradlew runServer` for development. Regenerate resources with `./gradlew runServerData` or `./gradlew runClientData`; both generate the complete resource set, including the 26.3 item definitions, material translucency, recipes, tags, and loot tables.

Run the in-world migration checks with `./gradlew -PportTests runGameTestServer`. They cover all block families, cylinder directions, particle interactions, visibility/collision, recoloring, save/load round trips, legacy particle saves, and McJtyLib client synchronization. Test classes are excluded from normal builds.

For the optional client rendering check, copy the generated test world from `runs/gameTestServer/gametestserver/gametestworld` to `runs/client/saves/nice-port-smoke`, then run `./gradlew -PportTests -PclientSmoke runClient`. This places a temporary test scene in that world, resolves all 96 inventory models, saves `runs/client/screenshots/nice-port-smoke.png`, and exits automatically. On a fresh client, complete the accessibility onboarding before running it.

Client settings are available in `config/nice-client.toml`: particle render distance and red, green, and blue brightness.
