# _ExpenseTrackerCLI_

## Overview

A simple Java CLI application to manage expenses.

## How to execute

Simply clone the repo into your own system, open up the project in a Java IDE and then run `ExpenseTrackerCLI.main` with no arguments.
After launching the CLI, interact with the application using the API described below.

## Operations

Refer to the following functionalities.
**Notice**: parameters must be separated by a single whitespace in between them.

### `add` 
Append to the appropriate data structures the cashflow statement specified.

In order:
1. Mandatory parameters:
- type of statement -> string, `expense` or `income`
- amount -> floating point number, value of statement
- category -> string, category of statement

2. Optional parameters (if not passed as input, string will be empty):
- subcategory -> string, subcategory of statement
- description -> string, description of statement

### `delete`
Delete from the appropriate data structures the cashflow statement specified.

In order:
1. Mandatory parameters:
- id -> integer, unique identifier of the cashflow statement

### `view`
Lists all cashflow statements that have been added since the launch of the program.

### `help`
Prints a mini guide on how to use the CLI.

### `exit`
Terminates the execution.


## References

- [Cash Flow Statement definition](https://en.wikipedia.org/wiki/Cash_flow_statement)
- [Build a simple core.expense.Expense Tracker CLI](https://roadmap.sh/projects/expense-tracker)