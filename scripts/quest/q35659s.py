# [Genesis Weapon] Traces of Arkarium, the Guardian of Time (35659): defeat Normal Arkarium.
# GenesisLiberation sets 35650 clear=2 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Normal Arkarium, then report to Grendel the Really Old.")
sm.dispose()
