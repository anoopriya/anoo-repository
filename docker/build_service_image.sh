#!/usr/bin/env bash

echo "Building Distribution ZIP for hello-world-service..."
if ! [ -a ../target/universal/hello-world-service-0.1.zip ]  ; then
	echo "Distribution ZIP not found, building from source..."
	cd ../ && sbt "project hello-world-service" dist
	cd docker
fi

echo "Copying hello-world-service ZIP to current directory..."
cp ../target/universal/hello-world-service-0.1.zip .

tag=hbcdigital/service:hello-world-service-0.1

echo "Building hello-world-service Docker Container.."
sudo docker build -t ${tag} .

echo "Removing ZIP..."
rm hello-world-service-0.1.zip

echo "Completed building image: $tag"
