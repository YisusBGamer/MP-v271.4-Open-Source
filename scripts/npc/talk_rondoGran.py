# Rondo (1520002) in Pantheon - Explorer story, Grandis at War! (32250)
# Hands the quest in once he's seen the Eastern Border (record 32364, portal/enter_eastborder.py).
RONDO = 1520002
GRANDIS_AT_WAR = 32250

if sm.hasQuest(GRANDIS_AT_WAR) and sm.getQRValue(32364) == "1":
    sm.questEndDialogue(GRANDIS_AT_WAR, RONDO)
elif sm.hasQuest(GRANDIS_AT_WAR):
    sm.setSpeakerID(RONDO)
    sm.sendSayOkay("Cartalion can doubt all he wants, but I WILL find my people. And you'll help me, when the time comes.")
else:
    sm.setSpeakerID(RONDO)
    sm.sendSayOkay("Is this Grandis? Looks kinda strange if you ask me.")
sm.dispose()
