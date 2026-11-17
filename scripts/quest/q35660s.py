# [Genesis Weapon] Traces of the Tyrant Magnus (35660): defeat Hard Magnus.
# GenesisLiberation sets 35650 clear=3 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Magnus, then report to Grendel the Really Old.")
sm.dispose()
