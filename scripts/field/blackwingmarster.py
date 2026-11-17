# 931000650 Entrance to Gelimer's Lab - Wing Master (24091, Mercedes story): Orchid fights
if sm.hasQuest(24091) and not sm.isComplete(24091) and not sm.hasMobsInField():
    sm.removeNpc(1033230)
    sm.spawnMob(9300435, 1338, -164, False)

# Black Wing Master (23147, Resistance story): Gelimer and Orchid wait at the entrance to Gelimer's Lab (your own copy:
# enterBlackWingHD). Sets the quest's record (23150 = 1) so it can be handed in. The client has no lines for the
# meeting; written from the quest's own Quest.wz summaries ("You met...the founder of the Black Wings?! Her name is
# Orchid." / "You tell her about Gelimer and your face-off with Orchid.").
elif sm.hasQuest(23147) and sm.getQRValue(23150) != "1":
    GELIMER, ORCHID = 2159349, 1033230
    sm.setSpeakerID(GELIMER)
    sm.sendNext("Back again? You Resistance pests are tiresome. My experiments are none of your concern.")
    sm.setSpeakerID(ORCHID)
    sm.sendSay("Enough, Gelimer. So this is the one who keeps getting in our way.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Who are you?")
    sm.setSpeakerID(ORCHID)
    sm.sendSay("I am Orchid, founder of the Black Wings and Commander of the Black Mage. Remember the name. The Black Mage will return, and there's nothing you or your little Resistance can do to stop it.")
    sm.setQRValue(23150, "1")
    sm.sendSayOkay("Run home and tell your friends. I'll be waiting.")
    sm.warpInstanceOut(310040210, 0)
