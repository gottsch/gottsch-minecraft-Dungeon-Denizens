# Changelog for Dungeon Denizens 1.20.1
<img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/ddenizens_curseforge_logo.png" width="128px">

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.6.0] - 2025-07-16

### Changed
- Tweaked Orc attributes - Attack = 3.25 (zombie = 3), added slight Knockback = 0.2, added Armor =2 (zombie = 2), lowered health = 25 (zombie = 20)


## [1.5.0] - 2024-12-14

### Changed

- Removed Patchouli book added to inventory on first join. It is still available in the Creative DD tab.
- Mobs that didn't despawn now despawn by default but can be set with config option.

### Added

- Config options for despawning for Daemon, Beholder, Death Tyrant, Shadowlord, Boulder. Default = true.

## [1.4.2] - 2024-09-12

### Changed

- Fixed Daemon's summoned lifespan config option.
- Moved logging config settings to the -common config.
- Fixed accessing config properties before config is loaded error.
- Removed check for treasure2 integrations config setting.

## [1.4.1] - 2024-02 - Skeleton Update

### Changed

- Fixed Winged Skeleton not spawning issue. 
- Fixed Death Tyrant pathouli page entry from crashing.

### Added

- Built-In optional Treasure2 integration. ie. items can be injected into Treasure2 loot.

## [1.4.0] - 2024-02 - Skeleton Update

### Changed

- Ghoul had RestrictSunGoal twice - removed duplicate.
- Increased the hit box on the Orc to match body size better.
- Updates to DD Bestiary.

### Added

- Winged Skeleton.
- Fossilized Skeleton.
- Iron Skeleton.
- Magma Skeleton.
- PassiveMeleeAttackGoal.
- VariantPoweredRangedBowAttackGoal.


## [1.3.0] - 2024-01-31 - Shadowlord Update

### Changed

- New Shadowlord model.
- Refactored Shadow model.
- Shadowlords now carry a Shadow Blade.
- Shadows now carry a Shadow Falchion.
- Shadowlords and Shadows have a high chance of dropping a Shadow Blade/Falchion.
- Reduced Aura of Blindness effect to 2 seconds duration by default.
- Reduced duration of Poison effect
- Refactored Shadowlord's and Shadow's weakness to gold weapons.
- Refactored Shadowlord's and Shadow's immunity to all other weapons.
- A Shadow Blade is now a weakness of a Shadowlord and Shadow - it does the same damage as Netherite Sword would normally do.
- Fixed spamming logs when Shadowlord summons mobs.
- Reduced Blindness duration for Shadow.
- Reduced probability for Blindness for Shadow.
- Reduced Knockback resistence for Shadow.
- Updated Bestiary book with mobs.

### Added

- Shadowlord walking and ambient sounds.
- Shadow ambient sound.
- Shadow Blade.
- Shadow Falchion.
- Config options for existing and new Shadowlord abilities.
- Config options for existing Shadow abilities.
- A cooldown time for Drain spell.


## [1.2.0] - 2024-01-09 - Beholderkin Update

### Changed

- Fixed Gazer LAYER_LOCATION
- Refactored and rebalanced Gazer (lesser beholderkin now).
- Fixed CommonSpawnConfig constructor. There was a mix-up with the weight and minSpawn, maxSpawn values.
- Fixed some config label naming.
- Refactored spawn rules classes for nether mobs.
- Renamed spell classes and resources.
- Updated Daemon's jaw and horns.
- Updated Daemon's animation to include mouth bob.
- Reduced Daemon's speed, knockback.
- Daemon and Shadowlord will now despawn;
- Nether spawnable mobs only spawn in the nether wastes
- Reduced spawn weights for all nether spawning mobs

### Added

- Beholder mob.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/beholder-plaque.png" width="128px">

- Death Tyrant mob.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/death-tyrant-plaque.png" width="128px">

- Spectator mob.

  <img src="https://github.com/gottsch/gottsch-minecraft-Dungeon-Denizens/wiki/images/spectator-plaque.png" width="128px">

- Skeleton Warrior mob.
- Disintegrate spell.
- Disarm spell.
- Daemon glowing eyes layer.
- Owner and lifespan properties to Daemon if summoned.
- Shadow glowing eyes layer.
- Mob "enable" config option.
- Supports Patchouli - DD Bestiary book contains new mobs (older mobs not complete). 
- Bare-bones tooltips on mob eggs (similar but reduced info as DD Bestiary book).
- Add custom sounds to Daemon and the beholderkin mobs.
- Supports Biomes O Plenty

## [1.1.0] - 2023-10-18

### Changed

- Changed spawn rules to require the correct darkness for mobs to spawn (like vanilla Minecraft)


## [1.0.0] - 2023-10-17

### Changed

### Added 

- Port from 1.19.3-1.0.0