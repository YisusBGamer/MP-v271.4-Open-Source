# Forgotten Temple Keeper (2141002), Forgotten Twilight 270050200 / 270051200: where Munin's Banish sends a player.
# Talking to him leaves the fight; the portal in this room leads back into it (Pinkin).
response = sm.sendAskYesNo("Munin's power has sent you out of the battle. The portal in this room leads back to it.\r\n\r\n"
                           "Do you want to give up and leave the temple instead? You won't be able to return to this fight.")

if response:
    sm.warpInstanceOut(270050000)
