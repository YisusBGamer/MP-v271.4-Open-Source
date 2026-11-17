# [Labyrinth of Suffering] On the Offensive (36760): to Neinheart on the White Spear (993063000), by the small
# airship on Deck 3
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("It's nearly the time to depart for the #rblack sun#k. #bEmpress #p3003701##k and #b#p3003702##k "
                        "are waiting at the #r#m993063000##k.\r\n\r\n(* You can move to #r#m993063000##k by taking the "
                        "small airship in #b#m450009300##k.)"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.setQRValue(parentID, "dummy", "1")
sm.dispose()
