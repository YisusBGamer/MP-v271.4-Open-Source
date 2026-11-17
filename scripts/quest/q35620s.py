# [Moonbridge] Terror in the Fog (35620), Neinheart on Deck 2. Next: Confronting the Giant Monster (35628), from
# the quest notifier (v265 dropped the Nautilus and Edea steps 35616-35619 and 35621-35627).
started = sm.questStartDialogue(parentID)
if started:
    sm.questEndDialogue(35620)
sm.dispose()
