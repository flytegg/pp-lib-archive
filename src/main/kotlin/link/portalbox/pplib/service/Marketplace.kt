package link.portalbox.pplib.service

import com.google.common.collect.BiMap
import link.portalbox.pplib.type.Plugin
import link.portalbox.pplib.type.PluginService.CUSTOM
import java.net.URL

class Marketplace(
    id: String,
    name: String,
    onlineVersion: String,
    description: String,
    iconURL: String?,
    releaseVersions: LinkedHashSet<String>,
    supportedVersions: LinkedHashSet<String>,
    downloads: Int,
    updateDate: Long,
    price: Double,
    rating: Double,
    premium: Boolean,
    downloadURL: URL?,
    files: BiMap<String, String>?
) : Plugin(CUSTOM, id)