# 10351 - The Memory of the Heroes
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendNext("Have you finished collecting the Aran Memory Fragments? You're probably a bit short.\r\n#b#L0#You're right. I haven't collected all 12 yet.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You seem to have forgotten all about the Arans... \r\n#b#L0#What are you talking about? I didn't forget about them!#l\r\n#k") != 0:
    sm.dispose()
sel = sm.sendSay("Okay, okay. Then tell me this. What is the name of the girl who is waiting for the heroes to break out of the seal?\r\n#b#L0#Jane#l\r\n#L1#Kong Ji#l\r\n#L2#Lilin#l\r\n#k")
if sel != 2:
    wrong = {
        0: "Are you going to have a party or something?",
        1: "Are you going to just sit there and do nothing?",
    }
    if sel in wrong:
        sm.sendSayOkay(wrong[sel])
    sm.dispose()
if sm.sendAskAccept("You're right! The girl who waits for the heroes in Rien is Lilin. You haven't forgotten."):
    sm.startQuest(parentID)
    sm.sendNext("Perhaps the girl's earnest belief has brought the Arans back to life. By the way, #h0#... You said you were after the Aran Memory Fragments, correct? But you can't always get what you want. Wouldn't you agree?")
