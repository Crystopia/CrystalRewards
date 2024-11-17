﻿package net.crystopia.crystalRewards.events

import net.crystopia.crystalRewards.CrystalRewards
import net.crystopia.crystalRewards.utils.config.ConfigManager
import net.crystopia.crystalRewards.utils.config.PlayerObject
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

object InteractEvent : Listener {

    val mm = MiniMessage.miniMessage()


    @EventHandler
    fun onInteract(event: PlayerInteractAtEntityEvent) {

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

            CrystalRewards.instance.logger.info(hasreward?.heads?.size.toString())

            val armorstandsSize = hasreward?.armorstands?.size ?: 0

            CrystalRewards.instance.logger.info(hasreward?.armorstands?.size.toString())

            val founded = headsSize + armorstandsSize

// Fix the Loop to add to many...
            if (hasreward?.armorstands != null) {
                hasreward?.armorstands?.forEach { armorstand ->
                    (
                            if (armorstand == entityid) {
                                return player.sendMessage(mm.deserialize(ConfigManager.settings.rewarderrormessage.toString()))
                            } else {

                                // Add Econemy add here!
                                player.sendMessage(
                                    mm.deserialize(
                                        ConfigManager.settings.rewardsuccesmessage.toString()
                                            .replace(
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
                            )
                }
            } else {

                // Add Econemy add here!
                player.sendMessage(
                    mm.deserialize(
                        ConfigManager.settings.rewardsuccesmessage.toString()
                            .replace(
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

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {

        val player = event.player

        if (event.clickedBlock?.type == Material.PLAYER_HEAD) {

            val state: TileState = event.clickedBlock!!.state as TileState
            val container: PersistentDataContainer = state.persistentDataContainer

            val key = NamespacedKey(CrystalRewards.instance, "crystals")
            val uuid = NamespacedKey(CrystalRewards.instance, "uuid")

            val haskey = container.has(key, PersistentDataType.INTEGER)
            if (!haskey) return

            val getdata = container.get(key, PersistentDataType.INTEGER)
            player.sendMessage(getdata.toString())

            // player.sendMessage("You have clicked on the Reward head! You get the reward of <amount> Crystals 👍")
        }
    }


}