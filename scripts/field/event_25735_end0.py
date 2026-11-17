# 940000020 Pantheon Vacant Lot - Evil Auras! (25735, Kaiser): the priests got away. 25735 waits for record 25802 to
# say "1"; the quest is finished from the quest window. The exit leads back to Pantheon.
if sm.hasQuest(25735) and sm.getQRValue(25802) != "1":
    sm.startQuest(25802)
    sm.setQRValue(25802, "1")
    sm.chatScript("The Nefarious Priests escaped. They must be after the East Sanctum Relic... Tell Cartalion.")
sm.dispose()
