#!/bin/bash

set -e

echo "RUNNING UNIT TESTS"

GIT_GET="git clone --single-branch --depth 1"

export GRADLE_OPTS='-server'

echo "RUNNING unit tests"

echo "#---> Getting code from github"
$GIT_GET https://github.com/intermine/intermine-ws-java.git client

echo "#---> Installing"
cd client && ./gradlew install
echo "#---> Running tests"
./gradlew test

echo "#---> Checking test results"
../config/lib/parse_test_report.py 'intermine'

echo "#---> All tests passed"


