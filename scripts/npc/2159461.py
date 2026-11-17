# Treasure Chest (2159461), Ariant Treasure Vault 915020101: the journals for "The Lowdown" (25111, Phantom 3rd job).
# Finding them sets the quest's record (25113); the quest is then completed in the quest window, which gives the 3rd job
# (ClassQuestSetupModule). Talking to the chest again completes it here, in case the quest window doesn't.
QUEST = 25111
RECORD = 25113
if sm.hasQuest(QUEST) and chr.getJob() in (2410, 2411):
    if sm.getQRValue(RECORD) != "1" and chr.getJob() == 2410:
        sm.setQRValue(RECORD, "1", False)
        sm.chat("You found your old journals. Complete 'The Lowdown' in your quest window.")
    else:
        sm.questJobAdvance(QUEST, 2410, 2411)
else:
    sm.sendSayOkay("(A chest full of old treasure. There's nothing else you need in here.)")
sm.dispose()
