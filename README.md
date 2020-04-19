# myRetail

<h3> Live Server: </h3>

https://my-retail-srtleap7qq-ue.a.run.app/

Technologies Used:
Spring Boot
MongoDB
Target Redsky API
Docker
Google Cloud Run
Mockito

Get Request Working Examples:

https://my-retail-srtleap7qq-ue.a.run.app/products/13860428

https://my-retail-srtleap7qq-ue.a.run.app/products/52091946

https://my-retail-srtleap7qq-ue.a.run.app/products/14756360

https://my-retail-srtleap7qq-ue.a.run.app/products/13860429

https://my-retail-srtleap7qq-ue.a.run.app/products/50939781

https://my-retail-srtleap7qq-ue.a.run.app/products/52997343

https://my-retail-srtleap7qq-ue.a.run.app/products/51004752

https://my-retail-srtleap7qq-ue.a.run.app/products/53256681

https://my-retail-srtleap7qq-ue.a.run.app/products/51143245

https://my-retail-srtleap7qq-ue.a.run.app/products/51301099


Get Request Route: 

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
Application is put in a docker container and run on Google Cloud Run.

You can download Docker image 
with docker run -p 5000:8080 maddi011/my-retail:0.0.1-SNAPSHOT

You can access the application at https://my-retail-srtleap7qq-ue.a.run.app
With Get/Put Requests at https://my-retail-srtleap7qq-ue.a.run.app/products/{id}

TODO:

There is no security around the GET/PUT requests. In production code we'd have JWT tokens to handle authorization.
But as this is it's a little overkill to have to generate JWT tokens to access the API we'll have the following security design.
Having the PUT request exposed means that anybody would get access to the Database which is an issue.
And furthermore because getting a DB miss on prices would trigger a put from the external API this means it would also be useful to require authorization for the GET method.
And maybe this API needs to be a service that is to be paid for so adding basic authorization makes sense. 
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

Also if I a get request for a price which isn't in our DB then once we bring the Price down from the external api
we put that value and currency Code into our DB.

API Health checks

Scaling it to Production:

Loggers for errors are a good idea.
Currently I am just utilizing error reporting through my Google Cloud dashboard

If we just look at movies, the distribution of searches about movies follows the power law (i.e. a change in one quantity has a proportional relative change in the quantity of the other). So it follows that the majority of searches about movies will be about a minority of movies. 
In fact in a more general sense popularity in products is distributed via the power law and storing this means that a caching solution would be recommended. 
Product popularity also is mostly time dependent as cult hits and fads are common reality while certain products like toilet paper are going to have consistent interest over time. 
This means that a least recently used cache eviction policy would be a good fit. Redis and Memcached are very popular caching solutions for this purpose.

Explain why MongoDB was used.
Same for Google Cloud Run
Docker
Spring Boot

Google Cloud Run is a fully managed serverless platform for containers I thought it was a good choice for this POC as it allowed me to abstract out all the infrastructure management that would not have been useful for this .
