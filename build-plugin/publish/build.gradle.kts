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
        register("publish") {
            id = libs.plugins.project.publish.get().pluginId
            implementationClass = "io.github.pavelannin.publish.PublishPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.vanniktech.mavenPublish)
}
