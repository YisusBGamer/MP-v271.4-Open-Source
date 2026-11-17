# Sir Blacksmith (1203000), in the Mirror of Desire's memory (914021000). Aran 2nd job (36962 Bringing Back the
# Brain): reliving his test brings back the memory of how Aran got Maha; then back to Maha in Rien.
QUEST = 36962

sm.setSpeakerID(1203000)
if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "clear") != "cl":
    sm.sendNext("So you're the one who wants the finest polearm in the world? Words are cheap. Show me you're worthy "
                "of it.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("#b(You remember now... You passed Sir Blacksmith's test, and he forged Maha for you.)#k")
    sm.setQRValue(QUEST, "clear", "cl")
    sm.sendSayOkay("#b(Some of your skills come back along with the memory. Go back to #p1201002# in Rien.)#k")
    sm.warpInstanceOut(140000000, 0)
elif sm.sendAskYesNo("Leave the Mirror of Desire?"):
    sm.warpInstanceOut(140030000, 0)
sm.dispose()
