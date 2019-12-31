package kotemp.routing

import com.jamdan2.kotemp.KotempConfig
import kotemp.templates.Template
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer

class RouteConfig(vararg routes: Pair<String, TagConsumer<*>.() -> Unit>) {
    internal val routes = mutableMapOf(*routes)

    fun html(path: String, builder: TagConsumer<*>.() -> Unit) {
        routes += path to builder
    }

    fun html(path: String, template: Template, builder: FlowContent.() -> Unit) {
        routes += path to { c: TagConsumer<*> -> c.template(builder) }
    }
}

fun KotempConfig.routing(builder: RouteConfig.() -> Unit) {
    routes.plusAssign((RouteConfig().apply(builder).routes))
}
