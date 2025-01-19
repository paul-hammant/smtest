#!/bin/bash

# Run the ReflectiveHttpClientCaller with a grant for a security policy - see Java source
java -Djava.security.manager -Djava.security.policy=security.policy -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller "$1"
