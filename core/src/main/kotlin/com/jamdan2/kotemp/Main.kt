package com.jamdan2.kotemp

import kotemp.utils.createHtmlFile
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.document
import kotlinx.html.dom.serialize

class KotempConfig {
    var settingsConfig = SettingsConfig()
    var routes = mutableMapOf<String, TagConsumer<*>.() -> Unit>()

    fun settings(builder: SettingsConfig.() -> Unit) {
        settingsConfig = SettingsConfig().apply(builder)
    }

    fun finalize() = Kotemp(settingsConfig.finalize(), routes)
}

class Kotemp(
    val settings: Settings,
    val routes: Map<String, TagConsumer<*>.() -> Unit>
)

class SettingsConfig {
    var port = 9999
    var rootFolder = "build"
    var production = true
    var development = true
    var watch: String? = null

    fun finalize() = Settings(port, rootFolder, development, production, watch)
}

class Settings(
    val port: Int,
    val rootFolder: String,
    val development: Boolean,
    val production: Boolean,
    val watch: String?
)

fun kotemp(builder: KotempConfig.() -> Unit) {
    val kotemp = KotempConfig().apply(builder).finalize()
    val settings = kotemp.settings

    kotemp.routes.forEach {
        val content = document { append(it.value) }.serialize()
        if (settings.production) {
            val file = createHtmlFile(settings.rootFolder, it.key)
            if (!file.exists()) file.mkdirs()
            file.writeText(content)
        }
    }
}
