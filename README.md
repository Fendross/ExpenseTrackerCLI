# _ExpenseTrackerCLI_

## Overview

A simple Java CLI application to manage expenses.

## How to execute

This project has been mavenized with the goal in mind to make it robust enough with unit tests.

Preliminary steps:
1. Clone the repo into your own system
2. Open up the project in a Java IDE (IntelliJ is highly recommended)

To achieve a Maven build:
1. Configure a standard Maven build, using as run commands `clean install` and make sure the build succeeds
2. Execute in a terminal, in the root directory of the project: `./run_expense_tracker_cli.sh`

To achieve an IntelliJ build:
1. Run `ExpenseTrackerCLI` with no arguments

After launching the CLI, interact with the application using the commands described below.

## Supported Commands

Refer to the following functionalities.
**Notice**: parameters must be separated by a single whitespace in between them.

### `add` 
Append to the appropriate data structures the cash flow statement specified.

In order:
1. Mandatory parameters:
   - type of statement -> string, `expense` or `income`
   - amount -> floating point number, value of statement
   - category -> string, category of statement
2. Optional parameters (if not passed as input, string will be empty):
   - subcategory -> string, subcategory of statement
   - description -> string, description of statement

It directly follows that a command will not be executed if it has less than 4 parameters or more than 6.

### `delete`
Delete from the appropriate data structures the cash flow statement specified.

In order:
1. Mandatory parameters:
- id -> integer, unique identifier of the cash flow statement

### `view`
Lists all cash flow statements that have been added since the launch of the program.

### `report`
Prints a minimal cash flow report, showing (with the appropriate currency):
- The total amount of incomes
- The total amount of incomes
- The net cash flow

### `help`
Prints a mini guide on how to use the CLI.

### `exit`
Terminates the execution.


## References

- [Cash Flow Statement definition](https://en.wikipedia.org/wiki/Cash_flow_statement)
- [Build a simple main.java.com.fendross.expensetrackercli.core.expense.Expense Tracker CLI](https://roadmap.sh/projects/expense-tracker)