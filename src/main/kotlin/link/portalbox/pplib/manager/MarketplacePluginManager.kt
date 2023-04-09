package link.portalbox.pplib.manager

import link.portalbox.pplib.exception.ServiceNotFoundException
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.PluginService

object MarketplacePluginManager {
    private val services: MutableMap<MarketplaceService, PluginService> = mutableMapOf()

    fun getPlugin(service: MarketplaceService, id: String): MarketplacePlugin {
        val pluginService = services[service]
        return pluginService?.getPlugin(id) ?: throw ServiceNotFoundException()
    }

    fun registerService(service: MarketplaceService, pluginService: PluginService) {
        services[service] = pluginService
    }
}