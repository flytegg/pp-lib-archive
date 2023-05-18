package link.portalbox.pplib.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.readValue
import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.api.PostError
import link.portalbox.pplib.type.api.RequestPlugin
import link.portalbox.pplib.type.VersionType
import link.portalbox.pplib.type.api.APIPlugin
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

var BASE_DOMAIN = "https://api.portalbox.link"

/**
 * Retrieves the latest version of the plugin from the API.
 *
 * @return the latest version of the plugin
 */
fun getLatestPPVersion(): String {
    return getPPVersions().values.toTypedArray().lastIndex.let { getPPVersions().values.toTypedArray()[it] }
}

fun getLatestVersion(version: String): VersionType {
    val latestVersion = getLatestPPVersion()

    val currentVersionParts = version.split(".")
    val latestVersionParts = latestVersion.split(".")

    return when {
        latestVersionParts[0].toInt() > currentVersionParts[0].toInt() -> VersionType.MAJOR
        latestVersionParts[1].toInt() > currentVersionParts[1].toInt() -> VersionType.MINOR
        latestVersionParts[2].toInt() > currentVersionParts[2].toInt() -> VersionType.PATCH
        latestVersion == version -> VersionType.LATEST
        else -> VersionType.UNRELEASED
    }
}

/**
 * Retrieves a map of PP versions from the API.
 *
 * @return a LinkedHashMap containing the plugin hash and its version.
 */
fun getPPVersions(): LinkedHashMap<String, String> {
    return objectMapper.readValue("$BASE_DOMAIN/versions", object : TypeReference<LinkedHashMap<String, String>>() {})
}

///**
// * Retrieves the plugin index from the API.
// * @return A JSON string representing the plugin index.
// */
//fun getPluginIndex(): JsonObject {
//    return objectMapper.parse("$BASE_DOMAIN/v2/plugins")
//}

/**
 * Retrieves the plugin JSON from the API.
 * @param id The ID of the plugin to retrieve.
 * @return an APIPlugin object representing the specified id
 */
fun getAPIPlugin(id: String): APIPlugin {
    return objectMapper.readValue<APIPlugin>(getStringFromURL("$BASE_DOMAIN/v2/plugins/$id"))
}

/**
 * Requests a plugin from the developers via a RestAPI post request.
 * @param requestPlugin The plugin to request.
 */
fun requestPlugin(requestPlugin: RequestPlugin): String {
    val client = okHttpClient
    val request = Request.Builder()
        .url("$BASE_DOMAIN/v2/plugins")
        .method(
            "POST",
            objectMapper.writeValueAsString(requestPlugin).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body.string()
    }
}

fun sendError(postError: PostError): String {
    val client = okHttpClient
    val request = Request.Builder()
        .url("$BASE_DOMAIN/errors")
        .method(
            "POST",
            objectMapper.writeValueAsString(postError).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body.string()
    }
}

fun searchPlugins(filter: String): ArrayList<String> {
    return objectMapper.readValue(getStringFromURL("$BASE_DOMAIN/v2/plugins/search?filter=$filter"), ArrayList<String>().javaClass)
}

fun getPluginFromName(filter: String): MarketplacePlugin {
    if (filter.contains(":")) {
        separateServiceAndName(filter).let {
            return MarketplacePluginManager.getPlugin(it.first, it.second)
        }
    }

    return  MarketplacePluginManager.getPlugin(getPluginIdFromName(filter))
}

fun getPluginIdFromName(name: String): String {
    return getStringFromURL("$BASE_DOMAIN/v2/plugins/getIdFromName?name=$name")
}

fun separateServiceAndName(id: String): Pair<MarketplaceService, String> {
    val split = id.split(":")
    var pluginName = split[1]

    if (split.size == 3) {
        pluginName = "${split[1]}:${split[2]}"
    }

    return Pair(MarketplaceService.valueOf(split[0].uppercase()), pluginName)
}


fun startErrorCatcher(postError: PostError) {
    defaultPostError = postError
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        e.printStackTrace()
        sendError(
            PostError(
                defaultPostError.pluginVersion,
                defaultPostError.mcVersion,
                e.stackTraceToString(),
            )
        )
    }
}