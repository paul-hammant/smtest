# Project Overview

This project consists of two modules: `core` and `test`. The `core` module depends on the `test` module for compilation but does not include it in the runtime classpath. The `ReflectiveHttpClientCaller` class in the `core` module uses reflection to load and invoke a method from the `test` module.

## Build and Run Instructions

To build and run the project, follow these steps:

1. **Build the Project**

First, navigate to the root directory of the project and run the following command to build all modules:

```bash
mvn install
```

This command will compile the source code, and package the modules into JAR files.

## Default run with a "no security manager & policy" situation (the default for Java)

```bash
./run.sh
 ```

Output should be:

```
TestHttpClient is NOT in ordinarily the base classpath, good.
NO explicit GRANTING permissions in Java, See README.md
Setup complete, now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse()....
Class that was dynamically loaded from a child classloader was able to go to HttpBin.org and get a JSON response
```

## Run with Security Policy and Java granting of privileges

Execute the following command:

```bash
./run_with_policy.sh --WithGetAccessPermissionGrantInJava
```

Output should be:   

``` 
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
TestHttpClient is NOT in ordinarily the base classpath, good.
Explicit GRANTING of `new URLPermission("https://httpbin.org/get", "GET:Accept")` in Java
Setup complete, now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse()....
Class that was dynamically loaded from a child classloader was able to go to HttpBin.org and get a JSON response
```

The same output as before, with two Warnings from the JDK itself

## Run with Security Policy with no granting of privileges in Java

Execute the following command:
   
```bash   
./run_with_policy.sh
```

Output should be:

```
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
TestHttpClient is NOT in ordinarily the classpath, good.
NOT GRANTING permissions in Java, re-run with --WithGetAccessPermissionGrantInJava as arg to shell script
etup complete, now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse()....
Class that was dynamically loaded from a child classloader was NOT able to go to HttpBin.org and get a JSON response. 
```

For that, java.lang.SecurityException was caught, and the "Not able to..." message printed out
