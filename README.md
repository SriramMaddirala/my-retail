# myRetail

Get Request:

Call {url}/products/{id}
Returns JSON, example: {"id" : 1384608, "name" : "Big Leb(Blu Ray)(WideScreen)", "current_price" : {"value": 13.49,"currency_code":"USD"}}
"name" is stored at http://redsky.target.com/v2/pdp/tcin/"13860428".
"price" needs to be stored in a noSQL data store so routing can be:
	1. call made to controller for get product
	2. call made to MongoDB for price
		3a. If no price in MongoDB then make a call to target API and get the name and price.
			4a. Then put in the "name":"price" into MongoDB for future use.
		3b. If price is in MongoDB then make a call to target api to get the name.
	 4. Combine Price and Name into a JSON and return it.

Put Request:

Call {url}/products/{id}

Request Body will be a JSON containing id, current_price.
	1. Call made to Controller for Put product
	2. Call made to MongoDB to put Price
	3. Return success of Response
