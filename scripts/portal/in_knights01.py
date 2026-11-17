# Portal script in_knights01. Knight Stronghold: Stronghold Entrance -> Knight District 1.
# [Knight Stronghold] Scouting the Stronghold (31124) is finished by scouting the stronghold (Quest.wz: its record
# "end"; "The stronghold's security was tight. You'll need to find a way in..."). Trying the way in while on the quest
# sets it, and turns you back.
SCOUTING = 31124
if sm.hasQuest(SCOUTING):
    if sm.getQRValue(SCOUTING) != "end":
        sm.setQRValue(SCOUTING, "end", False)
        sm.updateQRValue(SCOUTING, True)
    sm.chat("The stronghold's security is tight. You'll need to find a way in... Report back to Chief Alex.")
else:
    sm.warp(271030100, 4)
sm.dispose()
