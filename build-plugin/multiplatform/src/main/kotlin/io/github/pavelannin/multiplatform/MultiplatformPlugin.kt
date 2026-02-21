package io.github.pavelannin.multiplatform

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        val kotlinMultiplatformPlugin = libs.findPlugin("kotlin-multiplatform").get().get()
        if (!project.plugins.hasPlugin(kotlinMultiplatformPlugin.pluginId)) {
            project.plugins.apply(kotlinMultiplatformPlugin.pluginId)
        }

        val androidKotlinMultiplatformLibraryPlugin = libs
            .findPlugin("android-kotlin-multiplatform-library")
            .get()
            .get()
        if (!project.plugins.hasPlugin(androidKotlinMultiplatformLibraryPlugin.pluginId)) {
            project.plugins.apply(androidKotlinMultiplatformLibraryPlugin.pluginId)
        }

        with(project.extensions.getByType<KotlinMultiplatformExtension>()) {
            explicitApi()
            jvmToolchain(17)

            jvm()
            iosX64()
            iosArm64()
            iosSimulatorArm64()

            with(extensions.getByType<KotlinMultiplatformAndroidLibraryExtension>()) {
                compileSdk {
                    val compileSdk = libs.findVersion("android-compileSdk").get()
                    version = release(compileSdk.requiredVersion.toInt())
                }
                minSdk {
                    val minSdk = libs.findVersion("android-minSdk").get()
                    version = release(minSdk.requiredVersion.toInt())
                }
            }

            sourceSets.all {
                languageSettings.enableLanguageFeature("ContextParameters")
                languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
            }
        }
    }
}
