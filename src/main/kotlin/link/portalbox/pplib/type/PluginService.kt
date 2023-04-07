package link.portalbox.pplib.type

enum class PluginService(val supported: Boolean) {

    BUKKIT(false),
    CURSEFORGE(false),
    HANGAR(false),
    MODRINTH(false),
    SPIGOTMC(true),
    CUSTOM(false);

}