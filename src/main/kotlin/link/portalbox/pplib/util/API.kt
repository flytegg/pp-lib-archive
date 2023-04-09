package link.portalbox.pplib.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

const val BASE_DOMAIN = "https://api.portalbox.link"

/**
 * Retrieves the latest version of the plugin from the API.
 *
 * @return the latest version of the plugin
 */
fun getLatestPPVersion(): String? {
    return getPPVersions()?.values?.toTypedArray()?.get(0);
}

/**
 * Retrieves a map of PP versions from the API.
 *
 * @return a LinkedHashMap containing the plugin hash and its version.
 */
fun getPPVersions(): LinkedHashMap<String, String>? {
    return Gson().fromJson(
        JsonParser.parseString(getJSONFromURL("$BASE_DOMAIN/versions")).asJsonObject.get("versions"),
        object : TypeToken<LinkedHashMap<String, String>>() {}.type
    )
}

/**
 * Retrieves the plugin index from the API.
 * @return A JSON string representing the plugin index.
 */
fun getPluginIndex(): String? {
    return getJSONFromURL("$BASE_DOMAIN/plugins")
}
