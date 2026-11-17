# 25584 - Secret Ritual
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Grendel the Really Old (1032001)

sm.setSpeakerID(1032001)  # Grendel the Really Old
if sm.sendNext("Now get out of my town! But I want you to come back when you are able fully control both light and darkness. Didn't you acquire everything you needed?\r\n#L0##bDon't be so hasty. There's one more thing I need.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("What is it now? I've helped you enough!\"\r\n#L0##bCalm yourself, old man. All I need is a secluded place to do my ritual. Preferably someplace with an ample supply of magical energy.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Hm. I know just the place. The Black Wings used to hold their meetings there; seems like a good fit for someone like you. Head to the #beast end of the Golem Temple Entrance#k and take the portal. #bDouble-click on the Vampiric Lantern#k to teleport nearby."):
    sm.startQuest(parentID)
    sm.sendNext("Heed my words. The power of darkness is much more dangerous than you think. Learn to control it before the worst happens.")
else:
    sm.sendNext("Do whatever you want. Just LEAVE!")
