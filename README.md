# Crystal Rewards

**Crystal Rewards** provides an easy way to create and distribute rewards in Minecraft that are perfectly integrated with the **[Econix](https://modrinth.com/plugin/econix)** plugin. Players can reward with Armostands(For server with resource pack) or with Player Heads.

### Features:
- **Integration with Econix**: Rewards are integrated directly into the economy system, allowing players to earn credits in real time.
- **Reward Types**: Use armorstands when using a server resource pack or if you want to customize them in another cool way. Or playerheads to give your players a small reward.
- Easy creation of rewards: With a single command you can create rewards with custom values.

---

### Instructions: Using Crystal Rewards

1. **Create a reward**
   To create a reward, use the command:
   /crystalrewards create <amount>
   - <amount>: Specifies the amount that will be credited when the reward is collected.
   - Set up an Armorstand or Playhead for yourself and use the command.

2. **Display of the rewards**
   - Player Heads: Use player heads from head plugins or via commands to create the rewards.
   - Armor Stands: Armor Stands offer a visually impressive way to display rewards, but you should have special models in the resource pack to make it look nicer or you can position them nicely.

3. **Interaction with rewards**
   - Players can interact with the “Rewards” by clicking on the Player Head or Armor Stand. The specified amount will be added to the player's Econix account and the player will receive a customizable message.

4. **Example**
   To create a reward of 500(e.g. $), enter the following command:
   
```
/crystalrewards create 500
```
   A reward will be created at the next Armor-stand/Playerhead.

---

### Technical details:
- **Prerequisites**:
  - [Econix](https://modrinth.com/plugin/econix) plugin (for economy integration)
  - Resource Pack (optional, for user-defined Armor Stand models)
  
- **Customization**:
  - The plugin automatically recognizes whether Player Heads or Armor Stands are to be used.
  - All messages are customizable in the config
  - The currency is customizable in the config

- **Coming in the Future**
  - Add Vault Support
  - Add MySQL/MariaDB Support

With Crystal Rewards, server owners can offer their players exciting, visual rewards and make the economy system even more interactive. Ideal for servers that want to utilize custom designs and richer reward mechanics. 🎉
