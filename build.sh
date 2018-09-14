#!/bin/bash

cd orchestrator && mvn clean package && cd .. && \
cd service-account && mvn clean package && cd .. && \
cd service-room && mvn clean package && cd .. && \
cd service-ticket && mvn clean package && cd ..
