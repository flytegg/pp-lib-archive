package link.portalbox.pplib

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import link.portalbox.pplib.manager.MarketplacePluginManager
import link.portalbox.pplib.manager.MarketplacePluginManager.getPlugin
import link.portalbox.pplib.manager.MarketplacePluginManager.loadIndex
import link.portalbox.pplib.manager.MarketplacePluginManager.marketplaceCache
import link.portalbox.pplib.service.SpigotMCService
import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.RequestPlugin
import link.portalbox.pplib.util.getLatestPPVersion
import link.portalbox.pplib.util.getLatestVersion
import link.portalbox.pplib.util.requestPlugin
import java.io.File

fun main(args: Array<String>) {
    //MarketplacePluginManager.registerService(MarketplaceService.SPIGOTMC, SpigotMCService())
    //loadIndex()

    requestPlugin(RequestPlugin("id", MarketplaceService.SPIGOTMC, "username", "reasonForRequest"))
}