# The Developer guide

## How to build it locally

### Requirements

To compile and run this app you will need:

- JDK 11
- Maven 3.8+

### How to build locally

The following command will build the jar that will let you start the CLI using a JVM:

> `mvn clean package`

Then run it:

> `java -jar ./target/quarkus-app/quarkus-run.jar`

### How to build the Linux native executable and its container

The following command will build the jar that will let you start the CLI using a JVM:

> `mvn clean package -Pnative-container`

Then run it:

> `docker run -it digital/samplecli:1.0-SNAPSHOT help`

### How to build your platform native executable

The following command will build the jar that will let you start the CLI using a JVM:

> `mvn clean package -Pnative-local`

Then run it:

> `./target/samplecli-1.0-SNAPSHOT-osx-x86_64-runner`

or

> `./target/samplecli-1.0-SNAPSHOT-windows-x86_64-runner`

## How to develop and run tests

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports live coding. To try this out:

> mvn quarkus:dev

or

> mvn quarkus:dev -Dquarkus.args=<args>

if you want to pass arguments in. This command will run the command mode application and if you press enter run it
again. If you make a change, you can rerun your command by just typing enter. No need to recompile or package. If you
want to change the command you're testing, just type `e`. If you want to run continuously the Unit tests that are
impacted by your live changes, just type `r`.

## How to deploy the native executable CLI built on our machine

### Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM (See the [Building a Native Executable guide](https://quarkus.io/guides/building-native-image-guide)
  for help setting up your environment.)

### Windows Requirements

- To install `native-image` binary, if you encounter some SSL errors, you may need to make
  a [manual installation](https://www.graalvm.org/22.1/reference-manual/graalvm-updater/#install-components-manually).
- If you have errors because cl.exe is not found, you may need
  to [install Visual Studio](https://visualstudio.microsoft.com/thank-you-downloading-visual-studio/?sku=Community&channel=Release&source=VSLandingPage&cid=2029&workload=cplusplus)

### Procedure

| Platform | Command |
|----------|---------|
| Linux | *No need, the CI already publish the Linux binary* |
| Mac | Run `sh src/main/scripts/release-tag.sh <version>` |
|Windows|Use a search to open `x86_x64 Cross Tools Command Prompt for VS 2022` and run `.\src\main\scripts\release-tag.bat 1.0.29 <version>`|

