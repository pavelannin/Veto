package io.github.pavelannin.publish

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

open class PublishExtension {
    lateinit var artifact: Provider<MinimalExternalModuleDependency>
}
