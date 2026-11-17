# [Genesis Weapon] Traces of Lucid, Keeper of Nightmares (35664): defeat Hard Lucid.
# GenesisLiberation sets 35650 clear=7 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Hard Lucid, then report to Grendel the Really Old.")
sm.dispose()
