package link.portalbox.pplib.service

import gg.flyte.hangerApi.HangarClient
import link.portalbox.pplib.type.MarketplacePlugin
import link.portalbox.pplib.type.PluginService

class HangarService : PluginService {
    override fun getPlugin(id: String): MarketplacePlugin {
        val args = id.split(":")
        val plugin = HangarClient.getProject(args[0],args[1])
        return MarketplacePlugin(
            plugin.namespace.slug,
            plugin.namespace.owner,
            plugin.description,
            plugin.stats.downloads,
            0.0,
            0.0,
            plugin.avatarUrl,
            plugin.getVersions().first().name,
            plugin.getLatestDownloadURL(),
            false,
        )
    }
}