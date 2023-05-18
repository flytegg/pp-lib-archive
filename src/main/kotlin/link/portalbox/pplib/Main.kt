package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.util.BASE_DOMAIN
import link.portalbox.pplib.util.getAPIPlugin
import link.portalbox.pplib.util.getPluginIdFromName
import link.portalbox.pplib.util.searchPlugins

fun main() {
    BASE_DOMAIN = "http://localhost:5005"

    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    println(SpigotMCService().getPlugin("34315").downloadURL)

    println("name: ${getPluginIdFromName("hyperMOTD")}")

}