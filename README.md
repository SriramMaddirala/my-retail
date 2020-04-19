# myRetail

<h3> Live Server: </h3>

https://my-retail-srtleap7qq-ue.a.run.app/

About page at https://my-retail-srtleap7qq-ue.a.run.app/about

<h3>Technologies Used:</h3>

Spring Boot

MongoDB

Target Redsky API

Docker

Google Cloud Run

Mockito


<h3>Valid urls to which one could send Get/Put Requests:</h3>

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

https://my-retail-srtleap7qq-ue.a.run.app/products/16696652

https://my-retail-srtleap7qq-ue.a.run.app/products/53334446

https://my-retail-srtleap7qq-ue.a.run.app/products/53211599

https://my-retail-srtleap7qq-ue.a.run.app/products/52946420

<h3>Running Local Instance:</h3>

To pull and run the corresponding Docker image for this application at localhost 5000 run: 
"docker run -p 5000:8080 maddi011/my-retail:0.0.1-SNAPSHOT" from your terminal

You can check that the service is up locally via http://localhost:5000/actuator/health

<h1> Design notes: </h1>

<h2> Get Request Route: </h2>

Call {url}/products/{id}

It returns JSON, example: {"id" : 1384608, "name" : "Big Leb(Blu Ray)(WideScreen)", "current_price" : {"value": 13.49,"currency_code":"USD"}}


"name" is stored at http://redsky.target.com/v2/pdp/tcin/"13860428".

Price details need to be stored in a noSQL data store so routing is:

	1. call made to MongoDB for price details
	
		2a. If no price in MongoDB then make a call to target API and get the name and price.
		
			3a. Then put in the price into MongoDB for future use.
			
		2b. If price is in MongoDB then make a call to target api to get the name.
		
	4. Combine Price and Name into a JSON and return it.


<h2>Update Request:</h2>

Call {url}/products/{id}

Request Body is a JSON containing id, current_price.
	1. Call made to MongoDB to put Price
	2. Return the object updated into DB

Application is made with SpringBoot and uses MongoDB Atlas as it's NoSQL DB.
Application is put in a docker container and run on Google Cloud Run.


Having no security around the GET/PUT requests is not acceptable. In production code we'd have JWT tokens to handle authorization.
But as it's a little overkill to have to generate JWT tokens to access the API we'll simply have basic Auth which would require Username/Password.

Having the PUT request exposed means that anybody would get access to the Database which is an issue and furthermore because getting a DB miss on prices would trigger a put from the external API this means it would also be useful to require authorization for the GET method.
Maybe this API needs to be a service that is to be paid for so adding basic authorization makes sense. 

So let's keep access to those who use the following Credentials:
Username: target
Password: target

Unit tests serve as documentation as well as validation so we'll unit test the behavior of the controller and the product model.

The application should probably have an about page that explains who and why this api exists.

Also if I get a request for a price which isn't in our DB then once we bring the Price down from the external api
we put that value and currency Code into our DB.

MongoDB was used as MongoDB works by storing documents which are readily mappable to JSON nodes. Also MongoDB Atlas doesn't require any changes to be scaled up as more shards can easily be added to your cluster. 

I used Docker as I needed a way to run my application in a portable, reproducible way that I could deploy while minimizing the specific issues that could come due to various environmental issue and put simply I want my service to develop once, run anywhere.

I used Spring Boot as it is the industry standard for making applications in java and it really makes development very easy.

Google Cloud Run is a fully managed serverless platform for containers I thought it was a good choice for this POC as it allowed me to abstract out all the infrastructure management that would not have been useful.

<h3> Scaling it to Production: </h3>

Loggers for errors are a good idea.
Currently I am just utilizing error reporting through my Google Cloud dashboard

If we just look at movies, the distribution of searches about movies follows the power law (i.e. a change in one quantity has a proportional relative change in the quantity of the other). So it follows that the majority of searches about movies will be about a minority of movies. In fact in a more general sense popularity in products is distributed via the power law and storing this means that a caching solution would be recommended. Product popularity also is mostly time dependent as cult hits and fads are common reality while certain products like toilet paper are going to have consistent interest over time. 
This means that a least recently used cache eviction policy would be a good fit. Redis and Memcached are very popular caching solutions for this purpose.
