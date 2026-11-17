# 23681 - A Gift Most Manly 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Professor Dreamboat (2300001)

sm.setSpeakerID(2300001)  # Professor Dreamboat
if sm.sendNext("Hello #b#h0##k, what's up?\r\n#L0##bI am going to visit a friend and I've been told I should bring a souvenir.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Ah, yes, I am very experienced in receiving gifts. Tell me about your friend.\r\n#L0##bYou are statistically very close to them. What would you like?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Most people tend to buy me beard softeners, expensive hair products, or... I guess #bbooks#k. In fact, I've got a whole pile of those things, if you want one."):
    sm.startQuest(parentID)
    sm.sendNext("Feel free to grab one off of one of the #bbook piles#k around here. I couldn't care less.")
else:
    sm.sendNext("Not a fan of the book idea? I'm always disappointed too. There are so many words...")
