package moe.nea.conductor

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.serialization.ContentConverter
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charset
import java.io.StringWriter
import kotlin.reflect.full.findAnnotation


class CustomHtmlConverter(
	val mustacheFactory: MustacheFactory = DefaultMustacheFactory("pages")
) : ContentConverter {
	class SentHtmlToServer : UserError("Sent HTML to server.") {
		override val code: HttpStatusCode
			get() = HttpStatusCode.NotAcceptable
	}
	override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
		throw SentHtmlToServer()
	}

	override suspend fun serializeNullable(
		contentType: ContentType,
		charset: Charset,
		typeInfo: TypeInfo,
		value: Any?
	): OutgoingContent? {
		require(contentType == ContentType.Text.Html)
		val page = typeInfo.type.findAnnotation<HtmlPage>() ?: error("This page is not available in the HTML view")
		if (value == null) {
			TODO("404 for null values")
		}

		val writer = StringWriter()
		mustacheFactory.compile(page.path + ".hbs").execute(writer, value)

		return TextContent(text = writer.toString(), contentType)
	}
}
