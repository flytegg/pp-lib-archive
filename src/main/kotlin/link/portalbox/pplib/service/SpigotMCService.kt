package link.portalbox.pplib.service

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import link.portalbox.pplib.exception.PluginNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.api.PluginService
import link.portalbox.pplib.util.getSpigetJSON
import link.portalbox.pplib.util.isJarFile
import link.portalbox.pplib.util.moshiClient
import java.net.URL
/*
class SpigotMCService : PluginService {

    private val adapter = moshiClient.adapter<Map<String, Any>>(Map::class.java)

    override fun getPlugin(id: String): MarketplacePlugin {
        val spigetJSON = kotlin.runCatching {
            moshiClient.fromJson(getSpigetJSON(id))
        }.onFailure { throw PluginNotFoundException() }.getOrThrow()

        val iconUrl = spigetJSON["icon"].asJsonObject?.get("url")?.asString
        val imageUrl = if (iconUrl.isNullOrEmpty()) "https://cdn.discordapp.com/attachments/1065031876470906880/1105626560087736439/smallpreviewpluginportal.png" else "https://www.spigotmc.org/$iconUrl"

        var downloadURL: String = "https://api.spiget.org/v2/resources/$id/download";

        if (spigetJSON["file"].["externalUrl"]?.asString != null) {
            downloadURL = spigetJSON["file"].asJsonObject["externalUrl"]?.asString ?: ""

            if (!isJarFile(URL(downloadURL))) {
                downloadURL = adapter.fromJson(getPluginJSON(id)).let { it?.get("alternateDownload")?.asString } ?: ""
            }
        }

        var isPremium = false;
        runCatching {
            isPremium = spigetJSON["premium"].asBoolean
        }.onFailure { isPremium = false }


        return MarketplacePlugin(
            MarketplaceService.SPIGOTMC,
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

 */