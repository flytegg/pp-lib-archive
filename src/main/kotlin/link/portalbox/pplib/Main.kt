package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.manager.MarketplacePluginManager.getPlugin
import link.portalbox.pplib.manager.MarketplacePluginManager.loadIndex
import link.portalbox.pplib.service.HangarService
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService

fun main(args: Array<String>) {
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    MarketplacePluginManager.registerService(MarketplaceService.HANGAR, HangarService())
    loadIndex()

    println(getPlugin("HANGAR:stephen:PluginPortal"))
}