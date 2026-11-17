# Showa Postbox (9100036), Showa Town (801000000)
# [Showa Town] Welcome to Showa Town! (66115) is the letter in the postbox: reading it finishes the quest
# (it has no NPC to hand it in to).
WELCOME = 66115

sm.setSpeakerID(9100036)
if sm.hasQuest(WELCOME):
    sm.sendNext("There's a letter addressed to you in the postbox.")
    sm.sendSay("#b\"To the adventurer who answers this letter: the hoodlums of the Yakuza still prowl the streets "
               "outside Showa Town. We need someone to look into it. Please talk to Pipopa.\"#k")
    sm.completeQuestNoCheck(WELCOME)
else:
    sm.sendSayOkay("The postbox is empty. Letters for the residents of Showa Town are collected here.")
sm.dispose()
