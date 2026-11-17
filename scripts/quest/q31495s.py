# 31495 - Escape the Tyrant's Castle
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001100), taken from the other quest phase

sm.setSpeakerID(3001100)  # Edea
if sm.sendAskAccept("#b#p3001107##k called. I should be able to get out of the castle with his help."):
    sm.startQuest(parentID)
    sm.sendNext(u"Kaiser, you there? I\u2019m working out a floor plan of the castle and I think I found a good escape route.")
    sm.sendSay(u"There\u2019s a weirdly-shaped object in the corner of the first field. There\u2019s a secret passage behind that. Take it outside and report back to #b#p3001000##k. Good luck!")
else:
    sm.sendNext("Let me know when you get somewhere safe.")
