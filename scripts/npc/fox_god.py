# 3002015 Fox God at the Fox Tree (410000002). The Last Gift (Shade 38063, record 38063 = clear): make a wish to the
# Fox God, as Moonbeam showed you. The quest is then finished from the quest window.
if sm.hasQuest(38063) and sm.getQRValue(38063) != "clear":
    sm.setPlayerAsSpeaker()
    sm.sendNext("Dear Fox God, my Fox God. Please grant my wish. My wish is...")
    sm.setQRValue(38063, "clear")
    sm.chatScript("You tried to give Moonbeam's spirits back, but the spirits will not leave you.")
sm.dispose()
