package link.portalbox.pplib.service

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonParser
import link.portalbox.pplib.type.Plugin
import link.portalbox.pplib.type.PluginService.SPIGOTMC
import link.portalbox.pplib.util.*
import java.net.URL

class Spiget(id: Int) : Plugin(SPIGOTMC, id.toString()) {

    var externalURL: String
    private val spigetJSON = JsonParser.parseString(getSpigetJSON(id)).asJsonObject
    private val portalBoxJSON = runCatching {
        JsonParser.parseString(getPortalBoxPluginJSON(id)).asJsonObject
    }.getOrNull()

    init {
        name = spigetJSON["name"].asString
        description = spigetJSON["tag"].asString
        onlineVersion = spigetJSON["version"].asJsonObject["id"].asString
        downloads = spigetJSON["downloads"].asInt
        updateDate = spigetJSON["updateDate"].asLong
        price = spigetJSON["price"].asDouble
        rating = spigetJSON["rating"].asJsonObject["average"].asDouble
        ratingCount = spigetJSON["rating"].asJsonObject["count"].asInt
        supportedVersions = Gson().fromJson(
            spigetJSON["testedVersions"],
            object : TypeToken<LinkedHashSet<String>>() {}.type
        )
        spigetJSON.getAsJsonArray("versions").forEach {
            releaseVersions.add(it.asJsonObject["id"].asString)
        }

        premium = runCatching {
            spigetJSON["premium"].asBoolean
        }.getOrDefault(false)

        externalURL = runCatching {
            spigetJSON["file"].asJsonObject["externalUrl"].asString
        }.toString()

        val url = spigetJSON["icon"].asJsonObject["url"].asString
        iconURL = if (url.isEmpty()) {
            "https://i.imgur.com/V9jfjSJ.png"
        } else {
            "https://www.spigotmc.org/$url"
        }

        if (portalBoxJSON != null) {
            runCatching {
                downloadURL = getURL(portalBoxJSON!!["alternateDownload"].asString)

                portalBoxJSON["files"].asJsonObject.entrySet().forEach { entry ->
                    files!![entry.key] = entry.value.asString
                }
            }
        }

        if (downloadURL == null || downloadURL.toString().isEmpty()) {
            downloadURL = getSpigetDownloadURL()
        }
    }

    /**
     * Gets the download URL for the plugin.
     *
     * If the plugin has an external URL set and it is a direct download or a JAR file, it will be returned. Otherwise, a URL
     * to download the plugin from SpigotMC's API will be returned.
     *
     * @return The download URL for the plugin, or null if no suitable download URL could be found.
     */
    fun getSpigetDownloadURL(): URL? {
        externalURL.let { url ->
            if (isDirectDownload(url) || isJarFile(getURL(url)!!)) {
                return getURL(url)
            }
        }
        return getURL("https://api.spiget.org/v2/resources/$id/download")
    }

}