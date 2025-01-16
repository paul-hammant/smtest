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

## Run with Security Policy

1. **Run the Shell Script**

   From the root directory of the project, execute the following command:

   ```bash
   ./run_with_policy.sh

   # or

   ./run.sh
   ```

   This script runs the `ReflectiveHttpClientCaller` class under a security manager with a policy that restricts permissions (or without), except for the permissions explicitly granted in the `security.policy` file.
