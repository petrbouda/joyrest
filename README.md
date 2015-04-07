# JoyRest
##### Lightweight and Lambda-based REST Framework
### Official pages - [http://www.joyrest.org](http://www.joyrest.org)
### About
JoyRest is a lightweight and convenient tool for **_creating REST services_** by a concise way. The main intention is to provide framework which is simply extensible for different types of DI and servers. JoyRest is purely developed using **_Java programming language 8 and with lambdas_**. Thank to this technology the framework is able to provide very clear API which allows you to defined your endpoints through a **_functional way_**.

### Example
```java
public class JokeController extends AbstractControllerConfiguration {

	@Inject
	private JokeService service;

	@Override
	protected void configure() {
		setGlobalPath("jokes");

		// Create a new Joke
		post((Request request, Response response, Joke joke) -> {
			Joke savedJoke = service.save(joke);
			response.entityClass(savedJoke)
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, 
				    getEntityLocation(savedJoke.getId(), request.getPath()));
		}).consumes(MediaType.JSON).produces(MediaType.JSON);

		// Get a list of all jokes
		get((request, response) -> {
			List<Joke> jokes = service.getAll();
			response.entityClass(jokes);
		}).produces(MediaType.JSON, MediaType.XML);

		// Get a concrete joke using ID
		get(":id", (request, response) -> {
			Joke joke = service.get(request.getPathParam("id"));
			response.entityClass(joke);
		}).produces(MediaType.JSON, MediaType.XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
```

### Easy Integration
One of the main requirements of this framework is easy integration with other tools such as DI framework or Servers. A clue how to integrate a new DI or Server is covered in a particular section **_Integration_**.

#### Dependency Injection Frameworks
Primarily JoyRest was developed using `HK2` DI but is very easy to integrate JoyRest with whatever dependency framework you want. A support for most famous DI framework such as `Spring, Guice, Dagger` will be developed implicitly.

#### Servers
A Support for both types of servers `HTTP Server and NIO Server` is covered and whatever server can be used for running an application with JoyRest framework.

### License
```
Copyright 2015 Petr Bouda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```