# 993160001 Magic Training Grounds Entrance: Tired of Tranquility (Hoyoung 39510, record 39510 move=1) is done here.
# Its Quest.wz end NPC is the Maple Administrator, who isn't on Mt. Songyu, so it's handed in on arrival.
if sm.hasQuest(39510):
    sm.setQRValue(39510, "move", "1")
    if sm.completeQuest(39510):
        sm.chatScript("Feels weird to come to the training grounds when I COULD just be slacking off and enjoying all this free time.")
