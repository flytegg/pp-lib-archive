package link.portalbox.pplib.service

import com.google.gson.JsonParser
import link.portalbox.pplib.exception.PluginNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.PluginService
import link.portalbox.pplib.util.getSpigetJSON
import link.portalbox.pplib.util.isDirectDownload

class SpigotMCService : PluginService {
    override fun getPlugin(id: String): MarketplacePlugin {
        val spigetJSON = kotlin.runCatching {
            JsonParser.parseString(getSpigetJSON(id)).asJsonObject
        }.onFailure { throw PluginNotFoundException() }.getOrThrow()

        return MarketplacePlugin(
            spigetJSON["id"].asString,
            spigetJSON["name"].asString,
            spigetJSON["tag"].asString,
            spigetJSON["downloads"].asInt,
            spigetJSON["price"].asDouble,
            spigetJSON["rating"].asJsonObject["count"].asInt,
            spigetJSON["rating"].asJsonObject["average"].asDouble,
            spigetJSON["versions"].asJsonArray[0].asJsonObject["id"].asString,
            spigetJSON["author"].asJsonObject["id"].asString,
            spigetJSON["file"].asJsonObject["externalUrl"]?.asString ?: "null",
            spigetJSON["file"].asJsonObject["externalUrl"]?.asString?.let { isDirectDownload(it) } ?: false,
            spigetJSON["premium"].asBoolean,
        )
    }
}