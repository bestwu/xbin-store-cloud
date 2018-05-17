description = "xbin-store-cloud-service-item"

dependencies {
    compile(project(":xbin-store-cloud-service-item-api"))
    compile(project(":xbin-store-cloud-common-mapper"))
    compile("org.springframework.cloud:spring-cloud-starter-eureka")
    compile("org.springframework.cloud:spring-cloud-starter-zipkin")
    compile("org.springframework.cloud:spring-cloud-starter-hystrix")
    compile("org.springframework.cloud:spring-cloud-starter-bus-amqp")
    compile("org.webjars.npm:apollo-client")
//        compile("org.springframework.data:spring-data-redis")
    compile("org.springframework.boot:spring-boot-starter-redis")
    compile("org.freemarker:freemarker:2.3.9")
}