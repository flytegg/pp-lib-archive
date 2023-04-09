package link.portalbox.pplib.type

interface PluginService {
    /**
     * Gets a MarketplacePlugin object for the specified plugin ID from the marketplace API.
     *
     * @param id the ID of the plugin to retrieve
     * @return a MarketplacePlugin object representing the specified plugin
     */
    fun getPlugin(id: String): MarketplacePlugin
}