# Portal script enterSnowDragon.
# Snowy Forest -> the Cave of Silence at the center of Slumbering Dragon Island (Evan's story):
#   Slumbering Dragon Island (22580): reaching the cave is the goal (quest record 22599 = 2).
#   Secret Organization's Fifth Mission (22588): the Ice Wall to break (your own copy).
#   Dangerous Premonition (22589): the Black Wings ambush in the cave (your own copy).
#   After that: the cave where the sleeping Onyx Dragon is.
if sm.hasQuest(22580):
    sm.setQRValue(22599, "2", False)
    chr.chatMessage("Mir feels it strongly now... something is sleeping deep inside this cave.")
    sm.warp(914100020, 1)
elif sm.hasQuest(22588) and sm.getQRValue(22605) != "1":
    sm.warpInstanceIn(914100022, 1)
elif sm.hasQuest(22589) and sm.getQRValue(22604) != "1":
    sm.warpInstanceIn(914100023, 1)
elif sm.hasQuestCompleted(22588):
    sm.warp(914100021, 2)
else:
    sm.warp(914100020, 1)
sm.dispose()
