# id 940010000 (Hidden Street : Pantheon Vacant Lot), field 940010000
sm.lockInGameUI(True, True)
sm.forcedInput(1)
sm.sendDelay(30)
sm.createQuestWithQRValue(16119, "")
sm.createQuestWithQRValue(27039, "")
sm.createQuestWithQRValue(27039, "")
sm.completeQuestNoCheck(28515)
sm.createQuestWithQRValue(26015, "")
sm.createQuestWithQRValue(26011, "")
sm.forcedInput(0)
sm.spawnNpc(3000110, 350, 0)
sm.showNpcSpecialActionByTemplateId(3000110, "summon", 0)
sm.spawnNpc(3000111, 250, 0)
sm.showNpcSpecialActionByTemplateId(3000111, "summon", 0)
sm.spawnNpc(3000114, 150, 0)
sm.showNpcSpecialActionByTemplateId(3000114, "summon", 0)
sm.spawnNpc(3000119, 0, 0)
sm.showNpcSpecialActionByTemplateId(3000119, "summon", 0)
sm.setSpeakerType(3)
sm.setParam(17)
sm.sendNext("Yikes! I found bad guys!")
sm.setSpeakerID(3000110) # Nefarious Priest
sm.setParam(1)
sm.sendSay("Hey! I found a beautiful girl!")
sm.setSpeakerID(3000114) # Nefarious Priest
sm.sendSay("Get yourself together! That's Angelic Buster!")
sm.setParam(17)
sm.sendSay("What are you goons up to?!")
sm.setParam(1)
sm.sendSay("Do I have to hit her? She's really good looking...")
sm.setSpeakerID(3000119) # Nefarious Priest
sm.sendSay("Don't hit her! Just catch her! She can clean up that mess I made in the laundry room!")
sm.setSpeakerID(3000110) # Nefarious Priest
sm.sendSay("Perfect plan! You're gonna be our new maid, cutie!")
sm.setParam(17)
sm.sendSay("Hey! I'm standing RIGHT HERE! I'm gonna crack you peanut-brains across the head for that!")
sm.setSpeakerID(3000119) # Nefarious Priest
sm.setParam(1)
sm.sendSay("Everybody at once!")
sm.lockInGameUI(False, True)

# The recording's login data (quest records reset, event quests started) is left out, since it would overwrite the
# player's own progress. What the recording leaves out is the fight: the priests attack, and one of them carries the
# Nefarious Pouch (quest 25852). The exit portal (portal/check_25852.py) leads back to Pantheon.
if sm.hasQuest(25852) and not sm.hasItem(4033460):
    for npc, mob, x in ((3000110, 9300550, 350), (3000111, 9300551, 250), (3000114, 9300552, 150),
                        (3000119, 9300550, 0)):
        sm.removeNpc(npc)
        sm.spawnMob(mob, x, 29, False)
