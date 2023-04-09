package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.util.getLatestPPVersion
import link.portalbox.pplib.util.getPPVersions

fun main(args: Array<String>) {
    // PluginPortal: 108700
    // Oraxen: 72448
    // EssentialsX: 9089
    // Vault: 34315
    // WorldEdit: 31034

    // Test, delete after
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    println(MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, 9089).downloadURL)
    println(MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, 108700).downloadURL)
    println(MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, 34315).downloadURL)
    println(getLatestPPVersion())
    println(getPPVersions())
}