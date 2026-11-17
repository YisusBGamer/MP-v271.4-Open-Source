# 31493 - Infiltrate
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept("Looks like a call from #b#p3001102##k."):
    sm.startQuest(parentID)
    sm.sendNext(u"Kaiser? Can you hear me?#bThis is Kaiser. I\u2019m inside.#k")
    sm.sendSay(u"We\u2019ll keep communications brief while you\u2019re inside. I need you to find any high-ranking Specters in that castle and identify them.")
    sm.sendSay(u"I was told you took down a Specter general and took its emblem before. That\u2019s what we\u2019re looking for. We need to know what they\u2019re capable of.")
    sm.sendSay("Look for any high-ranking  Specters with a sword and take them down. We need to know everything we can. Over and out.")
else:
    sm.sendNext("I lost connection... Maybe I should just wait for another call...")
