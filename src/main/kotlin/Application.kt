package moe.nea.conductor

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main() {
	embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	install(ContentNegotiation) {
		json(Json {
			isLenient = true
		})
		register(ContentType.Text.Html, CustomHtmlConverter())
	}

	configureSecurity()
	configureRouting()
}

fun Application.configureSecurity() {
	// TODO: configure security
}

@Serializable
@HtmlPage("error")
data class ErrorPage(val message: String)


fun Application.configureRouting() {
	install(StatusPages) {
		exception<Throwable> { call, cause ->
			val code = when (cause) {
				is HasCustomStatusCode -> cause.code
				else -> HttpStatusCode.InternalServerError
			}
			call.respond(status = code, ErrorPage(cause.message ?: cause.javaClass.simpleName))
		}
	}

	routing {
		get("/") {
			call.respond(Hello("World"))
		}
		staticResources("/static", "/static")
	}
}

@Serializable
@HtmlPage("hello")
data class Hello(val name: String)
