package com.example.server

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val imageUrl: String,
    var liked: Boolean = false
)

private val dataFile = File("artists_database.json")

private const val API_KEY = "MojeTajneHaslo123"

private fun createImageUrl(fileName: String): String = "http://10.67.14.16:8080/static/$fileName"
private fun getInitialArtists(): List<Artist> {
    return listOf(
        Artist(id = "mac_miller", name = "Mac Miller", genre = "Hip Hop", imageUrl = createImageUrl("mac_miller.png")),
        Artist(id = "joji", name = "Joji", genre = "Alt R&b", imageUrl = createImageUrl("joji.png")),
        Artist(id = "kendrick_lamar", name = "Kendrick Lamar", genre = "Hip Hop", imageUrl = createImageUrl("kendrick_lamar.png")),
        Artist(id = "the_weeknd", name = "The Weeknd", genre = "R&B / Pop", imageUrl = createImageUrl("the_weeknd.png")),
        Artist(id = "travis_scott", name = "Travis Scott", genre = "Trap", imageUrl = createImageUrl("travis_scott.png")),
        Artist(id = "lana_del_rey", name = "Lana Del Rey", genre = "Alt Pop", imageUrl = createImageUrl("lana_del_rey.png")),
        Artist(id = "post_malone", name = "Post Malone", genre = "Pop / Rock", imageUrl = createImageUrl("post_malone.png")),
        Artist(id = "asap_rocky", name = "A\$AP Rocky", genre = "Hip Hop", imageUrl = createImageUrl("asap_rocky.png")),
        Artist(id = "tyler_the_creator", name = "Tyler, The Creator", genre = "Alternative", imageUrl = createImageUrl("tyler_the_creator.png")),
        Artist(id = "frank_ocean", name = "Frank Ocean", genre = "R&B / Soul", imageUrl = createImageUrl("frank_ocean.png")),
        Artist(id = "billie_eilish", name = "Billie Eilish", genre = "Pop", imageUrl = createImageUrl("billie_eilish.png")),
        Artist(id = "kanye_west", name = "Kanye West", genre = "Hip Hop", imageUrl = createImageUrl("kanye_west.png")),
        Artist(id = "childish_gambino", name = "Childish Gambino", genre = "Polymath", imageUrl = createImageUrl("childish_gambino.png")),
        Artist(id = "drake", name = "Drake", genre = "Hip Hop / Pop", imageUrl = createImageUrl("drake.png")),
        Artist(id = "sza", name = "SZA", genre = "R&B", imageUrl = createImageUrl("sza.png"))
    )
}

val artistsList: MutableList<Artist> = if (dataFile.exists()) {
    try {
        val fileContent = dataFile.readText()
        Json.decodeFromString<List<Artist>>(fileContent).toMutableList()
    } catch (e: Exception) {
        e.printStackTrace()
        getInitialArtists().toMutableList()
    }
} else {
    getInitialArtists().toMutableList()
}

fun saveArtistsToFile() {
    try {
        val jsonString = Json.encodeToString(artistsList.toList())
        dataFile.writeText(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun main() {
    // 🔥 POPRAWKA: .start(wait = true) wywołujemy bezpośrednio na embeddedServer wewnątrz funkcji main
    embeddedServer(Netty, port = 8080, host="0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }

        routing {
            staticFiles("/static", File("static"))

            route("/api") {

                // Pobieranie listy artystów (GET)
                get("/artists") {
                    val userKey = call.request.headers["X-API-Key"]
                    if (userKey != API_KEY) {
                        call.respond(HttpStatusCode.Unauthorized, "Brak autoryzacji lub błędny klucz API!")
                        return@get
                    }

                    call.respond(artistsList)
                }

                // Polubienie artysty (POST)
                post("/artists/{id}/toggle-liked") {
                    val userKey = call.request.headers["X-API-Key"]
                    if (userKey != API_KEY) {
                        call.respond(HttpStatusCode.Unauthorized, "Brak autoryzacji lub błędny klucz API!")
                        return@post
                    }

                    val id = call.parameters["id"]
                    val artist = artistsList.find { it.id == id }

                    if (artist != null) {
                        artist.liked = !artist.liked
                        saveArtistsToFile()
                        call.respond(HttpStatusCode.OK, artist)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Nie znaleziono wykonawcy")
                    }
                }
            }
        }
    }.start(wait = true)
} // Klamra zamykająca funkcję main znajduje się teraz na samym końcu