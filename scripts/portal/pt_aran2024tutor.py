# Snow Island Ice Cave (140090010-140090013), Aran's v265 start: each cave's west exit leads on once that cave's quest
# is done (The Return of the Hero 36901, The Missing Weapon 36902, Abilities Lost 36903, A Gift for the Hero 36904);
# the last one comes out in Rien.
STEPS = {
    140090010: (36901, 140090011, "Talk to the man who found you first."),
    140090011: (36902, 140090012, "Finish The Missing Weapon before going on."),
    140090012: (36903, 140090013, "Defeat the monsters in the cave first (Abilities Lost)."),
    140090013: (36904, 140000000, "Bring the Piece of Bamboo and the Wood first (A Gift for the Hero)."),
}

step = STEPS.get(chr.getFieldID())
if step is None:
    sm.dispose()
else:
    quest, next_map, hint = step
    if sm.hasQuestCompleted(quest):
        sm.warp(next_map, 0)
    else:
        sm.chat(hint)
    sm.dispose()
