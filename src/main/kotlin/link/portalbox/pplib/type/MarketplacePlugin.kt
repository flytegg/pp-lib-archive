package link.portalbox.pplib.type

data class MarketplacePlugin(
    val service: MarketplaceService,

    // Plugin information
    val id: String,
    val name: String,
    val description: String,
    val downloads: Int,
    val price: Double,
    val ratingAverage: Double,
    val iconURL: String,

    // Service data
    val version: String,

    // Download information
    val downloadURL: String,
    val isPremium: Boolean,
)