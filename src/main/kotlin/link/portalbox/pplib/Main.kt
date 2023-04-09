package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService

fun main(args: Array<String>) {
    // PluginPortal: 108700
    // Oraxen: 72448

    // Test, delete after
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    println(MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, "72448"))
}
