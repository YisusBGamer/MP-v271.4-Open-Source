# Korean Folk Town: after Pursue Hongbu (30405). In GMS a scene showed Nolbu collapsing at the Goblin House, and
# "How Is Nolbu?" (30406) started at the Fallen Nolbu NPC that only that scene placed. The scene is played here
# instead, and 30406 is done with it (it has nothing to hand in). Chumji then has the Stone of Rage (30407).
FALLEN_NOLBU = 2074109
HONGBU = 2074001
if not sm.hasQuestCompleted(30406):
    sm.setSpeakerID(HONGBU)
    sm.sendNext("Brother! Brother, wake up! ...He just collapsed when he saw me. One moment he was screaming at me, "
                "and the next...")
    sm.setSpeakerID(FALLEN_NOLBU)
    sm.sendNext("Ugh... my head... Hongbu? Why is everyone staring at me like that? Did I... did I chase you all the "
                "way out here?")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(A small red stone rolls out of Nolbu's sleeve. It's warm, as if it were full of anger.)")
    sm.setSpeakerID(FALLEN_NOLBU)
    sm.sendNext("I've never felt so angry in my life... It wasn't me. It was that stone. Take it, take it away from "
                "me!")
    sm.startQuestNoCheck(30406)
    sm.completeQuestNoCheck(30406)
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay("(You should show the red stone to #bChumji#k in Korean Folk Town.)")
sm.dispose()
