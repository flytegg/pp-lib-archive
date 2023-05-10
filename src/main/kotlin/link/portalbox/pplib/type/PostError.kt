package link.portalbox.pplib.type

data class PostError(
    val pluginVersion: String,
    val mcVersion: String,
    val stackTrace: String,
)