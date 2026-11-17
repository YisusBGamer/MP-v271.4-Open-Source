# Maple Tree (1541068), Burning Maple Tree Hill: The Miracle of the Maple Leaf (36323). The Seal Stone restores Maple
# Island; then back to the Bowman Instructional School for Mysterious Power (36324).
SEAL_STONE = 4036847
QUEST = 36323

if not sm.hasQuest(QUEST):
    sm.sendSayOkay("#b(The Maple Tree's leaves rustle softly.)#k")
    sm.dispose()
sm.setPlayerAsSpeaker()
sm.sendNext("#b(You place your hand on the burning Maple Tree and hold up the Seal Stone of Victoria Island.)#k")
sm.setSpeakerID(1541063)
sm.sendSay("Your heart's wish to protect others... that is the true key. Let the stone answer it.")
sm.setPlayerAsSpeaker()
sm.sendSay("#b(The Seal Stone blazes with light. The flames die away and the scorched hills turn green again.)#k")
sm.setSpeakerID(1541006)
sm.sendSay("Thank you... I remember now who I really am. I'll be watching over you, from the Maple Tree. Always.")
if sm.hasItem(SEAL_STONE):
    sm.consumeItem(SEAL_STONE, 1)
sm.completeQuest(QUEST)
sm.setPlayerAsSpeaker()
sm.sendSayOkay("#b(Sugar has returned to the Maple Tree. Tell Athena Pierce what happened on Maple Island.)#k")
if chr.getInstance() is not None:
    sm.warpInstanceOut(100000201, 0)
else:
    sm.warp(100000201)
sm.dispose()
