package link.portalbox.pplib.util

const val BASE_DOMAIN = "https://api.portalbox.link"

/**
 * Retrieves the latest version of the plugin from the API.
 *
 * @return the latest version of the plugin
 */
fun getPPVersion(): String? {
    return getJSONFromURL("$BASE_DOMAIN/version")
}

/**
 * Retrieves the plugin index from the API.
 * @return A JSON string representing the plugin index.
 */
fun getPluginIndex(): String? {
    return getJSONFromURL("$BASE_DOMAIN/index")
}
