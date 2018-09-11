#!/bin/bash
cd orchestrator && mvn package &&  cd .. \
cd service-a && mvn package && cd .. \
cd service-b && mvn package && cd .. \
cd service-main && mvn package && cd ..
