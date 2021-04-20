#!/bin/bash

set -e

GIT_GET="git clone --single-branch --depth 1"

export GRADLE_OPTS='-server -Dorg.gradle.daemon=false'

####################
# set up test mine #
####################
sudo -u postgres createuser test
sudo -u postgres psql -c "alter user test with encrypted password 'test';"
export KEYSTORE=${PWD}/keystore.jks

echo "#---> Getting code from github (for testmine)"
${GIT_GET} https://github.com/intermine/intermine.git
cd intermine

echo "#---> Setting mine properties"
source config/create-ci-properties-files.sh

echo '#---> Installing python requirements'
pip install -r config/lib/requirements.txt

echo "#---> Set up database for testing"
(cd intermine && ./gradlew createUnitTestDatabases)

echo '#---> Building and releasing web application to test against'
cd testmine && ./setup.sh

sleep 60 # let webapp startup

#############
# Run tests #
#############

echo "#---> Getting code from github (client)"
cd
${GIT_GET} https://github.com/intermine/intermine-ws-java.git client

echo "#---> Testing"
(cd client && ./gradlew test)

echo "#---> Checking test results"
./client/config/lib/parse_test_report.py 'client'

echo "#---> All tests passed"


