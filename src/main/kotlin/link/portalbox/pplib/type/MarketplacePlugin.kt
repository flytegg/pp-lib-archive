package link.portalbox.pplib.type

data class MarketplacePlugin(
    // Plugin information
    val id: String,
    val name: String,
    val description: String,
    val downloads: Int,
    val price: Double,
    val ratingCount: Int,
    val ratingAverage: Double,

    // Service data
    val version: String,
    val author: String,

    // Download information
    val downloadURL: String,
    val isDirectDownload: Boolean,
    val isPremium: Boolean,
)