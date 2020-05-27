[![Codacy Badge](https://api.codacy.com/project/badge/Grade/248ffd68076b40f9a43337a4c87f86cf)](https://www.codacy.com/manual/EarlyObject/topjava?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=EarlyObject/topjava&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/travis-ci/travis-web.svg?branch=master)](https://travis-ci.com/travis-ci/travis-web)

Java Enterprise Online Project 
===============================
Development of a fully functional Spring / JPA Enterprise application with authorization and role-based access rights using the most popular Java tools and technologies: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных Postgresql и HSQLDB.

![topjava_structure](https://user-images.githubusercontent.com/13649199/27433714-8294e6fe-575e-11e7-9c41-7f6e16c5ebe5.jpg)

### <a href="http://cuentacalorias.herokuapp.com/login" target=_blank>See the working project version</a>


The focus of this project is on how to solve numerous development problems in Spring/JPA, as well as structural (beautiful and reliable) java coding and application architecture.
A lot of attention is paid to code testing: the project has more than 100 JUnit tests.
Despite its relatively small size, the application is developed from scratch as a large project (for example, we use the Hibernate level 2 cache, configure Jackson to work with lazy loading of Hibernate, make converters for LocalDateTime types (Java 8 time API). Architectural patterns are analyzed: layers applications and how to properly divide the logic into layers when you need to use the Data Transfer Object, i.e., the output is a well-scalable template for a large project using all the technologies passed.
Lots of attention is paid to the details: population of the database, the use of transactionality, tests of services and REST controllers, tuning of EntityManagerFactory, choice of implementation of the connection pool. Particular attention is paid to working with the database: through Spring JDBC, Spring ORM and Spring Data Jpa.
The most popular frameworks currently in use are: Maven, Spring Security 4, together with Spring Security Test, the most convenient Spring Database Jpa project for working with the database, the logback logging library that implements SLF4J, commonly used by Bootstrap and jQuery.

Project plan:

The architecture of the project. Persistence.
Version control systems
Java 8: Lambda, Stream API
Maven Build Tool.
WAR. Tomcat web container. Servlets.
Logging
Standard libraries. Apache Commons, Guava
Application layers Create an application wireframe.
Spring Framework. Spring Context.
Testing through JUnit.
Spring test
Database. PostgreSQL Overview of NoSQL and Java persistence solution without ORM.
Setting up Database in IDEA.
Base initialization scripts. Spring Jdbc Template.
Spring: initializing and populating DB
ORM. Hibernate JPA
Testing JPA service through AssertJ
HSQLDB Support
Transactions
Maven and Spring Profiles
Connection pool
Spring data jpa
Hibernate Cache
Web development

Spring cache
Spring web
JSP, JSTL, i18n
Tomcat maven plugin. Jndi
Spring Web MVC
Spring internationalization
Testing Spring MVC
REST controllers
Testing REST controllers. Jackson
jackson-datatype-hibernate. Testing through matchers.
Testing through SoapUi. Utf-8
WebJars.
Bootstrap. jQuery datatables.
AJAX. jQuery Notifications.
Spring security
Spring binding / validation
Work with datatables through Ajax.
Spring security test
Customization of JSON (@JsonView) and validation (groups)
Encoding password
CSRF (project protection against cross-site request forgery)
form-login. Spring security taglib
Handler interceptor
Spring exception handling
Change of locale
JSON filtering through @JsonView
XSS Protection (Cross Site Scripting)
Deployment in Heroku
Localization of datatables, validation errors
404 error handling (NotFound)
Access to the AuthorizedUser
