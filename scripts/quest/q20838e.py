# 20838 Certified Knight - Cygnus tutorial quest (Kia). Official lines (Quest.wz Say.img).
# The quest takes the 3 Proof of Exam itself (Act.img); Kia's chair is her own gift.
sm.setSpeakerID(1102104)
sm.sendNext("Let me see your Proof of Exam?")
if sm.hasItem(4033670, 3):
    sm.completeQuest(parentID)
    sm.giveItem(3010060)
    sm.sendSayOkay("Not bad, not bad. I'm gonna give you something of my own design. You can hit X to use it.")
