#!/bin/sh

TARGET_JAR=target/ExpenseTrackerCLI-1.0-SNAPSHOT.jar
MAIN_CLASS=com.fendross.expensetrackercli.ExpenseTrackerCLI

#echo "Target JAR: " $TARGET_JAR
#echo "Main Class to run: " $MAIN_CLASS

echo "================================================================"

if [ -f $TARGET_JAR ]; then
  java -cp $TARGET_JAR $MAIN_CLASS
else
  echo "ERROR: JAR not found."
fi

exit 0
