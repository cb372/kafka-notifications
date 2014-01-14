#!/bin/bash

cd /usr/local/kafka

echo Starting Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties >zookeeper.log 2>&1 &

echo Waiting 5 seconds for Zookeeper to start
sleep 5

echo Starting Kafka server
bin/kafka-server-start.sh config/server.properties >kafka-server.log 2>&1 &

echo Waiting 5 seconds for Kafka server to start
sleep 5

echo Creating Kafka topic 'notifications'
bin/kafka-create-topic.sh --zookeeper localhost:2181 --replica 1 --partition 1 --topic notifications >create-notification.log 2>&1

# Get Play to download sbt, etc
echo Setting up Play
cd /vagrant/app1
play compile > play-compile.log 2>&1

echo Starting Play app 1
play "start -Dhttp.port=9001" >app1.log 2>&1 &

echo Starting Play app 2
cd /vagrant/app2
play "start -Dhttp.port=9002" >app2.log 2>&1 &

echo "Giving the apps a few seconds to start up"
sleep 10

echo Done! Now go back to the host machine and open the play apps:
echo "open http://localhost:9001 && open http://localhost:9002"
