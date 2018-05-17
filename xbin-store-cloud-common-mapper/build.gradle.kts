import org.gradle.jvm.tasks.Jar

description = "xbin-store-cloud-common-mapper"

dependencies {
    compile(project(":xbin-store-cloud-common-pojo"))
    compile("mysql:mysql-connector-java")
    compile("org.mybatis.spring.boot:mybatis-spring-boot-starter")
    compile("com.github.pagehelper:pagehelper-spring-boot-starter")
    compile("com.roncoo:roncoo-spring-boot-starter-druid")
    compile (fileTree(mapOf("dir" to "src/main/java", "include" to "*.xml")))
}

tasks{
    "jar"(Jar::class) {
        from("src/main/java") {
            include ("**/*.xml")
        }
    }
}