
Displace's Template Mod Package
====

This is specifically a template designed around data generation, and various other more tedious things that I would
normally have to do over and over again, but apparently I'm now a "programmer", and so I am going to try and 
use good design principles and focus on building decent code, rather than creating insanely un-optimized slop.

The hope with this is that I can use the template when working on some other, far more major mod ideas, but still
build a good foundation while working on lower end / tiered mods. That being said, when changing this to be the mod name
a few things must be done.

1. Change the name of the base folder, that way the name matches. `net.displace.*insert_mod_name_here*`
2. The main mod class must also be properly named. `TemplateMod` ➡️ `ProgressionalCopper` or something like that.
3. The client needs to match the changed information, so the name should also be changed `TemplateModClient`
4. Change the MOD_ID variable in the main mod class. Make it unique, the last thing you want is a clash with other mod ids.
5. Modify the `gradle.properties` file. Change it to fit the current mod information.

_Additional & Useful Information_
====
- When working on Mod Compatibility, or some kind of equal task, adding mods for testing should be done within the `run` folder.
This is where you will add the extra mods.
- To update the version of neoforge that is running, change `gradle.properties` and check the neoforge site for the latest version.
For this case, the `neo_version` variable should be changed to the latest version. (for me, `21.11.6-beta`)

Installation information
=======

This template repository can be directly cloned to get you started with a new
mod. Simply create a new repository cloned from this one, by following the
instructions provided by [GitHub](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template).

Once you have your clone, simply open the repository in the IDE of your choice. The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
============
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

Additional Resources: 
==========
Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/
