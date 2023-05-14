package link.portalbox.pplib

import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.service.HangarService
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.util.getPPVersions
import link.portalbox.pplib.util.getPluginFromName
import link.portalbox.pplib.util.separateServiceAndName

fun main() {
    MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    MarketplacePluginManager.registerService(MarketplaceService.HANGAR, HangarService())

    println(getPluginFromName("EssentialsX"))
}