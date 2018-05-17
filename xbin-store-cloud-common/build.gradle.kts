description = "xbin-store-cloud-common"

dependencies {
    compileOnly("javax.servlet:javax.servlet-api")
    compileOnly("redis.clients:jedis")
    compile("org.apache.commons:commons-lang3")
    compile("commons-lang:commons-lang")
    compile("com.alibaba:fastjson")
    compile("net.oschina.zcx7878:fastdfs-client-java")
    compile("org.slf4j:slf4j-api")
    compile("io.springfox:springfox-swagger-ui")
    compile("io.springfox:springfox-swagger2")
}