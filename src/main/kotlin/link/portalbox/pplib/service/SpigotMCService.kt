package link.portalbox.pplib.service

import com.google.gson.JsonParser
import link.portalbox.pplib.exception.PluginNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.PluginService
import link.portalbox.pplib.util.getPluginJSON
import link.portalbox.pplib.util.getSpigetJSON
import link.portalbox.pplib.util.isDirectDownload
import link.portalbox.pplib.util.isJarFile
import java.net.URL

class SpigotMCService : PluginService {
    override fun getPlugin(id: String): MarketplacePlugin {
        val spigetJSON = kotlin.runCatching {
            getSpigetJSON(id)
        }.onFailure { throw PluginNotFoundException() }.getOrThrow()

        val iconUrl = spigetJSON["icon"].asJsonObject?.get("url")?.asString
        val imageUrl = if (iconUrl.isNullOrEmpty()) "https://i.imgur.com/V9jfjSJ.png" else "https://www.spigotmc.org/$iconUrl"

        var downloadURL: String = "https://api.spiget.org/v2/resources/$id/download";

        if (spigetJSON["file"].asJsonObject["externalUrl"]?.asString != null) {
            downloadURL = spigetJSON["file"].asJsonObject["externalUrl"]?.asString ?: ""

            if (!isJarFile(URL(downloadURL))) {
                downloadURL = getPluginJSON(id)?.let { it["alternateDownload"]?.asString } ?: ""
            }
        }

        var isPremium = false;
        runCatching {
            isPremium = spigetJSON["premium"].asBoolean
        }.onFailure { isPremium = false }


        return MarketplacePlugin(
            spigetJSON["id"].asString,
            spigetJSON["name"].asString,
            spigetJSON["tag"].asString,
            spigetJSON["downloads"].asInt,
            spigetJSON["price"].asDouble,
            spigetJSON["rating"].asJsonObject["average"].asDouble,
            imageUrl,
            spigetJSON["versions"].asJsonArray[0].asJsonObject["id"].asString,
            downloadURL,
            isPremium,
        )
    }
}
