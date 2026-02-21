plugins {
    alias(libs.plugins.project.multiplatform)
    alias(libs.plugins.project.publish)
}

kotlin {
    android {
        namespace = "io.github.pavelannin.veto.core"
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.coroutines.core)
        }
    }
}

publish {
    artifact = libs.project.core
}
