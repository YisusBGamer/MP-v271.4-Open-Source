# [Silent Crusade] Explosive Research. Crow's official lines (Quest.wz Say.img).

CROW = 9073022

sm.setSpeakerID(CROW)
sm.sendNext("That...was a close scrape. Stop looking at me like that.")
sm.sendSay("I-I didn't mean to save your life! It was an accident! Why does this always happen to me?!")

if sm.completeQuest(parentID):
    sm.showFieldEffect("Map/Effect.img/crossHunter/chapter/end1")
    sm.sendSayOkay("There must have been something hidden inside that Mystic Gate. I've got to figure this out. Leave me alone, "
                   "you're too weak to be of any help. Only someone #e#rLv. 105+#k could take on the next mission#n.")
