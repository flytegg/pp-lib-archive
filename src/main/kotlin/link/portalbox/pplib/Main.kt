package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService

fun main(args: Array<String>) {
    // PluginPortal: 108700
    // Oraxen: 72448
    // EssentialsX: 9089
    // Vault: 34315
    // WorldEdit: 31034
    // KoChat: 107564

    // Test, delete after
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    println(MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, 107564).downloadURL)
}