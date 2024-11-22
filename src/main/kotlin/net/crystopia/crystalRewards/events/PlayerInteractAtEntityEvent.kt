package net.crystopia.crystalRewards.events

import me.TechsCode.UltraEconomy.objects.Currency
import net.crystopia.crystalRewards.CrystalRewards
import net.crystopia.crystalRewards.utils.config.ConfigManager
import net.crystopia.crystalRewards.utils.config.PlayerObject
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

object PlayerInteractAtEntityEvent : Listener {

    val mm = MiniMessage.miniMessage()


    @EventHandler
    fun onPlayerInteractAtEntityEvent(event: PlayerInteractAtEntityEvent) {

        val player = event.player
        val config = ConfigManager.settings
        val playerdata = ConfigManager.playerdata

        if (event.rightClicked.type == EntityType.ARMOR_STAND) {

            val entityid = event.rightClicked.uniqueId.toString()

            val reward = config.rewardsarmostands.get(entityid)

            if (reward == null) {
                return
            }

            val hasreward = ConfigManager.playerdata.player["${player.uniqueId}"]
            val headsSize = hasreward?.heads?.size ?: 0
            val armorstandsSize = hasreward?.armorstands?.size ?: 0
            val founded = headsSize + armorstandsSize + 1

            if (hasreward?.armorstands != null) {
                if (hasreward?.armorstands?.contains(entityid) == true) {
                    player.sendMessage(mm.deserialize(ConfigManager.settings.rewarderrormessage.toString()))
                    return
                } else {

                    val getreward: Double = ConfigManager.settings.rewardsarmostands.get(entityid)?.reward as Double

                    val currency: Currency? =
                        CrystalRewards.instance.ueapi?.currencies?.name(ConfigManager.settings.currency)?.get()

                    val account = CrystalRewards.instance.ueapi?.accounts?.uuid(player.uniqueId)?.get()

                    account?.addBalance(currency, getreward)

                    player.sendMessage(
                        mm.deserialize(
                            ConfigManager.settings.rewardsuccesmessage.toString()
                                .replace("%allrewards%", ConfigManager.settings.allRewards.toString())
                                .replace("%hasamount%", founded.toString())
                        )
                    )

                    val playerData = ConfigManager.playerdata.player["${player.uniqueId}"]

                    val updatedArmorstands = playerData?.armorstands?.toMutableList() ?: mutableListOf()

                    updatedArmorstands.add(entityid)

                    val updatedPlayerObject = playerData?.copy(
                        armorstands = updatedArmorstands
                    ) ?: PlayerObject(
                        armorstands = listOf(entityid)
                    )

                    ConfigManager.playerdata.player["${player.uniqueId}"] = updatedPlayerObject

                    ConfigManager.save()
                    return
                }


            } else {

                val getreward: Double = ConfigManager.settings.rewardsarmostands.get(entityid)?.reward as Double

                val currency: Currency? =
                    CrystalRewards.instance.ueapi?.currencies?.name(ConfigManager.settings.currency)?.get()

                val account = CrystalRewards.instance.ueapi?.accounts?.uuid(player.uniqueId)?.get()

                account?.addBalance(currency, getreward)

                player.sendMessage(
                    mm.deserialize(
                        ConfigManager.settings.rewardsuccesmessage.toString()
                            .replace("%allrewards%", ConfigManager.settings.allRewards.toString())
                            .replace("%hasamount%", founded.toString())
                    )
                )

                player.sendMessage(
                    mm.deserialize(
                        ConfigManager.settings.rewardsuccesmessage.toString().replace(
                            "%allrewards%", ConfigManager.settings.allRewards.toString()

                        ).replace("%hasamount%", founded.toString())
                    )
                )

                val playerData = ConfigManager.playerdata.player["${player.uniqueId}"]

                val updatedArmorstands = playerData?.armorstands?.toMutableList() ?: mutableListOf()

                updatedArmorstands.add(entityid)

                val updatedPlayerObject = playerData?.copy(
                    armorstands = updatedArmorstands
                ) ?: PlayerObject(
                    armorstands = listOf(entityid)
                )

                ConfigManager.playerdata.player["${player.uniqueId}"] = updatedPlayerObject

                ConfigManager.save()

            }
        }
    }

}