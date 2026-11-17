# 21720 - The Puppeteer's Warning
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tru (1002104)

sm.setSpeakerID(1002104)  # Tru
sm.sendNext("Hm? I haven't found anything good on gathering jobs just yet... Did you need something else? Got any interesting news for me?\r\n\r\n")
sm.setPlayerAsSpeaker()
sm.sendSay("(You tell him about meeting the Black Wings Puppeteer.)")
sm.setSpeakerID(1002104)  # Tru
sm.sendSay("So, #p1204001# is the Puppeteer for the Black Wings? It all makes sense now... The Puppeteer must be the one behind the strange behavior of the #o1210102# monsters at #m100000000# and the feral actions of the Golems at #m101000000#. But... Did he mention the Black Mage?")
sm.sendSay("Come to think of it, there have been reports of a group that has been trying to awaken the Black Mage. I always thought it was just a rumor. Turns out it wasn't. Could the prophecies be true? Is it possible that the Black Mage could return?")
sm.sendSay("I think the Black Wings are worth investigating. They may be secretive, but they won't be able to sneak under #p1002104# the Information Dealer's radar. If I find anything out, I'll let you know. Head to #b#m140000000##k and tell #b#p1201000##k about this.")
if sm.sendAskAccept("The return of the hero, the followers of the Black Mage, and the prophecy... All of these things are connected. As the one who woke the hero, #p1201000# should be in the loop about all this. #p1201000# has been researching heroes for quite some time, so I'm sure she'll be able to help."):
    sm.startQuest(parentID)
else:
    sm.sendNext("You don't want to give #p1201000# the dangerous news? She may seem weak on the outside, but remember, she lived alone on that island a long time, just to find you. She's much stronger than you think.")
