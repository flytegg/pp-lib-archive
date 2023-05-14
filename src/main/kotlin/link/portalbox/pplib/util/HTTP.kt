package link.portalbox.pplib.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Retrieves JSON content from the specified URL.
 *
 * @param urlString The URL from which to retrieve the JSON content.
 * @return The JSON content as a String, or null if an error occurs.
 */
fun getJsonObjectFromURL(url: String) : JsonObject {
    return getJsonFromURL(url).asJsonObject
}

fun getJsonFromURL(url: String): JsonElement {
    return JsonParser.parseString(getStringFromURL(url))
}

fun getStringFromURL(url: String): String {
    return getClient().newCall(
        Request.Builder()
            .url(url)
            .header("User-Agent", "portal-box/pp-lib")
            .build()
    ).execute().body.string()
}



/**
 * Returns the JSON content of a PortalBox plugin with the given ID.
 *
 * @param id the ID of the plugin to retrieve
 * @return the JSON content of the plugin, or null if an error occurs
 */
fun getPortalBoxPluginJSON(id: Int): JsonObject {
    return getJsonObjectFromURL("https://api.portalbox.link/plugins/$id")
}

/**
 * Fetches the JSON for the given Spiget resource ID from the Spiget API.
 * @param id the ID of the resource to fetch
 * @return the JSON string for the resource, or null if an error occurred
 */
fun getSpigetJSON(id: String): JsonObject {
    return getJsonObjectFromURL("https://api.spiget.org/v2/resources/$id")
}

/**
 * Checks if the given URL refers to a direct download link.
 *
 * @param urlString the URL to check.
 * @return `true` if the URL is a direct download link, `false` otherwise.
 */
fun isDirectDownload(urlString: String): Boolean {
    return runCatching {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "HEAD"
        connection.connect()

        val contentType = connection.contentType
        val contentLength = connection.contentLength

        connection.disconnect()

        contentType == "application/octet-stream" && contentLength != -1
    }.getOrDefault(false)
}

/**
 * Converts a GitHub repository URL to its corresponding API URL.
 * @param url the GitHub repository URL to convert.
 * @return the corresponding GitHub API URL.
 */
fun convertGitHubToAPI(url: String): String {
    val split = url.split("/")
    return "https://api.github.com/repos/${split[3]}/${split[4]}/releases/latest"
}

/**
 * Returns a URL object for the given URL string.
 *
 * @param url the URL string to convert to a URL object
 * @return a URL object or null if the conversion failed
 */
fun getURL(url: String?): URL? {
    return runCatching {
        URL(url)
    }.onFailure {
        // log(Level.WARNING, "Failed to get URL from string: $url", it)
    }.getOrNull()
}

/**
 * Checks if the given URL points to a JAR file.
 * @param url the URL to check
 * @return true if the URL points to a JAR file, false otherwise
 */
fun isJarFile(url: URL?): Boolean {
    return runCatching {
        val connection = url?.openConnection() as HttpURLConnection
        connection.instanceFollowRedirects = false
        connection.requestMethod = "HEAD"
        connection.connect()
        val responseCode = connection.responseCode
        if (responseCode in 300..399) {
            val redirectUrl = connection.getHeaderField("Location")
            if (redirectUrl != null) {
                val newUrl = URL(redirectUrl)
                //println("$url -> $newUrl")
                return isJarFile(newUrl)
            }
        }
        if (isDirectDownload(url.toString())) return@runCatching true

        val contentType = connection.contentType
        val contentDisposition = connection.getHeaderField("Content-Disposition")
        connection.disconnect()

        (contentType != null && contentType == "application/java-archive")
                || (contentDisposition != null && contentDisposition.lowercase().contains(".jar"))
                || (url.toString().lowercase().endsWith(".jar"))
    }.getOrDefault(false)
}

fun getClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}