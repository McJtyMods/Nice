# Nice

Nice Innovative Creative Enhancements — decorative blocks for Minecraft **26.2** with **NeoForge 26.2.0.75 or newer**. Requires **Java 25**.

Build with `./gradlew build`. The mod jar is written to `build/libs/nice-26.2-6.0.0.jar`.

The project includes a local `gradletools.gradle`, copied from the Lost Cities 26.2 project. McJtyLib is no longer required; block registration, tooltips, configuration, data generation, and rendering use Minecraft/NeoForge APIs directly.

Use `./gradlew runClient` or `./gradlew runServer` for development. Regenerate resources with `./gradlew runServerData` or `./gradlew runClientData`; both generate the complete resource set, including the 26.2 item definitions, material translucency, recipes, tags, and loot tables.

Run the in-world migration checks with `./gradlew -PportTests runGameTestServer`. They cover all block families, cylinder directions, particle interactions, visibility/collision, recoloring, and save/load round trips. Test classes are excluded from normal builds.

For the optional client rendering check, copy the generated test world from `runs/gameTestServer/gametestserver/gametestworld` to `runs/client/saves/nice-port-smoke`, then run `./gradlew -PportTests -PclientSmoke runClient`. This places a temporary test scene in that world, resolves all 96 inventory models, saves `runs/client/screenshots/nice-port-smoke.png`, and exits automatically. On a fresh client, complete the accessibility onboarding before running it.

Client settings are available in `config/nice-client.toml`: particle render distance and red, green, and blue brightness.
