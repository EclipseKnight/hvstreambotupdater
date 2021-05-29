# hvstreambotupdater
Hunsterverse Stream Bot Updater

## Requirements
- jdk 15 or openjdk 15 

## Usage

```bash
java -jar hvstreambotupdater.jar --update
```
fetches and overwrites current jar

```bash
java -jar hvstreambotupdater.jar --update-run
```
fetches and then will run the new bot in a new terminal. (inconsistent depending on OS)


The hvstreambot has a command which will let it utilize this updater and update itself then run again. 
Currently, due to some issues between different OS the command is disabled until a resolution is found.
However, this can still be used to fetch the latest release.
