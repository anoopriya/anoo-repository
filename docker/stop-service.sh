#!/usr/bin/env bash
echo "Stopping the hello-world-service ..."
docker stop hello-world-service; docker rm hello-world-service
echo "... done."
