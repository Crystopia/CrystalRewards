package net.crystopia.crystalRewards.commands

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.integerArgument
import dev.jorel.commandapi.kotlindsl.literalArgument
import net.crystopia.crystalRewards.CrystalRewards
import net.crystopia.crystalRewards.utils.config.ConfigManager
import net.crystopia.crystalRewards.utils.config.RewardData
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.*


class CrystalRewardsCommand {

    val command = commandTree("crystalrewards") {
        literalArgument("create") {
            integerArgument("reward-amount") {
                anyExecutor() { sender, args ->
                    val sender = sender as Player

                    if (sender is Player) {

                        val item = sender.inventory.itemInMainHand

                        if (item?.type != Material.PLAYER_HEAD) {
                            val target = sender.getTargetEntity(5)
                            if (target is ArmorStand) {

                                val entity = target as ArmorStand

                                ConfigManager.settings.allRewards += 1

                                ConfigManager.settings.rewardsarmostands["${entity.uniqueId}"] =
                                    RewardData(args.args[0] as Int)

                                ConfigManager.save()

                                sender.sendMessage("Armor-stand reward wurde hinzugefügt mit dem Reward " + args.args[0])

                            } else {
                                sender.sendMessage("Please hold an Item in the Mainhand that is a Player_Head")
                            }
                        } else {
                            val meta = item.itemMeta

                            val key = NamespacedKey(CrystalRewards.instance, "crystals")
                            val uuid = NamespacedKey(CrystalRewards.instance, "uuid")
                            meta.persistentDataContainer[key, PersistentDataType.INTEGER] = args.args[0] as Int
                            meta.persistentDataContainer[uuid, PersistentDataType.STRING] = UUID.randomUUID().toString()

                            ConfigManager.settings.allRewards += 1
                            item.setItemMeta(meta)

                            sender.sendMessage("You have added the Reward amount " + args.args[0].toString() + " to this Block.")


                        }


                    } else {
                        sender.sendMessage("This command can't used from the console!")
                    }

                }
            }
        }
    }

}