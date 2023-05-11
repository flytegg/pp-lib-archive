package link.portalbox.pplib

import link.portalbox.pplib.type.MarketplaceService
import link.portalbox.pplib.type.PostError
import link.portalbox.pplib.type.RequestPlugin
import link.portalbox.pplib.util.*

fun main(args: Array<String>) {
    startErrorCatcher(
        PostError(
            "1.0.0",
            "1.16.5",
            "",
        )
    )

    requestPlugin(
        RequestPlugin(
            "9089",
            MarketplaceService.SPIGOTMC,
            "Invalid Download URL",
            "EssentialsX",
            "https://www.spigotmc.org/resources/essentialsx.9089/download?version=466639",
            "Dawsson"
        )
    )

    throw NullPointerException("Hi Stephen!")

}