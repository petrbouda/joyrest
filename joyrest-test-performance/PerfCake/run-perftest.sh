#!/usr/bin/env bash

echo "----------------------------------------------------------------------"
echo "Performance-Test Processing for: $1"
echo "----------------------------------------------------------------------"
echo ""

SCRIPTPATH=`pwd -P`

for path in scenarios/*.xml
do
	SCENARIO=$(basename $path)
	echo -e "\n******************************************"
	echo "Scenario was started: $SCENARIO"
	echo "******************************************"
	bash $PERFCAKE_HOME/bin/perfcake.sh -s $SCRIPTPATH/scenarios/$SCENARIO -D framework=$1 -D base.path=file://$SCRIPTPATH
done
