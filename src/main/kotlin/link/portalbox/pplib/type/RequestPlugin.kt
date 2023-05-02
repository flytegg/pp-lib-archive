package link.portalbox.pplib.type

data class RequestPlugin(
    val id: String,
    val service: MarketplaceService,
    val username: String,
    val reasonForRequest: String,
)