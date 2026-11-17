# Haven: 310070152. Take Me Away (39205): coming here with him is its task (record 39200 5m).
if sm.hasQuest(39205) and sm.getQRValue(39200, "5m") != "1":
    sm.setQRValue(39200, "5m", "1")
    sm.chat("You found a steel plate with strange markings on it.")
