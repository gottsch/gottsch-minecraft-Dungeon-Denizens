# Changelog for Dungeon Denizens 1.20.1
<img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/ddenizens_curseforge_logo.png" width="128px">

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.6.0] - 2026-06-28

### Added

- 🗿 **Gargoyle** — a winged stone monster that stalks you on foot, then launches into the air to close the distance, swoops in, drops down to land, and strikes. If it loses sight of you while airborne it settles safely back to the ground on its own. It shrugs off poison and can't be slowed to a crawl.
- 🦇 **Margoyle** — a cave-dwelling cousin of the Gargoyle. It can't truly fly, so it skims low along the ground as it hunts. It lurks only in the dark underground — caves, sewers, and dungeons below sea level — and never roams the surface. You'll know it by its four horns and short wings, which beat as it hovers.
- 🪨 **Rock-throwing Orcs** — some orcs now turn up empty-handed and hurl rocks at you in an arc, backing away to keep their distance instead of charging in. Roughly 1 in 7 by default, and adjustable in the settings.

### Changed

- Rebalanced the **Orc** — it hits harder, now knocks you back a little, and wears some armor, trading a bit of its health for the extra protection.
- Eased off the **Headless** a little — it gives up the chase a bit sooner and moves slightly slower, so it's not quite as relentless as before.

### Fixed

- Fixed a crash that could happen when you hit a **Shadow** or **Shadowlord** with a gold sword, Shadow Blade, or Shadow Falchion while you had the Weakness effect. Those weapons now also correctly ignore Weakness against these foes, so they land their full damage as intended.
- **Skeleton Warriors** now turn up carrying worn, battle-used weapons and armor, the way they were always meant to, instead of perfectly fresh gear.
- Fixed several **settings that were quietly being ignored**: the Magma Skeleton's options were tangled up with the Iron Skeleton's, and the Firespout spell's maximum height — along with the Beholder's and Death Tyrant's daemon-summoning timers — weren't using the values you set.

---

## [1.5.0] - 2024-12-14

### Added

- On/off **despawn settings** for the Daemon, Beholder, Death Tyrant, Shadowlord, and Boulder (all set to despawn by default).

### Changed

- The Patchouli guide book is no longer dropped into your inventory the first time you join a world — you can still grab the **DD Bestiary** any time from the Creative tab.
- Monsters that used to linger forever now despawn like normal mobs by default. Want to keep one around? Flip its despawn setting.

---

## [1.4.2] - 2024-09-12

### Fixed

- The **Daemon's** summoned-helper lifespan setting now works as intended.
- Fixed a startup error caused by reading settings before they had finished loading.

### Changed

- Tidied up the settings behind the scenes — moved the logging options into the shared "common" config and removed an unused Treasure2 integration check.

---

## [1.4.1] - 2024-02 — Skeleton Update

### Added

- Optional built-in **Treasure2** support — Dungeon Denizens items can be mixed into Treasure2's loot.

### Fixed

- The **Winged Skeleton** now spawns properly.
- Fixed a crash when opening the **Death Tyrant's** page in the Bestiary.

---

## [1.4.0] - 2024-02 — Skeleton Update

### Added

- Four new undead: the **Winged Skeleton**, **Fossilized Skeleton**, **Iron Skeleton**, and **Magma Skeleton**.

### Changed

- The **Ghoul** no longer doubles up on its avoid-the-sun behaviour (removed a duplicate).
- Tightened the **Orc's** hitbox so it better matches its body.
- Updated the DD Bestiary.
- Behind the scenes: added shared attack behaviours that drive the new skeletons' melee and bow attacks.

---

## [1.3.0] - 2024-01-31 — Shadowlord Update

### Added

- New **Shadow Blade** and **Shadow Falchion** weapons. **Shadowlords** now wield a Shadow Blade and **Shadows** a Shadow Falchion, and both have a high chance of dropping their weapon when defeated.
- Ambient sounds for the Shadow, plus ambient and walking sounds for the Shadowlord.
- A cooldown on the Shadowlord's **Drain** spell, and new settings for the Shadowlord's and Shadow's abilities.

### Changed

- A brand-new **Shadowlord** model and a refreshed **Shadow** model.
- Reworked how the Shadowlord and Shadow take damage: they're vulnerable to **gold** weapons — and to a **Shadow Blade**, which hurts them as much as a Netherite sword would — and shrug off everything else.
- Toned down the Shadow's **Aura of Blindness** (shorter by default, with a lower chance to blind) and made the Shadow easier to knock back.
- Shortened the Poison effect's duration.
- Added the new mobs to the Bestiary.

### Fixed

- Stopped the log spam when a Shadowlord summons monsters.

---

## [1.2.0] - 2024-01-09 — Beholderkin Update

### Added

- The **Beholder**.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/beholder-plaque.png" width="128px">

- The **Death Tyrant**.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/death-tyrant-plaque.png" width="128px">

- The **Spectator**.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/spectator-plaque.png" width="128px">

- The **Skeleton Warrior**.
- New **Disintegrate** and **Disarm** spells.
- Glowing eyes for the **Daemon** and **Shadow** — they now light up in the dark.
- Custom sounds for the Daemon and the beholderkin.
- An "enable" on/off setting for every mob.
- **Patchouli** support — a DD Bestiary book featuring the new mobs (older mobs not fully covered yet), plus brief tooltips on the mob spawn eggs.
- **Biomes O' Plenty** support.
- Summoned Daemons now remember their owner and have a lifespan.

### Changed

- Reworked and rebalanced the **Gazer** (now the lesser beholderkin).
- Nether monsters now spawn only in the nether wastes, and all nether-spawning monsters appear less often.
- The **Daemon** got an updated jaw and horns, a mouth-bob animation, and slightly lower speed and knockback.
- The Daemon and Shadowlord now despawn.
- Behind the scenes: tidied the spawn-rule and spell code and cleaned up some setting labels.

### Fixed

- Fixed a **Gazer** rendering glitch.
- Fixed a spawn-settings mix-up where the spawn weight and the group-size (minimum/maximum) values were swapped.

---

## [1.1.0] - 2023-10-18

### Changed

- Monsters now require proper darkness to spawn, just like vanilla Minecraft.

---

## [1.0.0] - 2023-10-17

### Added

- First release — ported from the 1.19.3 version.
