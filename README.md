# JoyRest
##### Lightweight and Lambda-based REST Framework
### Official pages - [http://www.joyrest.org](http://www.joyrest.org)
### About
JoyRest is a lightweight and convenient tool for **_creating REST services_** by a concise way. The main intention is to provide framework which is simply extensible for different types of DI and servers. JoyRest is purely developed using **_Java programming language 8 and with lambdas_**. Thank to this technology the framework is able to provide very clear API which allows you to defined your endpoints through a **_functional way_**.

### Download

```xml
<dependency>
    <groupId>org.joyrest</groupId>
    <artifactId>joyrest-{module name}</artifactId>
    <version>{version}</version>
</dependency>
```

#### Core Module
This is a standalone module with all needed classes for successful running of JoyRest application. But in most cases core module must be combined with other modules such as DI and server-oriented modules for sake of simplicity.

| Group ID	| Artifact ID	| Version	|
| ------------- | ------------- | ------------- |
|org.joyrest	|joyrest-core	|0.1-SNAPSHOT	|

#### Dependency Injection Modules
These modules provide capabilities of an integration with different types of DI frameworks. JoyRest can be very easy extensible for any other framework, as mention in secion Integration.

| Group ID	| Artifact ID	| Version	|
| ------------- | ------------- | ------------- |
|org.joyrest	|joyrest-hk2	|0.1-SNAPSHOT	|
|org.joyrest	|joyrest-spring	|0.1-SNAPSHOT	|
|org.joyrest	|joyrest-guice	|0.1-SNAPSHOT	|
|org.joyrest	|joyrest-dagger	|0.1-SNAPSHOT	|

#### Server Modules
It is necessary to use any REST Framework with some kind of server. Modules mentioned below allow us very simply integrate HTTP Server or Servlet-based Server with an application which contains JoyRest Framework. How to integrate any other server with JoyRest is mentioned in section Integration.

| Group ID	| Artifact ID		| Version	|
| ------------- | ---------------------	| ------------- |
|org.joyrest	|joyrest-grizzly	|0.1-SNAPSHOT	|
|org.joyrest	|joyrest-undertow	|0.1-SNAPSHOT	|
|org.joyrest	|joyrest-servlet	|0.1-SNAPSHOT	|

#### Repository for Snapshots
```xml
<repository>
    <id>oss-sonatype</id>
    <url>https://oss.sonatype.org/content/groups/public</url>
</repository>
```

### Easy Integration
One of the main requirements of this framework is easy integration with other tools such as DI framework or Servers. A clue how to integrate a new DI or Server is covered in a particular section **_Integration_**.

#### Dependency Injection Frameworks
Primarily JoyRest was developed using `HK2` DI but also supports most of the popular DI framework such as `Spring, Guice, Dagger`. To make a support for whatever DI framework is very easy and takes no more than one additional special class determined to the given DI framework. Joyrest can be used also without any DI Framework.

#### Servers
A Support for both types of servers `HTTP Server and Servlet-based Server` is covered and whatever server can be used for running an application with JoyRest framework.

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
