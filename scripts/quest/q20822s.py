# Start of The Path of Bravery
# Kiku's official lines (Quest.wz Say.img)
KIKU = 1102100
sm.setSpeakerID(KIKU)

response = sm.sendAskAccept("I don't know if they told you during the orientation, but we're here to fight the Black Mage. "
"Right now, you're not fit to fight a black mop. I'm gonna fix that.\r\n"
"You ready for some action?")

if not response:
	sm.sendSayOkay("Are you listening to me?")
	sm.dispose()

sm.removeEscapeButton()
sm.startQuestNoCheck(parentID)
sm.sendSayOkay("Your first stop is #eKimu#n. If you get lost, hit the #e#bQ key#k#n and check your #e#rQuest Info#k#n. "
"That's tip number one: always check your quest info before you go running your mouth!")
