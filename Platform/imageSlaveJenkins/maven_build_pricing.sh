#!/bin/bash

set -e

output_log=/tmp/nightly-setup

function trap_on_failure() {
	        echo "Error while setting the new versions. Printing the logs"
		        cat $output_log
		}

		trap "trap_on_failure" ERR

		version_date=$(date +%Y%m%d)
    
		mvn versions:update-parent &> $output_log

		mvn $projectOption build-helper:parse-version versions:set -DnewVersion='${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.incrementalVersion}-'$version_date &>> $output_log

		mvn versions:use-latest-versions -DallowMajorUpdates=false -DallowMinorUpdates=false -DallowIncrementalUpdates=false &>> $output_log

		mvn versions:update-properties -N -DfailIfNotReplaced=true -DallowMajorUpdates=false -DallowMinorUpdates=false -DallowIncrementalUpdates=false &>> $output_log

       	mvn versions:commit &>> $output_log


