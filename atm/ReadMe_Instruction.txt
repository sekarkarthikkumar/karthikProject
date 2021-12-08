AtmApplication.java is the main class
Run as spring boot app, once the server started follow the below step.

Using Postman:
1. To check balance 
GET -> localhost:8080/atm/balance/123456789/1234
localhost:8080/atm/balance/{account number}/{pin}

2. To withdraw amount 
GET ->localhost:8080/atm/withDraw/123456789/1234/555
localhost:8080/atm/withDraw/{account number}/{pin}/{amount}

Created loading account and cash using following services.

3. Loading account details 
POST -> localhost:8080/atm/loadAccount

json:
{
	 "accountNumber":123456789,
	 "pin":1234,
	 "balanceAmount":800,
         "overdraftAmount":200
}

POST -> localhost:8080/atm/loadAccount
json:
{
    "accountNumber": 987654321,
    "pin": 4321,
    "balanceAmount": 1230,
    "overdraftAmount": 150
}


4. Loading cash details

POST ->

 localhost:8080/atm/loadCash
json:
[
{
"notesValue": 50,
"notesVolume": 1
},
{
"notesValue": 20,
"notesVolume": 20
},
{
"notesValue": 10,
"notesVolume": 30
},
{
"notesValue": 5,
"notesVolume": 10
}
]

--------------------------------------

Note: Junit for service layer added -> BankAccountServiceImplTest.java