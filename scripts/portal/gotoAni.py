# 211061000 Fifth Tower, east00 (the bottom right passage): Ani's Jail, for [Lion King's Castle] The Lion King's
# Gatekeeper (3142) - Prison Guard Ani (field/summon_ani). Also for Looking Into the Lion King: Final (3147), which
# asks you to defeat Ani again and bring back the Congealed Evil.
if sm.hasQuest(3142) or (sm.hasQuest(3147) and not sm.hasItem(4033114)):
    sm.warpInstanceIn(211061100, 1)
else:
    sm.chat("The passage to Ani's Jail is locked.")
sm.dispose()
