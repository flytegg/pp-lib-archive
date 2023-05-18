package link.portalbox.pplib.type.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import link.portalbox.pplib.type.MarketplaceService

data class APIPlugin @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("service") val service: MarketplaceService,
    @JsonProperty("name") val name: String,
    @JsonProperty("alternateDownload") val alternateDownload: String?,
)
