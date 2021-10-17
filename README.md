# ATM Application

This ATM application allows for simple operations to view the account details and withdraw funds from the ATM.

## Running Application

The application is built with Gradle.

It can be run by cloning the repo and running a ```gradle bootRun```

## Endpoints
------------------------------
### - getAccountDetails
This endpoint will return account number, current balance, and the overdraft limit of the account.

#### URL
`localhost:5000/account/{ACCOUNT}/pin/{PIN}`

#### Example Response

```
{
    "Account No": 123456789,
    "Balance": 800,
    "Overdraft": 200
}
```

### withdraw
This endpoint will withdraw funds from the ATM and reduce the balance of the account.
Relevant validations occur include checking funds are available in account and ATM.

*Overdraft is assumed to be a static value set by the bank. Balance can be reduced to the negative amount of the overdraft, e.g. if account has an overdraft of 200, user can withdraw until the balance reaches -200.*

#### URL
`localhost:5000/account/{ACCOUNT}/pin/{PIN}/withdraw/{AMOUNT}`

#### Example Response
```
{
    "Account Details": {
        "Account No": 987654321,
        "Balance": 1055,
        "Overdraft": 150
    },
    "Notes Withdrawn": [
        {
            "Bank Note": 50,
            "Count": 3
        },
        {
            "Bank Note": 20,
            "Count": 1
        },
        {
            "Bank Note": 5,
            "Count": 1
        }
    ]
}
```

## BitBucket Pipelines
Bitbucket Pipelines was enabled for this repo.

Everytime a commit is pushed to the repo, a job will trigger which will compile the application and then run the code through Sonar to check for any code quality issues.

Pipeline jobs can be viewed here: https://bitbucket.org/mdanaher/atm-application/addon/pipelines/home 

## Sonar
The application was periodically run through Sonar to check for code quality issues and bugs. A project was set up on sonarcloud.io for this.

The most recent shows:

 * 0 Bugs
 
 * 0 Code Smells
 
 * 96.8% Code Coverage
 
Sonar report can be viewed here: https://sonarcloud.io/dashboard?id=com.mauriced%3Aatm-application