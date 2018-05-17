description = "xbin-store-cloud-service-admin-api"

dependencies {
    compile(project(":xbin-store-cloud-service-admin-api"))
    compile(project(":xbin-store-cloud-common-mapper"))
    compile("org.springframework.cloud:spring-cloud-starter-eureka")
    compile("org.springframework.cloud:spring-cloud-starter-zipkin")
    compile("org.springframework.cloud:spring-cloud-starter-hystrix")
    compile("org.springframework.cloud:spring-cloud-starter-bus-amqp")
    compile("org.springframework.cloud:spring-cloud-starter-security")
    compile("org.springframework.cloud:spring-cloud-starter-oauth2")
    compile("org.webjars.npm:apollo-client")
}
