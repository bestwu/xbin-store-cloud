import com.bmuschko.gradle.docker.DockerExtension
import com.bmuschko.gradle.docker.tasks.AbstractDockerRemoteApiTask
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerRemoveImage
import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer
import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import java.lang.Exception
import kotlin.reflect.KClass

/**
 *
 * @author Peter Wu
 * @since
 */
class Docker : Plugin<Project> {

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
            url = project.findProperty("docker.url") as? String ?: "unix:///var/run/docker.sock"
            apiVersion = project.findProperty("docker.apiVersion") as? String

            javaApplication {
                baseImage = project.findProperty("docker.javaApplication.baseImage") as? String ?: "bestwu/java:8"
                maintainer = project.findProperty("docker.javaApplication.maintainer") as? String ?: ""
            }
        }

        fun getAllDockerTasks(dockerTask: KClass<out AbstractDockerRemoteApiTask>, num: Int = 1): Array<String> {
            val dockerTasks = mutableSetOf<String>()
            project.tasks.filter { dockerTask.isInstance(it) }.forEach {
                dockerTasks.add(it.name)
            }
            project.subprojects.filter { !ignoredProjects(project) }.forEach { p ->
                p.tasks.filter { dockerTask.isInstance(it) }.forEach {
                    dockerTasks.add(":${p.name}:${it.name}")
                }
                if (num > 1) {
                    0.rangeTo(num).forEach {
                        dockerTasks.add(":${p.name}:${dockerTask.simpleName?.decapitalize()}-$it")
                    }
                }
            }
            return dockerTasks.toTypedArray()
        }


        project.tasks {
            "dockerStopAllContainers" {
                group = "app cloud"
                dependsOn(getAllDockerTasks(DockerStopContainer::class, 3))
            }

            "dockerRemoveAllContainers" {
                group = "app cloud"
                dependsOn(getAllDockerTasks(DockerRemoveContainer::class, 3))
            }

            "dockerRemoveAllImages"{
                group = "app cloud"
                dependsOn(getAllDockerTasks(DockerRemoveImage::class))
            }

            "cloud"{
                group = "app cloud"
                dependsOn()
            }
        }


    }
}