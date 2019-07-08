#!/bin/bash

set -e

echo "RUNNING test suite $TEST_SUITE"

GIT_GET="git clone --single-branch --depth 1"

export ANT_OPTS='-server'

if [ "$TEST_SUITE" = "intermine" ]; then
    echo "RUNNING unit tests"

    echo "Getting code from github"
    $GIT_GET https://github.com/intermine/intermine-ws-java.git client

    echo "Installing"
    cd client && ./gradlew install
    echo "Running tests"
    ./gradlew test

    echo CHECKING results
    ./config/lib/parse_test_report.py 'intermine'

    echo ALL TESTS PASSED

fi
