# [Genesis Weapon] Traces of Lotus, the Wing Master (35661): defeat Hard Lotus.
# GenesisLiberation sets 35650 clear=4 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Lotus, then report to Grendel the Really Old.")
sm.dispose()
