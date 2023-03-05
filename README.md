# Minesweeper

Using a Model–view–controller (MVC) in Java 17.

[toc]

## Guide to configure the basics

To facilitate the debugging of the project, we will include a series of dependencies.

### ¿Why?

Not always we want to see the `system.print.err` in the console.

¿What about saving the information?

For example:

```java
System.err.println("This is an error");
```

Would remain in the console, but we would not have a record of it.

Also, would be bad if in release mode we show this to the user.

That why we will use a library called `log4j2`.

Also the log4j2 is a library that allows us to save the information in different ways and facilitate us the debugging
process.

```java
23:12:37.556[main]INFO daw.pr.minesweeper.App-Debug mode enabled
```

As you can see, right now we print with a level control, also we know where the call was made and so on.

### Setup with Maven

Once we created our basic project, we will add the dependency to the `pom.xml` file.

```xml

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.20.0</version>
</dependency>
<dependency>
<groupId>org.apache.logging.log4j</groupId>
<artifactId>log4j-core</artifactId>
<version>2.20.0</version>
</dependency>
```

> Be aware, that versions bump from time to time, so you should check the latest version.
> <https://mvnrepository.com/artifact/org.apache.logging.log4j>

### Create a basic configuration file

We will create a file called `log4j2.xml` in the `src/main/resources` folder.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration status="INFO">
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
        </Console>
        <File
            name="FileAppender"
            fileName="application-${date:yyyyMMdd}.log"
            immediateFlush="false"
            append="true"
        >
            <PatternLayout
                pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"
            />
        </File>
    </Appenders>
    <Loggers>
        <Root level="error">
            <AppenderRef ref="ConsoleAppender" />
            <AppenderRef ref="FileAppender" />
        </Root>
    </Loggers>
</Configuration>
```

Just to explain a little:

-   We have two appenders, one for the console and one for the file.
-   We have a logger, that will be the root logger, we will change this for debug in the classes with arguments, that my
    approach.
-   The root logger will have the level `error` and will use the two appenders.
-   The pattern layout is the format of the log.
-   The file name is the name of the file, and the date format is the date format of the file.
-   The immediate flush is to flush the file immediately, and to append is to append the file or not.

### Configure the logger to work in release mode or debug mode

We need to read the arguments as we launch it.

```java
if(args.length>0){
        if(args[0].equals("--debug")||args[0].equals("-d")){
          Configurator.setRootLevel(Level.DEBUG);
          logger.info("Debug mode enabled");
        } else {
          logger.error("Invalid argument: "+args[0]);
          logger.error("Usage: java -jar minesweeper.jar [--debug|-d]");
          System.exit(1);
        }} else {
          logger.info("Release mode enabled");
          Configurator.setRootLevel(Level.ERROR);
        }
```

I'm using the standard _UNIX_ arguments, but you can use whatever you want.

> At the same time, please follow POSIX standards... don't be a d...

### Use the logger

In each class, we need to declare a funtion to get the logger.

**We need to do this in each class and call for the desire main class to print it!.**

Not always is the content root class the one that we want to print.

```java
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Using logger in the Main class");
    }
}

```

## Configuring prettier for code style improvements

We are going to use [prettier](https://prettier.io/) to improve the code style across the project.

### Configuring prettier with Maven

We need to add the dependency to the `pom.xml` file at the plugins section.

```xml
<plugin>
    <groupId>com.hubspot.maven.plugins</groupId>
    <artifactId>prettier-maven-plugin</artifactId>
    <!-- Find the latest version at https://github.com/jhipster/prettier-java/releases -->
    <version>2.0</version>
</plugin>
```

#### In case npm is not installed properly

You need to install npm and nodejs, and then run the following command:

```bash
 npm install --save-dev --save-exact prettier
 npm install --save-dev --save-exact prettier-plugin-java
```

### Create a basic config for prettier

You need to create a file called `.prettierrc` in the root of the project.

This will be used to configure the code style.

```json
{
    "printWidth": 120,
    "tabWidth": 4,
    "useTabs": false,
    "semi": true,
    "singleQuote": true,
    "trailingComma": "all",
    "bracketSpacing": true,
    "arrowParens": "always",
    "overrides": [
        {
            "files": "*.java",
            "options": {
                "parser": "java"
            }
        }
    ]
}
```

Also, you can create a file called `.prettierignore` in the root of the project.

## Git

As we are using some crap that we don't want on git, dont forget to exclude it.

```ignore
# At least ignore this folders
target
log
node_modules
```

> Plase, DO NOT IGNORE ALL `.idea` folder as is designed to be shared and could be helpful for other developers.

You can create a `.gitignore` more specific for your project with this tool:

[gitignore.io - Create useful .gitignore files for your project
](https://www.toptal.com/developers/gitignore)
