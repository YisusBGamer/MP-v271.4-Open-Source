# The Afterlands: exploring this field for A World Devoid of Color (63045).
if sm.hasQuest(63045) and sm.getQRValue(63045, "chk2") != "1":
    sm.setQRValue(63045, "chk2", "1")
    sm.chatScript("Everything here is grey... and the Ravens are pecking the last of the color from the trees.")
sm.dispose()
