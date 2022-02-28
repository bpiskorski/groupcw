# USE CASE: 4 Produce population reports on capitals

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *Investment Advisor* I want *to produce population reports on capitals* so that *I can support investment decisions.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database contains population data.

### Success End Condition

A report is available for Investment Advisor to provide to Marketing department.

### Failed End Condition

No report is produced.

### Primary Actor

Investment Advisor.

### Trigger

A request for population information is sent to Investments.

## MAIN SUCCESS SCENARIO

1. Marketing requests population information.
2. Investment Advisor extracts information of the population.
3. Investment Advisor provides report to Marketing.

## EXTENSIONS

1. **Role does not exist**:
    1. Investment Advisor informs Marketing no role exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
