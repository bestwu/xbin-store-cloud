description = "xbin-store-cloud-web-admin"

dependencies {
    compile(project(":xbin-store-cloud-service-admin-api"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.cloud:spring-cloud-starter-eureka")
    compile("org.webjars.npm:apollo-client")
    compile("org.springframework.cloud:spring-cloud-starter-hystrix")
    compile("org.springframework.cloud:spring-cloud-starter-bus-amqp")
    compile("org.springframework.cloud:spring-cloud-starter-zipkin")
    compile("org.springframework.boot:spring-boot-starter-aop")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.retry:spring-retry")
    compile("com.piggsoft:beetl-spring-boot-starter")
    compile("commons-fileupload:commons-fileupload")
}