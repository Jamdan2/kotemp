package kotemp.templates

import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer

typealias Template = TagConsumer<*>.(content: FlowContent.() -> Unit) -> Unit

fun template(builder: Template) = builder
