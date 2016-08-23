# JoyRest
##### Lightweight and Lambda-based REST Framework
### Official pages - [http://petrbouda.github.io/joyrest/](http://petrbouda.github.io/joyrest/)
### About
JoyRest is a lightweight and convenient tool for **_creating REST services_** by a concise way. The main intention is to provide framework which is simply extensible for different types of DI and servers. JoyRest is purely developed using **_Java programming language 8 and with lambdas_**. Thank to this technology the framework is able to provide very clear API which allows you to defined your endpoints through a **_functional way_**.

### Strongly typed and strict framework
Maybe someone may find strict declaring of Request/Response types as a verbose and annoying activity, but this is the biggest advantage of JoyRest. Since this strict defining types allows to JoyRest resolve Writes/Readers during an application bootstrapping and not during a Request processing. This is a trade-off between an additional written code and a performance impact.

`JoyRest has chose the better performance !!`

**_In addition_** the strongly-typed nature of JoyRest allows us to use some `Automatic Documentation Generator` because all information about routes are available immediately after the creation of dependency injection context. 
Don't forget to try out JoyRest-Maven-Plugin which provides extensible functionality of generation documentation.

### Lambda-based approach
There are three ways to define a body of the given route. Even if it's the `lambda-based approach` of defining routes the most favourite concept, it also worth mentioning other two approaches. For the sake of simplicity and conciseness my favourite is Lambda! 

`Try it out or look at the Examples on GitHub`

- lambda-based approach
- method reference
- implementing BiConsumer interface

### Clear java interface configuration
JoyRest prefers the way without or at least eliminate a number of annotations used during the declaring and bootstrapping the application. Annotations can be sometimes very confusing and make harder following debugging and understanding of the code underneath.

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
