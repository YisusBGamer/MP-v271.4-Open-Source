# Ice Wall broken (914100022): Secret Organization's Fifth Mission (22588, Evan) is done here (quest record
# 22605, which the quest checks). Back to the Snowy Forest; report to Ibech.
if sm.hasQuest(22588) and sm.getQRValue(22605) != "1":
    sm.setQRValue(22605, "1", False)
    chr.chatMessage("The Ice Wall shatters. Behind it, a huge dragon sleeps... Report back to Ibech.")
    sm.warpInstanceOut(914100010, 2)
