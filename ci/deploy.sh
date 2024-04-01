#!/bin/bash

set -e

# Change to the directory where the built artifact is located
cd source-code/artifact

# Here you would typically deploy your application using the built artifact.
# This could involve copying the JAR file to a server, restarting services, etc.

# Example: Copying the JAR file to a target server
scp Bookshop-project.jar user@hostname:/path/to/deployment/directory/

# Example: Restarting the application service on the target server
ssh user@hostname "sudo systemctl restart my-java-app"
