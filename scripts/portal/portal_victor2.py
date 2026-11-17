# Portal script "portal_victor2", 401051003 Darkheart Workshop 2nd Floor Stairs, up to Workshop 2nd Floor Area 1.
# Failure on the 2nd Floor (31513): a special device keeps you out - finding that out is the quest (record 31577).
# Breaking and Entering - 1F (31517): with the Distorted Light Essence you get through (record 31578).
if sm.hasQuest(31513):
    sm.startQuest(31577)
    sm.setQRValue(31577, "1")
    sm.chatScript("Looks like you can't enter the 2nd floor of the Workshop due to some sort of special device.")
else:
    if sm.hasQuest(31517) and sm.hasItem(4033577):  # Distorted Light Essence
        sm.startQuest(31578)
        sm.setQRValue(31578, "1")
    sm.warp(401051100, 1)  # Victor's Workshop : Workshop 2nd Floor Area 1, portal west00
sm.dispose()
