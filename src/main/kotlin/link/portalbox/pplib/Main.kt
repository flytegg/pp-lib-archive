package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.manager.MarketplacePluginManager.loadIndex
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.util.getLatestPPVersion
import link.portalbox.pplib.util.getLatestVersion

fun main(args: Array<String>) {
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    loadIndex()

    println(getLatestPPVersion())
}