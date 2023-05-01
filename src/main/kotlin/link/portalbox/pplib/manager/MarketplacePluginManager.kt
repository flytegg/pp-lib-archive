package link.portalbox.pplib.manager

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.gson.Gson
import com.google.gson.JsonObject
import link.portalbox.pplib.exception.ServiceNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.PluginService
import link.portalbox.pplib.util.getPluginIndex
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import link.portalbox.pplib.service.SpigotMCService


object MarketplacePluginManager {

    // BiMap<Id, String>
    var marketplaceCache: BiMap<String, String> = HashBiMap.create()

    private val services: MutableMap<MarketplaceService, PluginService> = mutableMapOf()
    private val gson = Gson()

    /**
     * Gets a MarketplacePlugin object for the specified plugin ID from the specified MarketplaceService.
     *
     * @param service the MarketplaceService to use for retrieving the plugin
     * @param id the ID of the plugin to retrieve
     * @return a MarketplacePlugin object representing the specified plugin
     * @throws ServiceNotFoundException if the specified service is not found in the services map
     */
    fun getPlugin(service: MarketplaceService, id: String): MarketplacePlugin {
        val pluginService = services[service]
        return pluginService?.getPlugin(id) ?: throw ServiceNotFoundException()
    }

    /**
     * Gets a MarketplacePlugin object for the specified plugin ID from the specified MarketplaceService.
     *
     * @param service the MarketplaceService to use for retrieving the plugin
     * @param id the ID of the plugin to retrieve
     * @return a MarketplacePlugin object representing the specified plugin
     * @throws ServiceNotFoundException if the specified service is not found in the services map
     */
    fun getPlugin(service: MarketplaceService, id: Int): MarketplacePlugin {
        val pluginService = services[service]
        return pluginService?.getPlugin(id.toString()) ?: throw ServiceNotFoundException()
    }

    /**
     * Registers a MarketplaceService implementation with the corresponding PluginService.
     *
     * @param service the MarketplaceService to register
     * @param pluginService the PluginService implementation to use for the specified service
     */
    fun registerService(service: MarketplaceService, pluginService: PluginService) {
        services[service] = pluginService
    }

    /**
     * Loads the plugin index data from the server and parses it into the marketplaceCache.
     */
    fun loadIndex() {
        val jsonObject: JsonObject = Gson().fromJson(getPluginIndex(), JsonObject::class.java)

        for (entry in jsonObject.entrySet()) {
            for (pluginEntry in entry.value.asJsonObject.entrySet()) {
                val serviceName = entry.key
                if (!marketplaceCache.containsKey("${serviceName}:${pluginEntry.key}") && !marketplaceCache.containsValue("${serviceName}:${pluginEntry.value.asString}")) {
                    marketplaceCache["${serviceName}:${pluginEntry.key}"] = "${serviceName}:${pluginEntry.value.asString}"
                }
            }
        }
    }

        /*
        val gson = Gson()
        val jsonData: JsonElement = gson.fromJson(getPluginIndex(), JsonElement::class.java)
        for ((key, value) in jsonData.asJsonObject.entrySet()) {
            kotlin.runCatching {
                marketplaceCache[key.toInt()] = value.asString
            }
        }
    }

         */
}