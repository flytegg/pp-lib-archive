package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.util.*

fun main() {
    BASE_DOMAIN = "http://localhost:5005"

    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    println(getLatestPPVersion())

}