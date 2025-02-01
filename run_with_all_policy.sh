#!/bin/bash

# Run the ReflectiveHttpClientCaller with a grant for a security policy - see Java source

JAVA_HOME=~/.jdks/corretto-21.0.6
~/.jdks/corretto-21.0.6/bin/java --version
~/.jdks/corretto-21.0.6/bin/java -Djava.security.manager -Djava.security.policy=all_security.policy -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller "$1"
#JAVA_HOME=/home/paul/jdk-25
#./jdk-25/bin/java --version
#./jdk-25/bin/java -Djava.security.manager -Djava.security.policy=all_security.policy -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller "$1"