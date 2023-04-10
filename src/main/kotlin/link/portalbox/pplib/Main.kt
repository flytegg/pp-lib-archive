package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.manager.MarketplacePluginManager.loadIndex
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService
import java.net.URL
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    loadIndex()
    val plugin = MarketplacePluginManager.getPlugin(MarketplaceService.SPIGOTMC, 22419)
    println(plugin.isPremium)
}