package net.crystopia.crystalRewards

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.crystopia.crystalRewards.commands.CrystalRewardsCommand
import net.crystopia.crystalRewards.events.InteractEvent
import net.crystopia.crystalRewards.utils.config.ConfigManager
import org.bukkit.plugin.java.JavaPlugin

class CrystalRewards : JavaPlugin() {

    companion object {
        lateinit var instance: CrystalRewards
    }

    init {
        instance = this
    }

    override fun onEnable() {
        logger.info("CrystalRewards enabled")
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        CommandAPI.onEnable();

        val settings = ConfigManager.settings
        val playerdata = ConfigManager.playerdata

        loadEvents()
        loadCommands()
    }

    override fun onDisable() {
        logger.info("CrystalRewards disabled")

        CommandAPI.onDisable();

    }

    fun loadEvents() {
        server.pluginManager.registerEvents(InteractEvent, this)
        logger.info("Loaded Events")
    }

    fun loadCommands() {
        CrystalRewardsCommand()
        logger.info("Loaded Commands")

    }

}
