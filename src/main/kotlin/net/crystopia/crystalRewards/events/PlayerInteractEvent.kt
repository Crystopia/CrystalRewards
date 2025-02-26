package net.crystopia.crystalRewards.events

import net.crystopia.crystalRewards.CrystalRewards
import net.crystopia.crystalRewards.utils.config.ConfigManager
import net.crystopia.crystalRewards.utils.config.PlayerObject
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*

object PlayerInteractEvent : Listener {

    val mm = MiniMessage.miniMessage()

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {

        if (event.action.isLeftClick) return

        if (event.hand != EquipmentSlot.HAND) return

        val player = event.player

        val cooldowns = mutableMapOf<UUID, Long>()
        val currentTime = System.currentTimeMillis()

        if (cooldowns.containsKey(player.uniqueId)) {
            val lastInteractTime = cooldowns[player.uniqueId]!!
            val timePassed = currentTime - lastInteractTime
            if (timePassed < 10000) {
                return
            }
        }

        if (event.clickedBlock?.type == Material.PLAYER_HEAD && event.action.isRightClick) {
            val state: TileState = event.clickedBlock!!.state as TileState
            val container: PersistentDataContainer = state.persistentDataContainer

            val rewardkey = NamespacedKey(CrystalRewards.instance.name.lowercase(Locale.getDefault()), "reward")
            val uuid = NamespacedKey(CrystalRewards.instance.name.lowercase(Locale.getDefault()), "uuid")

            if (!container.has(rewardkey, PersistentDataType.DOUBLE)) return

            val getuuid = container.get(uuid, PersistentDataType.STRING)
            val reward: Double = container.get(rewardkey, PersistentDataType.DOUBLE) as Double

            val hasreward = ConfigManager.playerdata.player["${player.uniqueId}"]
            val headsSize = hasreward?.heads?.size ?: 0
            val armorstandsSize = hasreward?.armorstands?.size ?: 0
            val founded = headsSize + armorstandsSize + 1

            if (hasreward?.heads?.contains(getuuid) == true) {
                player.sendMessage(mm.deserialize(ConfigManager.settings.rewarderrormessage.toString()))
                cooldowns[player.uniqueId] = currentTime
                return
            }

            CrystalRewards.instance.econix!!.addBalance(
                player.uniqueId.toString(),
                ConfigManager.settings.currency,
                reward
            )

            player.sendMessage(
                mm.deserialize(
                    ConfigManager.settings.rewardsuccesmessage.toString()
                        .replace("%allrewards%", ConfigManager.settings.allRewards.toString())
                        .replace("%hasamount%", founded.toString())
                )
            )

            cooldowns[player.uniqueId] = currentTime

            val playerData = ConfigManager.playerdata.player["${player.uniqueId}"]
            val updatedHeads = playerData?.heads?.toMutableList() ?: mutableListOf()

            updatedHeads.add(getuuid.toString())

            val updatedPlayerObject = playerData?.copy(
                heads = updatedHeads
            ) ?: PlayerObject(
                heads = listOf(getuuid.toString())
            )

            ConfigManager.playerdata.player["${player.uniqueId}"] = updatedPlayerObject
            ConfigManager.save()
        }

    }


}