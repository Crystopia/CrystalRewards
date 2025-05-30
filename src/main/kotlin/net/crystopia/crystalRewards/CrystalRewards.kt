package net.crystopia.crystalRewards

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import me.jesforge.econix.Econix
import me.jesforge.econix.api.EconixAPI
import net.crystopia.crystalRewards.commands.CrystalRewardsCommand
import net.crystopia.crystalRewards.events.PlayerInteractAtEntityEvent
import net.crystopia.crystalRewards.events.PlayerInteractEvent
import net.crystopia.crystalRewards.utils.config.ConfigManager
import org.bukkit.plugin.java.JavaPlugin

class CrystalRewards : JavaPlugin() {

    companion object {
        lateinit var instance: CrystalRewards
    }

    init {
        instance = this
    }

    var econix: EconixAPI? = null


    override fun onEnable() {
        logger.info("CrystalRewards enabled")
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        CommandAPI.onEnable();

        if (server.pluginManager.getPlugin("Econix")!!.isEnabled) {
            logger.info("Hooking into Econix")
            econix = Econix.getAPI()
        } else {
            logger.info("Econix is disabled")
            server.pluginManager.disablePlugin(this)
        }

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
        server.pluginManager.registerEvents(PlayerInteractEvent, this)
        server.pluginManager.registerEvents(PlayerInteractAtEntityEvent, this)
        logger.info("Loaded Events")
    }

    fun loadCommands() {
        CrystalRewardsCommand()
        logger.info("Loaded Commands")

    }

}
