# Roboshi

This is a Discord bot for a private server. Currently it can't do anything except /ping.

## Requirements

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Latest version of [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

## Recommended

- [Intellij](https://www.jetbrains.com/idea/)
- [Docker](https://docs.docker.com/desktop/)

## Setup

1. Clone the project.

- Open your command prompt (cmd).
- Navigate to the place where you would like to clone the project.

```
cd to/a/directory/that/you/like/
```

- Clone the project.

```
git clone git@github.com:SheepTheWeeb/Roboshi.git
```

2. Install the dependencies.

- Open your command prompt (cmd).
- Navigate to the root of the Roboshi project.
- Install the dependencies.

```
mvn clean
mvn install
```

## Running the project

- Open the project with Intellij
- Open App.java within /src/main/java/org/sheep
- Press the little green play button (it should crash the first time but create the config)
- Add the local variables as described here: https://github.com/SheepTheWeeb/Roboshi/wiki/Local-variables

The variables you need are:

```
-DdiscordBotToken=<Token>
```

- Press the green run button again

## Code quality

This project uses Sonarcloud: https://sonarcloud.io/project/overview?id=SheepTheWeeb_Roboshi

## Technologies

The technologies used for the bot are:

- Programming language : Java
- Dependency Manager : Maven

## Dependencies

Most important dependencies:

- [Java Discord API](https://github.com/DV8FromTheWorld/JDA) (JDA)

## Version control

- [Github](https://github.com/)
- [Sourcetree](https://www.sourcetreeapp.com/)

## Credits

- **Lead Sheepveloper**: Sheep
- **Pog**: Sheep's oom
