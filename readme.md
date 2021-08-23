# Solution

I created a basic Spring Boot app with Java 11 (to have `HttpClient` instead of use an external library to make http requests). I could have use swagger codegen to generate a project from the file `similarProducts.yaml`, but having only 1 endpoint it was easier to create a project from scratch.

The API info is in `com.example.shop.api` and the logic is in `com.example.shop.service`.

I used the `Cacheable` annotation on the service method to cache the responses of "similar products", which is very common in an ecommerce platform. I would not have used the cache if I had to return the detail of the product of the given productId, because in that case the availability and price are important, but in a list of similar products it does not have as much relevance.

The steps I followed to test the project are:

- Added the folder `shared` to the file sharing settings of Docker.
- Edited `docker-compose.yaml` to use influxdb `1.8` instead of `latest`, which currently is `2.0.8`, which is incompatible with k6.
- Launch the mocks script.
- Run the project's Tomcat.
- Launch the test script.

# Backend dev technical test
We want to offer a new feature to our customers showing similar products to the one they are seeing. To do this we agreed with our front-end applications to create a new REST API that will provide them the product detail of similar products to a given one. [Here](./similarProducts.yaml) is the contract we agreed.

We already have an endpoint that provides the product Ids similar to a given one. We also have another endpoint that returns the product detail by productId. [Here](./existingApis.yaml) is the documentation of the existing APIs.

**Create a Spring boot application that exposes the agreed REST API on port 5000.**

![Diagram](./assets/diagram.jpg "Diagram")

Note that _Test_ and _Mocks_ components are given, you must only implement _yourApp_.

## Testing and Self-evaluation
You can run the same test we will put through your application. You just need to have docker installed.

First of all, you may need to enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

Then you can start the mocks and other needed infrastructure with the following command.
```
docker-compose up -d simulado influxdb grafana
```
Check that mocks are working with a sample request to [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids).

To execute the test run:
```
docker-compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the results.

## Evaluation
The following topics will be considered:
- Code clarity and maintainability
- Performance
- Resilence
