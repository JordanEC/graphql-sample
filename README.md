# graphql-sample
This is a sample spring boot application implementing graphql with the following tools:

+ graphql-java https://github.com/graphql-java/graphql-java
+ graphql-java-spring-boot-starter-webmvc https://github.com/graphql-java/graphql-java-spring
+ graphql-java-extended-scalars https://github.com/graphql-java/graphql-java-extended-scalars

##How to run

- Run docker dependency: docker-compose up -d mysql
- Run application: mvn spring-boot:run
- Create data and make queries with the collection "graphql_sample.postman_collection.json"