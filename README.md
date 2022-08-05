# 模块

## primal
依赖：graphql-java

参考：https://www.graphql-java.com/documentation/getting-started

原生方式，需要自己写schema和解析。

## spqr
依赖：spqr

参考：https://www.baeldung.com/spring-boot-graphql-spqr

使用注解@GraphQLQuery、@GraphQLMutation、@GraphQLArgument等，以及从Java类中扫描schema，简化了代码。

## spqrstarter
依赖：graphql-spqr-spring-boot-starter

参考：https://www.baeldung.com/spring-boot-graphql-spqr

自带controller，默认请求路径 /graphql，不需要自己写一层controller，进一步简化了代码。
