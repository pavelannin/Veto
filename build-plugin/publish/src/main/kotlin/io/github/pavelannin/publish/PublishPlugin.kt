package io.github.pavelannin.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishBasePlugin
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
        val ext = project.extensions.create<PublishExtension>("publish")

        val vanniktechMavenPublish = libs.findPlugin("vanniktech-mavenPublish").get().get()
        if (!project.plugins.hasPlugin(vanniktechMavenPublish.pluginId)) {
            project.plugins.apply(vanniktechMavenPublish.pluginId)
        }

        project.afterEvaluate {
            plugins.withType<MavenPublishBasePlugin> {
                extensions.configure<MavenPublishBaseExtension> {
                    publishToMavenCentral()
                    signAllPublications()

                    val artifact = ext.artifact.get()
                    coordinates(artifact.group, artifact.name, artifact.version)
                    pom {
                        name.set(artifact.name)
                        description.set("A lightweight, feature flagging library for Kotlin Multiplatform. Control your app's destiny with simple toggles, seamless cross-platform integration, and instant feature rollout. Command your code, enforce your rules, and say \"Veto\" to complexity.")
                        inceptionYear.set("2026")
                        url.set("https://github.com/pavelannin/Veto")
                        licenses {
                            license {
                                name.set("Apache-2.0")
                                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                name.set("Pavel Annin")
                                email.set("pavelannin.dev@gmail.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:github.com/pavelannin/Veto.git")
                            developerConnection.set("scm:git:ssh://github.com/pavelannin/Veto.git")
                            url.set("https://github.com/pavelannin/Veto/tree/master")
                        }
                    }
                }
            }
        }
    }
}
