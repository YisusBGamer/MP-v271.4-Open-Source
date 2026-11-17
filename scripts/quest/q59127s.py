# 59127 - Excavation Impossible 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Canary (9390353)

sm.setSpeakerID(9390353)  # Canary
sm.sendNext("#bTristan's Sword#k?")
sm.sendSay("I'm guessing you heard about it from #b#p1101004##k. Look, I know you can hunt a Copper Drake or two and think you're hot stuff, but #bTristan's Sword#k isn't just any item.")
sm.sendSay("Anyway, all I know about #bTristan's Sword#k ended at #b#m105020000##k. Hm, let's see...")
if sm.sendAskAccept("Once you get the key, get it to #bMudry#k."):
    sm.startQuest(parentID)
    sm.sendNext("Get as many Key Fragments as you can. The more, the better.")
else:
    sm.sendNext("I didn't know you were so kind!")
