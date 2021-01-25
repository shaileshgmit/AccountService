POST
URL:localhost:8083/account
Header :
Authorization:<jwtToken>(will get from customer service)

Request:{
			"accountType": "Saving",
			"openDate": "2019-01-02T06:29:59.862+00:00",
			"branch": "abc",
			"minorIndicator": "T",
			"customer" : {
							"userName": "test123",
							"dateOfBirth": "2019-01-02T06:29:59.862+00:00",
							"gender": "M",
							"phoneNumber": "7655768",
							"password" : "test123"
						}
			
		}

POST
URL:localhost:8083/accountadd
Header :
Authorization:<jwtToken>(will get from customer service)

		
{
			"accountType": "Saving",
			"openDate": "2019-01-02T06:29:59.862+00:00",
			"branch": "abc",
			"minorIndicator": "T",
			"customerName":"saving",
            "customerId": 1
			
	}

GET(return all account)
URL:localhost:8083/account
Response:[
    {
        "id": 1,
        "accountType": "Saving",
        "openDate": "2019-01-02T06:29:59.862+0000",
        "branch": "abc",
        "minorIndicator": "T",
        "customerId": 2,
        "customerName": "test123"
    }
]

GET(return specific account)
URL:localhost:8083/account/2
Response:{
    "accountType": "Saving",
    "openDate": "2000-01-02T06:29:59.862+0000",
    "branch": "katras",
    "customer": {
        "id": 5,
        "userName": "test4",
        "dateOfBirth": "2019-01-02T06:29:59.862+0000",
        "gender": "M",
        "phoneNumber": "7655768",
        "password": "test1",
        "role": {
            "roleName": "Customer",
            "roleCode": "001"
        }
    }
}

PUT
URL:localhost:8083/account/2
{
			"accountType": "Saving",
			"openDate": "2000-01-02T06:29:59.862+00:00",
			"branch": "katras",
			"minorIndicator": "T",	
			"customerName":"new name"
}

DELETE
Url:localhost:8083/account/2
Response:Account deleted successfully..
