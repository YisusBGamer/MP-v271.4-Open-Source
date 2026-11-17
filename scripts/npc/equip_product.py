# Ardentmill profession NPC: crafting is retired (GameConstants.CRAFTING_RETIRED); the crafted goods are sold instead
sm.setSpeakerID(parentID)
if sm.sendAskYesNo("Crafting has been retired on this server. Everything useful it made - Meister gear, the alchemy buff potions, Android Hearts - can be bought instead: #b@menu > Shops#k (Crafted goods and the Android Shop). Cubes, Rebirth Flames and Golden Hammers are in #b@enhance#k.\r\n\r\nShall I show you the crafted goods now?"):
    sm.openShop(9031003)
sm.dispose()
