# 915010001 Orbis Treasure Vault (Phantom 2nd job)
if sm.hasQuest(25101) and sm.getQRValue(25103) != "1":
    sm.lockInGameUI(True)
    sm.removeEscapeButton()
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendNext("This place is frighteningly well-preserved. Now where did I put that skill book? The small cabinet with the world's first meso? The trunk with the only existing Reverse Miracle Cube?")
    sm.lockInGameUI(False)
    sm.chat("Search the Small Cabinet.")
