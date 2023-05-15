package link.portalbox.pplib.manager

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.gson.Gson
import com.google.gson.JsonObject
import link.portalbox.pplib.exception.ServiceNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.api.PluginService
import link.portalbox.pplib.util.getPluginIndex
import link.portalbox.pplib.util.separateServiceAndName


object MarketplacePluginManager {

    // BiMap<Id, String>

    private val services: MutableMap<MarketplaceService, PluginService> = mutableMapOf()

    /**
     * Gets a MarketplacePlugin object for the specified plugin ID (without service in string) from the specified MarketplaceService.
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
     * Gets a MarketplacePlugin object for the specified plugin ID
     *
     * @param id the ID of the plugin to retrieve with the format "service:id"
     * @return a MarketplacePlugin object representing the specified plugin
     * @throws ServiceNotFoundException if the specified service is not found in the services map
     */
    fun getPlugin(id: String): MarketplacePlugin {
        val pair = separateServiceAndName(id)
        return getPlugin(pair.first, pair.second)
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

}