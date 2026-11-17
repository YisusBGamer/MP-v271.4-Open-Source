# 38120 [Hotel Arcus] 'Til We Meet Again - end (Rusty, Hotel Arcus Saloon). quest/done_38120 then gives the 'Slow and
# Steady' medal and its title quest (38121), which Karote and Odium need.
RUSTY = 3005001

if sm.hasQuest(parentID):
    sm.setSpeakerID(RUSTY)
    sm.setBoxChat()
    sm.sendNext("Heading out, are ya? Can't say I'm surprised. Folks like you never stay put for long.")
    sm.sendNext("You've done right by Hotel Arcus. The door's always open, so don't be a stranger.")
    sm.completeQuest(parentID)
sm.dispose()
