# What are the technical foundations of dodo?

## The Java Code

### picocli

The main library that is used to create this CLI is the combination of [picocli](https://picocli.info/)
and [Quarkus](https://quarkus.io/guides/picocli). Picocli makes it very easy to setup a CLI.

`@TopCommand` indicates what the entrypoint of the CLI (see `Main.java`). And the `subcommands` value
of `@CommandLine.Command` indicates all the subcommands that can be passed to the CLI. This is recursive, all those
subcommands can also have subcommands. With that information, picocli will automatically generate the relevant help and
call the right command when needed.

Every command or subcommand must be annotated with `@CommandLine.Command` and provide the values `name`
and `description` that will be used by the help. Each command should inherit from `CommandWithHelp`. This class provides
to all subclasses the necessary help options like `-h` and `--help`.

Every command can receive [options and parameters](https://picocli.info/#_options_and_parameters) with the
annotations `@CommandLine.Parameters`
and `@CommandLine.Option`. Both should always have a `description` for the help. Parameters can be made mandatory
with `arity = "1"`. They can also have a default value: `defaultValue = "xyz"`. Options should always have short and
long names `names = { "-f", "--filter" }` and can be declared mandatory with `required = true`.

It is recommended to separate commands and business code. For that purpose, please use injection (that will also help
the writing of Unit Tests).

```java
@Inject 
MyBusinessClass businessInstance;
```

### Loggers

You can use two kinds of loggers. The generic one for all technical logs (errors and debug info mainly). It should be
messages for the developer. Not the user. They won't get printed in the console by default. But they'll get printed when
tests are executed to help you debug those. They could eventually end up in a log file too. Today they are only printed
in Console. The default technical logger should be injected this way:

```java
@Inject
Logger log;
```

A second logger is proposed for the Console out put of the CLI. This one is dedicated for the user. You can use the
various log levels. By default, the `INFO` level is used. In the future, we could have a generic `-v` or `--verbose`
option for all our commands that would change the log level of this logger to `DEBUG`.

This logger should be injected in the class this way:

```java
@LoggerName(Main.CONSOLE_OUTPUT_LOGGER)
Logger console;
```

### Configuration

To learn everything you need regarding configuration options with Quarkus, please rely
on [this document](https://quarkus.io/guides/config-reference#configuration-sources).

For convenience, there is a class `Configuration.java` that gathers all configuration used by this application. Each
property used is declared this way:

```java
@ConfigProperty(name = "my.property.name")
public Optional<String> myPropertyName;
```

Easy! If you need to use a configuration property in your code, just inject the Configuration object:

```java
@Inject
Configuration configuration;
```

An advantage of Quarkus is that you'll be able to adapt that configuration for all your cases. By adding
a `-Dmy.property.name=x`. Or by setting MY_PROPERTY_NAME environment variable. Or by using a Quarkus profile to
customize that value depending if you are in dev, test or prod. And there are plenty of other ways to customize
properties.

For example, to activate debug logging for the CONSOLE_OUTPUT_LOGGER, you can override the following environment
variable:

```bash
export QUARKUS_LOG_CATEGORY__COM_AMADEUS__LEVEL=DEBUG
```

### REST client

It is quite common to have to make a REST call. Again Quarkus helps you with that. You can use
its [RESTEasy client](https://quarkus.io/guides/rest-client) or
its [Reactive version](https://quarkus.io/guides/rest-client-reactive) if you prefer.

In a nutshell, all you'll need is to name your client and register it with an annotation at class level:

```java

@RegisterRestClient(configKey = "myService")
public interface MyServiceClient {
  ...
}
```

Then, all you need is to declare a method for each entry point that you want to be able to call. Specify via simple
annotations what HTTP method you want to use, the payload, the output (automatically mapped to objects via Jackson!),
the Form parameters, the Query parameters, the Path parameters... For example:

```java
@POST
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Path("/http/path/of/the/servide")
ObjectForResponse callService(@FormParam("param1") String param1,@FormParam("param2") String param2);
```

Note that the host URL is automatically taken as a configuration
parameter `quarkus.rest-client.myService.url=https://the.host.address.net`

When you want to use this client in your code, just inject it. And you'll get a complete REST client without having
written one line of code:

```java
@Inject
@RestClient
MyServiceClient restClient;
```

``` java
ObjectForResponse response = restClient.callService("p1","p2");
```

## Tests

With a Quarkus application, you can use two kinds of tests. Regular JUnits and QuarkusTest that will mimic an
Integration test but run it as any Unit Test.

### Quarkus tests

It is quite natural to favor QuarkusTest since it provides a way to test the contract that you have with your users:
your API. But you can still execute those tests as any unit test in your IDE or via `mvn test` or `mvn verify` (but the
project does not currently propose integration tests using `@QuarkusIntegrationTest`). For all the details
of `@QuarkusTest`, you can refer to [this documentation](https://quarkus.io/guides/getting-started-testing).

To test a CLI, Quarkus proposes to annotate your test class with `@QuarkusMainTest`. This way, you can have each test
that simulates a call to the CLI and you can check its output.

#### Simplest QuarkusMainTest

```java
@Test
@Launch(value = "command", exitCode = 2)
public void proposesSubcommands(LaunchResult result){
        Assertions.assertTrue(result.getOutput().contains("check"));
        Assertions.assertTrue(result.getErrorOutput().contains("correct"));
```

#### QuarkusMainTest with dynamic parameter

```java
@Test
void proposesSubcommands(QuarkusMainLauncher launcher){
        LaunchResult result=launcher.launch({"command",aString});
        Assertions.assertEquals(0,result.exitCode());
        Assertions.assertTrue(result.getOutput().contains("check"));
        Assertions.assertTrue(result.getErrorOutput().contains("correct"));
```

#### Test profiles

If you need to go further in terms of mocking (for example), you can rely
on [test profiles](https://quarkus.io/guides/getting-started-testing#testing_different_profiles):

```java

@QuarkusMainTest
@TestProfile(ProfileWithMock.class)
class TestClass {
```

In your profile class (that must extend `QuarkusTestProfile), you can override `
getEnabledAlternatives` to propose Injection replacements for services used in the command. You can also override `
getConfigOverrides` to propose other values for some configuration properties.

#### Mocking REST answers

If you are making some REST calls in your CLI, you
should [mock them in your tests](https://quarkus.io/guides/rest-client#using-a-mock-http-server-for-tests). To do so,
use the `QuarkusTestResource` annotation and use WireMock to serve mocks :

```java

@QuarkusMainTest
@QuarkusTestResource(WireMockExtensions.class)
class TestClass {
}

public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

  private WireMockServer wireMockServer;

  @Override
  public Map<String, String> start() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor(8089);

    stub();

    Map<String, String> config = new HashMap<>();
    config.put("quarkus.rest-client.myService.url", wireMockServer.baseUrl());

    return config;
  }

  private void stub() {
    stubFor(post(urlEqualTo("/http/path/of/the/servide"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"id_token\":\"11111100000000222223333444445555555aaaaaaaaaafffffff\"}}")));
  }

  @Override
  public void stop() {
    if (null != wireMockServer) {
      wireMockServer.stop();
    }
  }
}
```

### Unit tests

Usually, you cannot have exhaustive testing only with End to End tests. You need to test corner case and your class
design with dedicated Unit Tests. For that purpose, you can still rely on plain Junit 5 tests. Just annotate your class
with `@ExtendWith(MockitoExtension.class)` and eventually with
`@MockitoSettings(strictness = Strictness.LENIENT)` if you use some partial mocking / stubbing.

When done, you can instantiate the class instance you want to test with:

```java
@InjectMocks
private MyClass instance=new MyClass();
```

And you can prepare all the mocks and captors you'll need using the following injections:

```java
@Mock
private OtherClass testField;
@Captor
private ArgumentCaptor<List> commandCaptor;
```

Note that if a field has the same name as a field of the class you're testing, the mock will automatically get injected.

Now annotate your tests with `@Test` and use all the relevant `mock`, `verify`, `assertThrows` that you need.

## Quarkus configuration

A part of the configuration of Quarkus is done via properties. You can find a complete reference for all the
options [here](https://quarkus.io/guides/all-config). Another part is done thanks
to [application.properties](src/main/resources/application.properties).

Here are a few notable configuration properties:

|Property|Justification|
|---|---|
| `quarkus.package.type`| If value is `native` the maven build will execute the native compilation|
| `quarkus.container-image.build`| Should we also build a docker container |
| `quarkus.native.container-build`| Should the native compilation be done via a container or using the Graal VM installation of the build machine |
| `quarkus.native.native-image-xmx`| The amount of memory to reserve for native compilation (yes it is greedy) |
| `quarkus.banner.enabled`| To avoid having logs in Console that promote Quarkus |
| `quarkus.log.*`| All configuration properties for the logs and console outputs |
| `quarkus.rest-client.*`| Configuration for the Resteasy HTTP client |
| `quarkus.tls.trust-all`| The HTTP client can only ignore certificates globally. A nice improvement would be to solve that security concern. |

## Dockerfile

The packaging as a docker container is done via the configuration of the Quarkus plugin. We just provide a `Dockerfile`.
It is all standard excepted that we add the `argocd` CLI in the image since some commands need it. It was mandatory to
rely on the CLI instead of the REST API since some commmands need to look at local folders.

## pom.xml

### Plugins

Here are all the Maven plugins that you can find in the `pom.xml` and their justification:

|Plugin|Justification|
|---|---|
| `quarkus`|The must have plugin to benefit from the Quarkus framework that is the base of this framework|
| `artifactory`|To take full benefits of Artifactory when we deploy|
| `assembly`|Used for the distribution of native binaries to rename them and compress them in a zip file|
| `os`|It is actually an extension. It helps us detecting the OS on which the build is executed. Useful to setup native compilation properly.|
| `jacoco`|Used to monitor the coverage of all tests types if this application. Configured to be compatible with the maven workflow too. This can serve [as a reference](https://quarkus.io/guides/tests-with-coverage)|
| `jreleaser`|Used to publish the native binaries artefacts taht otherwise Maven does not pick|

### Profiles

Here are all the profiles that you can find in the `pom.xml` and their justification:

|Profile|Justification|
|---|---|
| `native-container`|It will go further than just package a jar, it will also compile a native (linux) binary using GraalVM. <br> All will happen in containers so the prerequisites are minimal: just make sure you have Docker running on your machine.|
|`native-local`|It will go further than just package a jar, it will also compile a native binary for your machine using GraalVM. <br> You must have GraalVM properly installed on your machine. See [dev documentation](contribute.md) for more details.|
|`native-ci`|The profile that CI will use to package a Linux native executable. Similar to `native-container` but will install GraalVM on the fly because we can't run the native compilation in a container when the CI build is already in a container.|
|`integration`|To adapt the Artifactory repositories (validated intermediate level) that are used during deployment. To be used on branches of the main repository.|
|`release`|To adapt the Artifactory repositories (production level) that are used during deployment. To be used on the release branch of the main repository.|
|`dist-windows`|Activates automatically when running a native compilation on Windows to accommodate with the fact that Windows binaries have the `.exe` extension.|

## Jenkinsfile

The CI is done using
the [Maven workflow](https://rndwww.nce.amadeus.net/confluence/pages/viewpage.action?spaceKey=JPGL&title=Maven+workflow%3A+mvn)
. Nothing very specific here excepted the need to run Java 11. Please note that `workbench.yaml` also contains some
parameters of the workflow. Please refer to the workflow documentation for details.