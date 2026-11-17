# 940000140 East Pantheon Border: the Battle Hounds are beaten - Protective Shield Patrol (25749) waits for record
# 25796 = "1". Walk back west to the East Sanctum and report to Cartalion in Pantheon.
if sm.hasQuest(25749) and sm.getQRValue(25796) != "1":
    sm.startQuest(25796)
    sm.setQRValue(25796, "1")
    sm.chatScript("Although it was only the preliminary attack using trained animals, Magnus's attack came faster "
                  "than expected. Report to Cartalion.")
sm.dispose()
