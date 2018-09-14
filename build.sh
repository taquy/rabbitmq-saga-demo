#!/bin/bash

cd orchestrator && mvn clean package && docker build -t saga-2-service-orchestrator . &&cd .. && \
cd service-account && mvn clean package && docker build -t saga-2-service-account . &&cd .. && \
cd service-room && mvn clean package && docker build -t saga-2-service-room . &&cd .. && \
cd service-ticket && mvn clean package && docker build -t saga-2-service-ticket . &&cd ..
