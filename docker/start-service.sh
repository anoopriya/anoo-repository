#!/usr/bin/env bash
echo "Starting the hello-world-service on port 9000 ..."
docker run -d --name hello-world-service -p 9000:9000 hbcdigital/service:hello-world-service-0.1
echo "... done."
