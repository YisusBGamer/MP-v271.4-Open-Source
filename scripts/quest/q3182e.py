# 3182 - Alcaster's Crystal
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Murt (2161004)

sm.setSpeakerID(2161004)  # Murt
sm.sendNext("It's you... Did you deliver my letter to Alcaster?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("What? The curse has been broken? Amazing...")
    sm.sendSay("You weren't kidding... I'm not cold anymore. I'm not in pain either! I can move freely! Ha ha ha ha! Thanks a lot!")
    sm.sendSay("I owe Alcaster big! And you, too. As a token of my gratitude, I'll give you a #bpiece of equipment#k or a #bweapon#k I found in this castle, if you bring me the #b#t4310009##k or #b#t4310010##k.")
    sm.sendSay("What? You want it for free? Nonsense!")
