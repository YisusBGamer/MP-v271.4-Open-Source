# 21752 - Find the Key
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Loha (2131003)

sm.setSpeakerID(2131003)  # Loha
if sm.sendNext("Aren't you... Aran? You're alive? Well, look at you, of course you're alive! No way you'd let the Black Mage bring you down.\r\n#L0##b(And yet, that's just what happened...)#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("So, what brings you here? Shouldn't you be out vanquishing evil, saving the world, that sort of thing?\r\n#L0##bI came to get the #t4032325#.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Ah... You mean the #t4032325#? Hold on one second. Let me see...")
sm.startQuest(parentID)
