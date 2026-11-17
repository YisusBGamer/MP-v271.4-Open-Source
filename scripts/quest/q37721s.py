# [Yum Yum] Familiar, But Not (37721), start - the creature that looks like Afrien (3004718).
# The client has no lines for it; written from the quest's own Quest.wz summaries ("Maybe he won't be hostile, like
# Caster and Bappy? Talk to him." / "You've eliminated the creature that looks like Afrien."). Same pattern as
# Arkarium's Bastille (31176).
CREATURE_NPC = 3004718
CREATURE = 8642091

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(CREATURE_NPC)
    sm.sendNext("......")
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("(It looks just like Afrien... but something is off. Maybe it won't be hostile, like Caster and Bappy. Talk to it?)"):
        sm.startQuest(parentID)
        sm.sendSayOkay("(The creature lets out a roar and lunges at you!)")
if sm.hasQuest(parentID) and not sm.isComplete(parentID) and sm.getFieldID() == 450015230 and not sm.hasMobsInField(450015230, CREATURE):
    sm.spawnMob(CREATURE, 90, 117, False)  # on the ground below where it floats (the NPC stays for other players)
sm.dispose()
