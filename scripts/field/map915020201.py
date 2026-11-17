# 915020201 Leafre Treasure Vault (Phantom 4th job): the journal behind Aria's Portrait, for "The Empress and the Thief"
# (25122, record 25124). The quest is completed in the quest window, which gives the 4th job (ClassQuestSetupModule).
# Coming back with the journal found and the quest still open completes it here, in case the quest window doesn't.
QUEST = 25122
RECORD = 25124
if 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38054) and not sm.hasMobsInField(915020201, 9300872):
    pos = chr.getPosition()
    sm.spawnMob(9300872, pos.getX() + 250, pos.getY(), False)  # Shade: Phantom Reunion - Master Thief Phantom attacks
elif chr.getJob() == 2411 and sm.hasQuestCompleted(25121):
    if sm.getQRValue(RECORD) != "1":
        sm.setQRValue(RECORD, "1", False)
        if sm.hasQuest(QUEST):
            sm.chat("You found your journal behind Aria's Portrait. Complete 'The Empress and the Thief' in your quest window.")
        else:
            sm.chat("Your journal is behind Aria's Portrait. Accept and complete 'The Empress and the Thief' in your quest window.")
    elif sm.hasQuest(QUEST):
        sm.questJobAdvance(QUEST, 2411, 2412)
