# USE CASE: 2 Produce statistical reports on cities

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *Investment Advisor* I want *to produce statistical reports on cities* so that *I can support investment decisions.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database contains city data.

### Success End Condition

A report is available for Investment Advisor to provide to Marketing department.

### Failed End Condition

No report is produced.

### Primary Actor

Investment Advisor.

### Trigger

A request for city information is sent to Investments.

## MAIN SUCCESS SCENARIO

1. Marketing requests city information.
2. Investment Advisor extracts information of all cities in the country.
3. Investment Advisor provides report to Marketing.

## EXTENSIONS

1. **Role does not exist**:
    1. Investment Advisor informs Marketing no role exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
