# Amnesia

## Graphic Interface

Constructed with [libGDX](https://libgdx.com/) opensource framework.

## Project Architecture

Defined by libGDX pattern, in game creation phase:

```
settings.gradle            <- definition of sub-modules. In this caso, we have just: desktop
build.gradle               <- main Gradle build file, defines dependencies and plugins
gradlew                    <- local Gradle wrapper
gradlew.bat                <- script that will run Gradle on Windows
gradle                     <- script that will run Gradle on Unix systems

assets/                    <- contains graphics, images, audio, etc.

core/
    build.gradle           <- Gradle build file for core project
    src/                   <- Source folder for all game's code

desktop/
    build.gradle           <- Gradle build file for desktop project
    src/                   <- Source folder for desktop project, contains LWJGL launcher class
```
