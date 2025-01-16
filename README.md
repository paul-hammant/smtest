# Project Overview

This project consists of two modules: `core` and `test`. The `core` module depends on the `test` module for compilation but does not include it in the runtime classpath. The `ReflectiveHttpClientCaller` class in the `core` module uses reflection to load and invoke a method from the `test` module.

## Build and Run Instructions

To build and run the project, follow these steps:

1. **Build the Project**

   First, navigate to the root directory of the project and run the following command to build all modules:

   ```bash
   mvn install
   ```

   This command will compile the source code, run tests, and package the modules into JAR files.

2. **Run the Core Module**

   After building the project, navigate to the `core` module directory, and exec the main class

   ```bash
   cd core/
   mvn exec:java
   ```

   This will run the `ReflectiveHttpClientCaller` class, which attempts to load the `TestHttpClient` class from the `test` module using reflection and prints the result of the `fetchAndValidateResponse` method.

## Run with Security Policy

Alternatively, you can run the `ReflectiveHttpClientCaller` class with a restrictive security policy using the provided shell script. This assumes that you have already built the project with `mvn install`.

1. **Run the Shell Script**

   From the root directory of the project, execute the following command:

   ```bash
   ./run_with_policy.sh
   ```

   This script runs the `ReflectiveHttpClientCaller` class under a security manager with a policy that restricts permissions, except for the permissions explicitly granted in the `security.policy` file.
