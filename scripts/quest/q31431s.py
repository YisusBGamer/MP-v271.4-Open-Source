# 31431 - Enter Romero
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept("The transmitter is ringing. Ah. Ah! I GOTTA PICK UP!"):
    sm.startQuest(parentID)
    sm.sendNext(u"Bzzz...\r\n'Testing, testing. Hey! You hear me?'\r\n\r\n#b'Yeah, but this doesn't sound like #p3001004#. Who's this?'#")
    sm.sendSay(u"\u201cYo, this is #p3001005#, the trade king of the merchant crew. Don't go nowhere, I'll come to you!\u201d")
else:
    sm.sendNext("On second thought...I WON'T pick it up.")
