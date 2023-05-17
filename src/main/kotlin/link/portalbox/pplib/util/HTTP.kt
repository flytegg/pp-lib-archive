package link.portalbox.pplib.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

val okHttpClient = OkHttpClient.Builder().build()

fun getStringFromURL(url: String): String {
    return okHttpClient.newCall(
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
fun getPortalBoxPluginJSON(id: Int): String {
    return getStringFromURL("https://api.portalbox.link/plugins/$id")
}

/**
 * Fetches the JSON for the given Spiget resource ID from the Spiget API.
 * @param id the ID of the resource to fetch
 * @return the JSON string for the resource, or null if an error occurred
 */
fun getSpigetJSON(id: String): String {
    return getStringFromURL("https://api.spiget.org/v2/resources/$id")
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

fun isJarFileDownload(url: String): Boolean {
    val request = Request.Builder()
        .url(getFinalRedirect(url))
        .build()
    var response: Response? = null
    try {
        response = okHttpClient.newCall(request).execute()
        val contentType = response.header("Content-Type")
        val contentDisposition = response.header("Content-Disposition")
        return (contentType != null && contentType == "application/java-archive")
                || (contentDisposition != null && contentDisposition.contains(".jar"))
                || (url.endsWith(".jar"))
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        response?.close()
    }
    return false
}

fun getFinalRedirect(url: String): String {
    var request = Request.Builder().url(url).head().build()
    var response = okHttpClient.newCall(request).execute()

    while (response.isRedirect) {
        response.close()
        val redirectUrl: String = response.header("Location") ?: "none"
        request = Request.Builder().url(redirectUrl).head().build()
        response = okHttpClient.newCall(request).execute()
    }

    return response.request.url.toString()
}