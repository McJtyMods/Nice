# Nice

Nice Innovative Creative Enhancements — decorative blocks for Minecraft **26.2** with **NeoForge 26.2.0.75 or newer**. Requires **Java 25** and **McJtyLib 26.2-10.0.0.2 or newer**.

Build with `./gradlew build`. The mod jar is written to `build/libs/nice-26.2-6.0.0.jar`.

The build uses the shared `gradletools.gradle` from the parent workspace when present, otherwise from the MultiWorkspace `26.2_neo` branch. McJtyLib supplies the base blocks, block items, and block entities. Existing registry names, block states, and particle save data are preserved.

Use `./gradlew runClient` or `./gradlew runServer` for development. Regenerate resources with `./gradlew runServerData` or `./gradlew runClientData`; both generate the complete resource set, including the 26.2 item definitions, material translucency, recipes, tags, and loot tables.

Run the in-world migration checks with `./gradlew -PportTests runGameTestServer`. They cover all block families, cylinder directions, particle interactions, visibility/collision, recoloring, save/load round trips, loading the pre-McJtyLib 26.2 save format, and McJtyLib client synchronization. Test classes are excluded from normal builds.

For the optional client rendering check, copy the generated test world from `runs/gameTestServer/gametestserver/gametestworld` to `runs/client/saves/nice-port-smoke`, then run `./gradlew -PportTests -PclientSmoke runClient`. This places a temporary test scene in that world, resolves all 96 inventory models, saves `runs/client/screenshots/nice-port-smoke.png`, and exits automatically. On a fresh client, complete the accessibility onboarding before running it.

Client settings are available in `config/nice-client.toml`: particle render distance and red, green, and blue brightness.
