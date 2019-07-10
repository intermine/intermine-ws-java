#!/bin/bash

set -e

GIT_GET="git clone --single-branch --depth 1"

BUILD_LOG=${HOME}/build.log

export PSQL_USER=postgres

echo "#---> Setting mine properties"
source config/create-ci-properties-files.sh

echo '#---> Installing python requirements'
pip install -r config/lib/requirements.txt

echo "#---> Getting code from github"
$GIT_GET https://github.com/intermine/intermine.git
cd intermine

echo "#---> Set up database for testing"
(cd intermine && ./gradlew createUnitTestDatabases)

echo '#---> Building and releasing web application to test against'
cd testmine && ./setup.sh
