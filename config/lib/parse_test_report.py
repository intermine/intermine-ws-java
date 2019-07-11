#!/usr/bin/env python

from __future__ import print_function

import sys
import os
import os.path as path
import xunitparser

# This requires the Gradle top-level directory (e.g. 'intermine' or 'bio')
directory = sys.argv[1]
total_failure_count = 0
total_test_count = 0

tests_path = path.join(directory, 'build/test-results/test')

print('Processing %s' % tests_path)

test_count = 0
failure_count = 0

for filename in os.listdir(tests_path):

    print('Processing %s' % filename)

    if filename.endswith(".xml"):
        with open(path.join(tests_path, filename)) as f:

            suite, tr = xunitparser.parse(f)
            failures = [testcase for testcase in suite if not testcase.good]

            for testcase in failures:

                print(
                    '%s: Class %s, method %s' % (testcase.result.upper(), testcase.classname, testcase.methodname))
                print(testcase.trace)

            test_count += len(list(suite))
            failure_count += len(failures)


print('Found %d tests with %d failures' % (test_count, failure_count))

total_test_count += test_count
total_failure_count += failure_count

print(total_test_count, 'tests were run')

if total_failure_count:
    print(total_failure_count, 'TESTS FAILED')
    sys.exit(total_failure_count)
