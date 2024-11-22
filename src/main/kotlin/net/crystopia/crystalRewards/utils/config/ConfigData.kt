package net.crystopia.crystalRewards.utils.config

import kotlinx.serialization.Serializable

@Serializable
data class ConfigData(

    var allRewards: Int = 0,
    var currency : String = "crystals",
    var rewardsuccesmessage: String? = "\n ċ <color:#bf6bff>You have found a secret!</color> <color:#adf5ff>Keep collecting and you'll get more Crystals.</color> <color:#9effb8>You have now found <color:#ffd587><i>%hasamount%/%allrewards%</i></color> secrets.</color> \n",
    var rewarderrormessage: String? = "\n ‰ <red>You have already found this <color:#ffc45e>reward</color>!</red><color:#63ffd6> Keep searching to find more.</color> \n",
    var rewardsarmostands: MutableMap<String, RewardData> = mutableMapOf(),

    )

@Serializable
data class RewardData(
    val reward: Double
)


@Serializable
data class PlayerData(

    var player: MutableMap<String, PlayerObject> = mutableMapOf(),

    )

@Serializable
data class PlayerObject(
    val armorstands: List<String> = listOf(),
    val heads: List<String> = listOf(),
)


/*
"headrewards": {
    "1": {
        "x": 3.0,
        "y": 2.1,
        "z": 1.0,
        "reward": 1
    }
}
*/