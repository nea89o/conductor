package moe.nea.conductor

import io.ktor.http.HttpStatusCode

abstract class UserError(message: String) : Exception(message), HasCustomStatusCode {
	override val code: HttpStatusCode
		get() = HttpStatusCode.BadRequest
}