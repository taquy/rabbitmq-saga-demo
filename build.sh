#!/bin/bash

# cd orchestrator && mvn clean package && docker build -t saga-2-orchestrator . && cd .. && \
# cd service-a && mvn clean package && docker build -t saga-2-service-a . && cd .. && \
# cd service-b && mvn clean package && docker build -t saga-2-service-b . && cd .. && \
# cd service-main && mvn clean package && docker build -t saga-2-service-main . && cd ..
cd orchestrator && mvn clean package && cd .. && \
cd service-account && mvn clean package && cd .. && \
cd service-room && mvn clean package && cd .. && \
cd service-ticket && mvn clean package && cd ..
