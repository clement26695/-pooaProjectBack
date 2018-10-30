#!/bin/sh

mvn package
docker build . -f docker/Dockerfile -t clement26695/myseries_back
