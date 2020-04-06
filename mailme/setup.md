---
description: How to setup your new MailMe plugin
---

# Setup

## Installation

1. Turn your server off
2. Place the MailMe plugin jar into the plugins directory
3. Start the server

## Configuration

All the configurable files --

### Customizing the config.yml

The config.yml itself has lots of commenting - unfortunately it gets destroyed when we try to write to it so here's a copy of the latest version with clear explanation.

```yaml
#     ___      ___       __        __    ___       ___      ___   _______
#    |"  \    /"  |     /""\      |" \  |"  |     |"  \    /"  | /"     "|
#     \   \  //   |    /    \     ||  | ||  |      \   \  //   |(: ______)
#     /\\  \/.    |   /' /\  \    |:  | |:  |      /\\  \/.    | \/    |
#    |: \.        |  //  __'  \   |.  |  \  |___  |: \.        | // ___)_
#    |.  \    /:  | /   /  \\  \  /\  |\( \_|:  \ |.  \    /:  |(:      "|
#    |___|\__/|___|(___/    \___)(__\_|_)\_______)|___|\__/|___| \_______)

# @Author Harry0198 / Harolds

#
# ◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥
#                     How to use the plugin
#   Most of this plugin should be self explanatory. Lots of values
#           Are commented out for your ease of use.
#
#   Alternatively, see plugin page for more help!
#
# ◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢◣◥
#
config-ver: 2
#                              Global Modifiers
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
#

  # This is the default global language that is used across the server if one is not selected
# Default "EN"
lang: "EN"

# This is the delay before you can send another mail to the recipient in minutes
# Default 1
delay: 1

# Turns debug mode on / off
# Default false (off)
debug: false

#                              Icons for usage
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
#

# Default icons:
# There is no limit to these so you can just add more and it will make new pages like normal.
# If a mistake is made an error will be thrown.
# To add more, simply add another row with your valid material

icons:
  - BLACK_BANNER
  - SEA_PICKLE
  - ELYTRA
  - DIAMOND_PICKAXE
  - EMERALD
  - MINECART
  - DIAMOND

#                           Default Mailbox Location
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
#

# Please Note: Here, pings are not unique! Therefore, if a player doesn't have mail at one of these locations,
# an effect will still show for them.

default-mailbox:
  world: "world"
  x: 0
  y: 0
  z: 0

#                           Preset Mail for sending
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
#

# Administrators can send preset mail that is defined here. You can send the presets using
# /mail admin send <player/all> [preset-ID]

presets:
  win: |-
    {
      "type": "com.haroldstudios.mailme.mail.types.MailItems",
      "data": {
        "items": [
          "is:\n  \u003d\u003d: org.bukkit.inventory.ItemStack\n  v: 1976\n  type: DIAMOND\n"
        ],
        "icon": "is:\n  \u003d\u003d: org.bukkit.inventory.ItemStack\n  v: 1976\n  type: ELYTRA\n  meta:\n    \u003d\u003d: ItemMeta\n    meta-type: UNSPECIFIC\n    enchants:\n      LURE: 1\n    ItemFlags:\n    - HIDE_ENCHANTS\n",
        "date": "04-04-2020-04:00:19.813",
        "recipients": [
          "4aebd9fe-c5df-32ec-bd8d-bda099eb15ba"
        ],
        "read": false,
        "reply": false,
        "sender": "4aebd9fe-c5df-32ec-bd8d-bda099eb15ba",
        "delay": 1
      }
    }
```

### Customizing the language files

{% hint style="success" %}
 We're always looking for more translations and accuracy! If you know another language than those provided - please don't hesitate providing another language file to the community!
{% endhint %}

Language files can be found in the directory ~MailMe/languages/

#### To edit:

Simply open the .yml and edit the VALUES. If you edit the KEYS the plugin will overwrite them to prevent any errors occuring.

Here's an example:

#### Old version

```
prefix: "&8[&eMailMe&8]&r"
```

**New version**

```text
prefix: "&7My Custom Prefix!"
```



