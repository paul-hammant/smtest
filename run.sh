#!/bin/bash

# Run the ReflectiveHttpClientCaller with a restrictive security policy
JAVA_HOME=~/.jdks/corretto-21.0.6
~/.jdks/corretto-21.0.6/bin/java --version
~/.jdks/corretto-21.0.6/bin/java -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller

#JAVA_HOME=~/.jdk-25
#./jdk-25/bin/java --version
#./jdk-25/bin/java -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller
