# [Genesis Weapon] Traces of Will, the King of Spiders (35663): defeat Hard Will.
# GenesisLiberation sets 35650 clear=6 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Will, then report to Grendel the Really Old.")
sm.dispose()
