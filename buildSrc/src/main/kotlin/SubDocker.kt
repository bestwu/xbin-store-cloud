import com.bmuschko.gradle.docker.DockerExtension
import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerRemoveImage
import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import java.lang.Exception

/**
 *
 * @author Peter Wu
 * @since
 */
fun ignoredProjects(project: Project): Boolean {
    return project.name.contains("common") || project.name.contains("api")
}

class SubDocker : Plugin<Project> {

    private val onDockerError = object : Closure<Unit>(this, this) {
        @Suppress("unused")
        fun doCall(e: Exception?) {
            if (e != null)
                println(e)
        }
    }

    override fun apply(project: Project) {
        project.apply {
            plugin("com.bmuschko.docker-java-application")
            plugin("com.bmuschko.docker-remote-api")
        }

        project.extensions.configure(DockerExtension::class.java) {
            url = project.findProperty("docker.url") as? String?:"unix:///var/run/docker.sock"
            apiVersion = project.findProperty("docker.apiVersion") as? String

            javaApplication {
                baseImage = project.findProperty("docker.javaApplication.baseImage") as? String ?: "bestwu/java:8"
                maintainer = project.findProperty("docker.javaApplication.maintainer") as? String ?: ""
                port = (project.findProperty("${project.name}.server.port") as? String)?.toInt() ?: 8080
            }
        }

        project.tasks {
            addRule("Pattern: dockerStartContainer-<ID>") {
                val prefix = "dockerStartContainer-"
                if (!ignoredProjects(project) && startsWith(prefix) && length > prefix.length) {
                    val id = substring(prefix.length)
                    "dockerStartContainer-$id"(DockerStartContainer::class) {
                        group = "docker"
                        dependsOn("dockerCreateContainer-$id")

                        containerId = "${project.name}-$id"
                    }
                }
            }

            addRule("Pattern: dockerCreateContainer-<ID>") {
                val prefix = "dockerCreateContainer-"
                if (!ignoredProjects(project) && startsWith(prefix) && length > prefix.length) {
                    val id = substring(prefix.length)
                    "dockerCreateContainer-$id"(DockerCreateContainer::class) {
                        group = "docker"
                        dependsOn("dockerRemoveContainer-$id", "dockerBuildImage")

                        network = project.findProperty("docker.network") as? String ?: "cloud"
                        imageId = "${project.group}/${project.name}:${project.version}"
                        hostName = "${project.name}-$id"
                        containerName = "${project.name}-$id"
                        val port = project.extensions.findByType(DockerExtension::class.java)!!.javaApplication.port
                        portBindings = kotlin.collections.listOf("${port + id.toInt()}:$port")

                        if (id.toInt() > 0 && project.name == "xbin-store-cloud-eureka") {
                            setEnv("SPRING_PROFILES_ACTIVE=eureka-server-$id")
                        }
                    }
                }
            }

            addRule("Pattern: dockerStopContainer-<ID>") {
                val prefix = "dockerStopContainer-"
                if (!ignoredProjects(project) && startsWith(prefix) && length > prefix.length) {
                    val id = substring(prefix.length)
                    "dockerStopContainer-$id"(DockerStopContainer::class) {
                        group = "docker"

                        containerId = "${project.name}-$id"

                        onError = onDockerError
                    }
                }
            }

            addRule("Pattern: dockerRemoveContainer-<ID>") {
                val prefix = "dockerRemoveContainer-"
                if (!ignoredProjects(project) && startsWith(prefix) && length > prefix.length) {
                    val id = substring(prefix.length)

                    "dockerRemoveContainer-$id"(DockerRemoveContainer::class) {
                        group = "docker"
                        dependsOn("dockerStopContainer-$id")

                        force = true
                        containerId = "${project.name}-$id"

                        onError = onDockerError
                    }
                }
            }

            "dockerRemoveImage"(DockerRemoveImage::class) {
                group = "docker"

//                force = true
                imageId = "${project.group}/${project.name}:${project.version}"

                onError = onDockerError
            }

            "dockerBuildImage"{
                dependsOn("clean", "dockerRemoveImage")
            }
        }

    }
}