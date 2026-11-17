# 31504 - Road to Treglow
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001100)

sm.setSpeakerID(3001100)  # Edea
sm.sendNext("Welcome back, Kaiser. You look more determined than usual...")
sm.sendSay(u"I\u2019ve decided that #rTreglow#k's laboratory is our next target. #rVictor#k and #rTreglow#k are relatively equally matched, but #rTreglow#k has far more important information in his files.")
sm.sendSay(u"Go through the #b#m401050000##k to the #b#m401052000##k. According to #b#p3001107##k\u2019s troops, the path to #bTreglow#k is being guarded by chemically-altered beasts.")
if sm.sendAskAccept("Keep quiet for now, but it would be smart to clear out some of #rTreglow#k's creations, like the #r#o2400118##k monsters. Also, bring me any #b#t4000737##k items you find. It might be helpful."):
    sm.startQuest(parentID)
    sm.sendNext("Return to me with the flasks this time, but our future communications would be better suited to the #bD-02 Receiver#k.")
else:
    sm.sendNext("Please talk to me again once you're ready.")
