# Small Cabinet (1403000), Orbis Treasure Vault 915010001: Raven's memoirs for "That's So Raven" (25101, Phantom 2nd job).
# Finding them sets the quest's record (25103); the quest is then completed in the quest window, which gives the 2nd job
# (ClassQuestSetupModule). Talking to the cabinet again completes it here, in case the quest window doesn't.
QUEST = 25101
RECORD = 25103
if sm.hasQuest(QUEST) and chr.getJob() == 2400:
    if sm.getQRValue(RECORD) != "1":
        sm.setQRValue(RECORD, "1", False)
        sm.removeEscapeButton()
        sm.setPlayerAsSpeaker()
        sm.sendNext("Let's see... 'A History of Ribbon Pigs' first edition... that's not it. 'The Great Mushroom Uprising'... why did I even steal this? Ah, there we are! I'll be back to my old self in no time!")
        sm.chat("You found the 'Memoirs of Raven: Ultimate Thief Master'. Complete 'That's So Raven' in your quest window.")
    else:
        sm.questJobAdvance(QUEST, 2400, 2410)
elif sm.hasQuest(QUEST) and chr.getJob() == 2410:
    sm.questJobAdvance(QUEST, 2400, 2410)
else:
    sm.sendSayOkay("(A small cabinet. There's nothing else you need in here.)")
sm.dispose()
