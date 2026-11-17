# Secret Corridor (921140000), the far end - [Lion King's Castle] Is There Any Hope? (3178)
# Queen Ifia (the escorted monster, 9300295) must still be alive to go on to the Lion King (921140001).
IFIA_ESCORT = 9300295

if sm.hasQuest(3178) and not sm.hasMobsInField(sm.getFieldID(), IFIA_ESCORT):
    sm.chat("Queen Ifia didn't make it... Talk to her on the Roof of the Fifth Tower to try again.")
    sm.warpInstanceOut(211061001, 0)
else:
    sm.warpInstanceIn(921140001, 0)
sm.dispose()
