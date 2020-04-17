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

Application is made with SpringBoot and uses MongoDB Atlas as it's NoSQL DB.
Application is put in a docker container and run on Google Cloud Compute Engine.
It will then be exposed via a load balancer.

You can download Docker image 
with docker run -p 5000:8080 maddi011/my-retail:0.0.1-SNAPSHOT

You can access the application at https://my-retail-srtleap7qq-ue.a.run.app
With Get/Put Requests at https://my-retail-srtleap7qq-ue.a.run.app/products/{id}

TODO:

There is no security around the GET/PUT requests. In production code we'd have JWT tokens to handle authorization.
But as this is it's a little overkill to have to generate JWT tokens to access the API we'll have the following security design.
We'll keep GET request exposed with auth. However having the PUT request exposed means that anybody would get access to the Database which is an issue. 
So let's keep access to those who use the following Credentials:
Username: target
Password: target

Unit tests serve as documentation as well as validation so we'll unit test the behavior of the controllers and the models.

The application should probably have an about page that explains who and why this api exists.
The application should also specific error mappings for 
	1. No data exists at this product mapping
	2. 404 error
	3. Invalid credentials
And a general catch all error mapping
