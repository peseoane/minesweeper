# Configuring prettier for code style improvements

We are going to use [prettier](https://prettier.io/) to improve the code style across the project.

## Configuring prettier with Maven

We need to add the dependency to the `pom.xml` file at the plugins section.

```xml
<plugin>
    <groupId>com.hubspot.maven.plugins</groupId>
    <artifactId>prettier-maven-plugin</artifactId>
    <!-- Find the latest version at https://github.com/jhipster/prettier-java/releases -->
    <version>2.0</version>
</plugin>
```

### In case npm is not installed properly

You need to install npm and nodejs, and then run the following command:

```bash
 npm install --save-dev --save-exact prettier
 npm install --save-dev --save-exact prettier-plugin-java
```

## Create a basic config for prettier

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