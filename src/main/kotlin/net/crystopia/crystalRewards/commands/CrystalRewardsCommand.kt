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
import org.bukkit.block.TileState
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.io.File
import java.util.*


class CrystalRewardsCommand {

    val command = commandTree("crystalrewards") {
        literalArgument("create") {
            integerArgument("reward-amount") {
                anyExecutor() { sender, args ->
                    val sender = sender as Player

                    if (sender is Player) {
                        val block = sender.getTargetBlockExact(3)
                        val target = sender.getTargetEntity(5)

                        if (block?.type == Material.PLAYER_HEAD) {
                            val crystals = NamespacedKey(
                                CrystalRewards.instance.name.lowercase(Locale.getDefault()),
                                "crystals"
                            )
                            val uuidKey = NamespacedKey(
                                CrystalRewards.instance.name.lowercase(Locale.getDefault()),
                                "uuid"
                            )

                            val state: TileState = block.state as TileState
                            val container: PersistentDataContainer = state.persistentDataContainer

                            container.set(crystals, PersistentDataType.INTEGER, args.args[0] as Int)
                            container.set(uuidKey, PersistentDataType.STRING, UUID.randomUUID().toString())

                            state.update()

                            ConfigManager.settings.allRewards += 1

                            sender.sendMessage("You have added the Reward amount " + args.args[0].toString() + " to this Block.")
                        } else if (target is ArmorStand) {
                            val entity = target as ArmorStand

                            ConfigManager.settings.allRewards += 1

                            ConfigManager.settings.rewardsarmostands["${entity.uniqueId}"] =
                                RewardData(args.args[0] as Int)

                            ConfigManager.save()

                            sender.sendMessage("Armor-stand reward wurde hinzugefügt mit dem Reward " + args.args[0])
                        } else {
                            sender.sendMessage("You need to look at a armor-stand or Player Head and run the Command!")
                        }
                    } else {
                        sender.sendMessage("This command can't used from the console!")
                    }
                }
            }
        }
        literalArgument("player") {
            literalArgument("reset") {
                anyExecutor() { sender, args ->

                    val sender = sender as Player

                    val playerFile = File("plugins/CrystalRewards/player.json")

                    playerFile.delete()

                    sender.sendMessage("The PlayerConfig has been reset")

                }
            }

        }
    }
}