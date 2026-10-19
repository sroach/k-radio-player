package gy.roach.kradioplayer.server

import gy.roach.kradioplayer.getStations
import io.ktor.http.HttpStatusCode
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.net.URI

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

/**
 * Convert a station to JSON manually to avoid needing kotlinx-serialization dependency.
 */
private fun RadioStationToJson(station: gy.roach.kradioplayer.RadioStation): String {
    val types = station.type.joinToString(", ") { "\"$it\"" }
    return """{"index":${station.index},"url":"${station.url.replace("\"", "\\\"")}","label":"${station.label.replace("\"", "\\\"")}","type":[$types]}"""
}

fun Application.module() {
    routing {
        get("/api/stations") {
            val stations = getStations()
            val json = stations.joinToString(", ") { RadioStationToJson(it) }
            call.respondText(
                "[$json]",
                contentType = ContentType.Application.Json
            )
        }

        get("/api/stream") {
            val streamUrl = call.request.uri.substringAfter("url=").takeIf { it != call.request.uri }
                ?: run {
                    call.respondText(
                        "Missing 'url' query parameter",
                        status = HttpStatusCode.BadRequest
                    )
                    return@get
                }

            try {
                val url = URI.create(streamUrl).toURL()
                val connection = url.openConnection()
                connection.connectTimeout = 5000
                connection.readTimeout = 30000
                val bytes = connection.getInputStream().readBytes()
                call.respondText(
                    bytes.decodeToString(),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                call.respondText(
                    "Failed to proxy stream: ${e.message}",
                    status = HttpStatusCode.BadGateway
                )
            }
        }
    }
}
