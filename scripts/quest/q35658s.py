# [Genesis Weapon] Traces of Von Leon the Lion King (35658): defeat Hard Von Leon.
# GenesisLiberation sets 35650 clear=1 when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Von Leon, then report to Grendel the Really Old.")
sm.dispose()
