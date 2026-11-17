# 310040200 Mine Entrance. Into the Mine (Evan 22611, record 22615 = "1"): Hiver said to meet him at the Mine; it was
# a trap, and a suspicious girl stepped in. Reaching the Mine counts; then confront Bavan in Edelstein.
if sm.hasQuest(22611) and sm.getQRValue(22615) != "1":
    sm.startQuest(22615)
    sm.setQRValue(22615, "1")
    sm.chatScript("Hiver laid a trap for you! But a suspicious girl intervened. Go to Edelstein and confront Bavan!")
sm.dispose()
