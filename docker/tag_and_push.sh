#!/bin/sh

echo "Tag (override if necessary) the hello-world-service..."
sudo docker tag -f hbcdigital/service:hello-world-service-0.1 hd1cutl01lx.saksdirect.com:5000/hello-world-service-0.1

echo "Push hello-world-service image to Internal Docker Registry..."
sudo docker push hd1cutl01lx.saksdirect.com:5000/hello-world-service-0.1

echo "Completed pushing hello-world-service image to Internal Docker Registry."
