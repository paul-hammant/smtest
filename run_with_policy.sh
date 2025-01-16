#!/bin/bash

# Run the ReflectiveHttpClientCaller with a restrictive security policy
java -Djava.security.manager -Djava.security.policy=security.policy -cp core/target/core-1.0-SNAPSHOT.jar com.example.ReflectiveHttpClientCaller
