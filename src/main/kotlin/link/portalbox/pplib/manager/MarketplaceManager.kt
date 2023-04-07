package link.portalbox.pplib.manager

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.gson.Gson
import com.google.gson.JsonElement
import link.portalbox.pplib.util.getPluginIndex

object MarketplaceManager {

    private val marketplaceCache: BiMap<Int, String> = HashBiMap.create()

    init {
        val gson = Gson()
        val jsonData: JsonElement = gson.fromJson(getPluginIndex(), JsonElement::class.java)
        for ((key, value) in jsonData.asJsonObject.entrySet()) {
            runCatching { marketplaceCache[key.toInt()] = value.asString }
        }
    }

    /**
     * Returns the name associated with the given ID in the marketplace cache.
     * @param id the ID of the marketplace item
     * @return the name of the marketplace item
     */
    fun getName(id: Int): String? {
        return marketplaceCache[id]
    }

    /**
     * Returns the ID associated with the given name in the marketplace cache.
     * @param name the name of the marketplace item
     * @return the ID of the marketplace item
     */
    fun getID(name: String): Int? {
        return marketplaceCache.inverse()[name]
    }

    /**
     * Returns the marketplace cache, which is a bidirectional map of IDs to names.
     * @return the marketplace cache
     */
    fun getMarketplaceCache(): BiMap<Int, String> {
        return marketplaceCache
    }

}
