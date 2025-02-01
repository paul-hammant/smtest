# Project Overview

This project consists of two modules: `core` and `test`. The `core` module depends on the `test` module for compilation but does not include it in the runtime classpath. The `ReflectiveHttpClientCaller` class in the `core` module uses reflection to load and invoke a method from the `test` module.

## Build and Run Instructions

To build and run the project, follow these steps:

1. **Build the Project**

First, navigate to the root directory of the project and run the following command to build all modules:

```bash
mvn install
```

This command will compile the source code, and package the modules into JAR files in their respective target/ folders

## Default run with a "no security manager & policy" situation (the default for Java)

```bash
./run_with_no_policy.sh
 ```

Output should be:

```
TestHttpClient is NOT in ordinarily the base classpath, good.
NO explicit GRANTING permissions in Java withough --WithGetAccessPermissionGrantInJava option, See README.md
Setup complete, now testing reflection-based invocation of TestHttpClient.fetchAndValidateResponse()....
Class that was dynamically loaded from a child classloader was able to go to HttpBin.org and get a JSON response
```

## Permutations to run

```bash
./run_with_no_policy.sh 
./run_with_specific_policy.sh --WithGetAccessPermissionGrantInJava
./run_with_specific_policy.sh 
./run_with_all_policy.sh --WithGetAccessPermissionGrantInJava
./run_with_all_policy.sh 
```

Among other output lines, the crucial one is:

``` 
Test: Class that was dynamically loaded from a child classloader, on a reflection-invcation of a permission-needing method (get string from httpbin.org/get) encountered: JSON as expected

// or

Test: Class that was dynamically loaded from a child classloader, on a reflection-invcation of a permission-needing method (get string from httpbin.org/get) encountered: Security Exception, msg: access denied ("java.net.URLPermission" "https://httpbin.org/get" "GET:Accept")
```

Status (gets JSON or has Security Exception):

```
JSON as expected: ./run_with_no_policy.sh 
Security Exception as expected: ./run_with_specific_policy.sh 
Unexpected Security Exception: ./run_with_specific_policy.sh --WithGetAccessPermissionGrantInJava
Security Exception as expected: ./run_with_all_policy.sh 
JSON as expected: ./run_with_all_policy.sh --WithGetAccessPermissionGrantInJava
```

