# Example to show testcontainers bug with Colima

This is a Spring Boot application with postgresql database and one test using Testcontainers.

On MacOS with [Colima](https://github.com/abiosoft/colima) the `ExampleTest` will fail with the fallowing:
```text
org.postgresql.util.PSQLException: Connection to localhost:49290 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
```

You can try it with the fallowing command:

```shell
TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock DOCKER_HOST="unix://${HOME}/.colima/default/docker.sock" ./mvnw clean package
```

see [Testcontainers Colima env vars](https://java.testcontainers.org/supported_docker_environment/)

But when we wait a bit it will work. With the fallowing command you can enable a system property witch I am using to set
a 2 sec delay:

```shell
TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock DOCKER_HOST="unix://${HOME}/.colima/default/docker.sock" ./mvnw clean package -Dwait-a-bit
```

Here is the [issue](https://github.com/testcontainers/testcontainers-java/issues/7875) and the solution is:

```shell
colima start --network-address
export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
export TESTCONTAINERS_HOST_OVERRIDE=$(colima ls -j | jq -r '.address')
export DOCKER_HOST="unix://${HOME}/.colima/default/docker.sock"
```