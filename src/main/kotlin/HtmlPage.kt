package moe.nea.conductor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class HtmlPage(val path: String)
