package link.portalbox.pplib.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import link.portalbox.pplib.type.VersionType

//const val BASE_DOMAIN = "https://api.portalbox.link"
const val BASE_DOMAIN = "localhost:8080"


/**
 * Retrieves the latest version of the plugin from the API.
 *
 * @return the latest version of the plugin
 */
fun getLatestPPVersion(): String? {
    return getPPVersions()?.values?.toTypedArray()?.lastIndex?.let { getPPVersions()?.values?.toTypedArray()?.get(it) }
}

fun getLatestVersion(version: String): VersionType {
    val latestVersion = getLatestPPVersion() ?: return VersionType.UNRELEASED

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
fun getPPVersions(): LinkedHashMap<String, String>? {
    return Gson().fromJson(
        getJSONFromURL("$BASE_DOMAIN/versions").asJsonObject.get("versions"),
        object : TypeToken<LinkedHashMap<String, String>>() {}.type
    )
}

/**
 * Retrieves the plugin index from the API.
 * @return A JSON string representing the plugin index.
 */
fun getPluginIndex(): JsonObject {
    println("$BASE_DOMAIN/v2/plugins")
    return getJSONFromURL("$BASE_DOMAIN/v2/plugins")
}

/**
 * Retrieves the plugin JSON from the API.
 * @param id The ID of the plugin to retrieve.
 * @return A JSON string representing the plugin.
 */
fun getPluginJSON(id: String): JsonObject {
    return getJSONFromURL("$BASE_DOMAIN/v2/plugins/$id")
}
