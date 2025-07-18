# _ExpenseTrackerCLI_

> [!NOTE]
> This is a WIP project. Its purpose would be to manage one's finances without having to fiddle around with an Excel.
> Also, this would serve as an entry point to develop an easy-to-use mobile application or web application to manage one's finances on the go.

## Overview

A simple Java CLI application to manage expenses.

## How to execute

This project has been mavenized with the goal in mind to make it robust enough with unit tests.

Preliminary steps:
1. Clone the repo into your own system
2. Open up the project in a Java IDE (IntelliJ is highly recommended)

To achieve a Maven build/run:
1. Configure a standard Maven build, using as run commands `clean install` and make sure the build succeeds
2. Execute in a terminal, in the root directory of the project: `./run_expense_tracker_cli.sh`

To achieve an IntelliJ build/run:
1. Run `ExpenseTrackerCLI` with no arguments

## How to use

After launching the CLI, interact with the application using the commands listed in the terminal.
> [!TIP]
> A menu will be displayed. Simply follow its instructions.
 
## Future TODOs
- [ ] Implement currency conversion (fetching exchange rates) if user chooses to change system currency with command '6'.
- [ ] Move currency treatment in a dedicated table.
- [ ] Add a new type of CFS, DIVIDEND, to account for that as well.

## References and Resources

- [Cash Flow Statement definition](https://en.wikipedia.org/wiki/Cash_flow_statement)
- [Build a simple Expense Tracker CLI](https://roadmap.sh/projects/expense-tracker)
- [Currency codes list and description](https://www.iban.com/currency-codes)
