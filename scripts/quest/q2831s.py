# 2831 - Ayan's Rage
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ayan (1022007)

sm.setSpeakerID(1022007)  # Ayan
if sm.sendAskAccept("I guess I'll give it a try!"):
    sm.startQuest(parentID)
    sm.sendNext("#b(An Orange Mushroom appears!)#k Wh-what's that? AAAAAH!!")
    sm.sendSay("How COULD she?! I'm really scared of Orange Mushrooms! I was attacked by a giant Orange Mushroom when I was little! Did 10 Boogies do this? Or was it YOU? Or was it that rascal Blackbull again?!")
else:
    sm.sendNext("Why not? You're the one who brought it to me!")
