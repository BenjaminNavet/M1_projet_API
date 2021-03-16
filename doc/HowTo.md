# Caveats, warnings and other vital information to build a RESTfull application

## Some critical definitions have moved.

Starting from Java 9, Oracle has started to move critical declaration used for EE applications.

For instance, to get the proper JAX-RS declarations, you need to add this to your `pom.xml` file:

```xml
<dependency>
  <groupId>javax.xml.bind</groupId>
  <artifactId>jaxb-api</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>com.sun.xml.bind</groupId>
  <artifactId>jaxb-core</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>com.sun.xml.bind</groupId>
  <artifactId>jaxb-impl</artifactId>
  <version>2.3.0</version>
</dependency>
```

## ServletContainer not found (when starting you REST server)

```text


It's an eclipse setup issue, not a Jersey issue.

From this thread ClassNotFoundException: org.glassfish.jersey.servlet.ServletContainer

Right click your eclipse project Properties -> Deployment Assembly -> Add -> Java Build Path Entries -> Gradle Dependencies -> Finish.

So Eclipse wasn't using the Gradle dependencies when Apache was starting .

```
