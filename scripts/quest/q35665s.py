# [Genesis Weapon] Traces of Verus Hilla the Red Witch (35665): defeat Verus Hilla.
# GenesisLiberation sets 35650 clear=8 (and gives the Black Mage Remnant) when the boss dies with this quest in progress.
sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID):
    sm.chat("[Genesis Weapon] Defeat Verus Hilla, then report to Grendel the Really Old.")
sm.dispose()
