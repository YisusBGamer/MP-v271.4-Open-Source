# [Genesis Weapon] Traces of Damien, the Sword of Destruction (35662): defeat Hard Damien.
# GenesisLiberation sets 35650 clear=5 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Damien, then report to Grendel the Really Old.")
sm.dispose()
