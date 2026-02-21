plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

gradlePlugin {
    plugins {
        register("multiplatform") {
            id = libs.plugins.project.multiplatform.get().pluginId
            implementationClass = "io.github.pavelannin.multiplatform.MultiplatformPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.kotlin.multiplatform)
    compileOnly(libs.android.multiplatform.library)
}
