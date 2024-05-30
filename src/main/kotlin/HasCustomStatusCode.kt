package moe.nea.conductor

import io.ktor.http.HttpStatusCode

interface HasCustomStatusCode {
	val code: HttpStatusCode
}