#!/bin/bash

set -e

# Change to the directory where the Maven project is located
cd source-code

# Run Maven build with skipping tests
mvn clean package -DskipTests

# Move the built artifact (e.g., JAR file) to the output directory
mv target/Bookshop-project.jar ../artifact/
