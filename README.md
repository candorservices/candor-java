# candor-java
Java Library for Candor Services' Project Mercury API.

## Documentation
You can add the library to your project with Maven:
<br>Repo:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
            <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Dependency:
```xml
<dependency>
    <groupId>com.github.discord-jar</groupId>
    <artifactId>discord.jar</artifactId>
    <version>Tag</version>
</dependency>
```
You can then get started by intializing a new instance of the `Candor` clas using your public API key found at https://candorian.app/api-dashboard.

### Verifying a license
You can verify if a license is valid using this method:
```java
boolean valid = candor.verifyLicense("LICDENSE_KEY_HERE", "PRODUCT_ID_HERE");
```

You'll want to grab your product ID from https://candorian.app/license-manager by creating a new product and then adding a license. The license key should be added by the client, using some sort of config (we recommend remote configs, see below!). Assign your license to your client using the tools in the dashboard.

### Retrieving a remote config
Remote configs allow clients to easily configure parts of their apps without having to mess with complicated or messy configuration files. You can use this function:
```java
Config config = candor.getConfig("CONFIG_ID_HERE");
```
to retrieve the remote config. You can then run actions on retrieving values in that config:
```java
String licenseKey = config.getString("license");
```

You can also retrieve nested values like this:
```java
String licenseKey = config.getString("license.key");
```
using a dot (.) to split objects, similar to how YAML in Spigot works.
<br><br>
To retrieve objects nested inside of an array, you can use:
```java
String colors = config.getString("colors.0.name");
```
with 0 being the index of the object you want to retrieve.
<br><br>
There are also tons of other cool methods in that class that could be helpful.
<br><br>

You can create a remote config at https://candorian.app/config-manager.

## Need support?
If you need help with anything to do with this project, then please see the [project-mercury-support](https://canary.discord.com/channels/650773903236399134/1146431646418079744) channel in Discord under the FREELANCERS category.
