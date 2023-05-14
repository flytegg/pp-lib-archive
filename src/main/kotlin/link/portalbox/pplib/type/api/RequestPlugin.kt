package link.portalbox.pplib.type.api

import link.portalbox.pplib.type.MarketplaceService

data class RequestPlugin(
    val id: String,
    val service: MarketplaceService,
    val requestReasoning: String,
    val pluginName: String,
    val externalUrl: String,
    val username: String,
)