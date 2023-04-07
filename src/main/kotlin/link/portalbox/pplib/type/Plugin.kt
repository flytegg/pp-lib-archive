package link.portalbox.pplib.type

import java.net.URL
import com.google.common.collect.BiMap
import link.portalbox.pplib.service.Marketplace
import java.util.LinkedHashSet

abstract class Plugin(val service: PluginService, var id: String) {
    var name: String = ""
    var onlineVersion: String = ""
    var description: String = ""
    var releaseVersions: LinkedHashSet<String> = LinkedHashSet()
    var iconURL: String? = null
    var supportedVersions: LinkedHashSet<String> = LinkedHashSet()

    // Plugin Statistics
    var downloads: Int = 0
    var updateDate: Long = 0
    var price: Double = 0.0
    var rating: Double = 0.0
    var ratingCount: Int = 0
    var premium: Boolean = false

    // PortalBox
    var downloadURL: URL? = null
    var files: BiMap<String, String>? = null

    // Methods
    fun getMarketplacePlugin(): Marketplace {
        return Marketplace(
            this.id,
            this.name,
            this.onlineVersion,
            this.description,
            this.iconURL,
            this.releaseVersions,
            this.supportedVersions,
            this.downloads,
            this.updateDate,
            this.price,
            this.rating,
            this.premium,
            this.downloadURL,
            this.files
        )
    }
}
