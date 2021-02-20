# Read Me First
The following project is used for getting user account balance expressed in USD.
User account balance is stored in PLN currency, therefore it needs to be converted to USD using api of nbp:
```
GET http://api.nbp.pl/api/exchangerates/rates/a/usd?format=json
```

# Getting Started

Run server:
```
./gradlew bootRun
``` 

Application will add two accounts for testing:
* accountId - firstAccount, userId - firstUser
* accountId - secondAccount, userId - secondUser

Go to `user-balance.http` file and run two scripts

