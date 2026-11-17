# Hidden Street: Path to the Reclamation HQ (401100000), Angelic Buster's Heliseum story.
# Kaiser (3001010) isn't placed on any map, but 'Can I hold the Cannon?' (31614) and 'That's a Lot of Firepower'
# (31615) need him. "You met up with Kaiser at the Path to the Reclamation HQ" (the quest's own text): he waits here
# while either quest needs him. Your own copy of the map (pt_goHelisium00).
KAISER = 3001010
CRATE = 4033388  # Blue Dragon Cannon Crate

if sm.hasQuest(31614) or sm.hasQuestCompleted(31614) and not sm.hasQuestCompleted(31615):
    sm.spawnNpcIfAbsent(KAISER, -250, 182)
    if sm.hasQuest(31615) and not sm.hasItem(CRATE) and sm.canHold(CRATE):
        sm.giveItem(CRATE)  # lost it on the way: Kaiser still has the cannon ready
