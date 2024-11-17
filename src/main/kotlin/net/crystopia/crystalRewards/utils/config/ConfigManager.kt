package net.crystopia.crystalRewards.utils.config

import kotlinx.serialization.encodeToString
import java.io.File

object ConfigManager {
    private val settingsFile = File("plugins/CrystalRewards/config.json")
    private val playerFile = File("plugins/CrystalRewards/player.json")


    val settings = settingsFile.loadConfig(ConfigData())
    val playerdata = playerFile.loadConfig(PlayerData())


    fun save() {
        settingsFile.writeText(json.encodeToString(settings))
        playerFile.writeText(json.encodeToString(playerdata))
    }


}