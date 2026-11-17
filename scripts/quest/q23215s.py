#23215 | True Awakening (Old 4th job quest)
# Mastema's official lines (Quest.wz Say.img), then her directions to the Path of Time.
if sm.questStartDialogue(parentID, 2450017):
    sm.setSpeakerID(2450017)
    sm.sendNext("Normally the Temple of Time is the only place where you can tinker with time, but I found another, #h #. Word is, there is something wrong with time in Ludibrium.")
    sm.sendNext("There's a place called #bPath of Time#k on Ludibrium's Clock Tower Bottom Floor. I can stabilize the Crack in Time so you can travel to where you need to go.")
    sm.sendNext("Yeah, I know I look funny like this, but this form allows me to move between dimensions. I'll meet you at the place... I'm taking the scenic route.")
    sm.sendNext("Entering the Crack of Time will shift you to a point in the past, but don't worry about messing things up. The way this works, your actions won't affect the future.")
    sm.warp(220050300, 2)
sm.dispose()
