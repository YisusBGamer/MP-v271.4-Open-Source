# End of Knight's Cavalier
KIMU = 1102004
FEATHERED_NOBLESSE_HAT = 1003769

sm.setSpeakerID(KIMU)

if not sm.isEquipped(FEATHERED_NOBLESSE_HAT):
	sm.sendSayOkay("It doesn't look like you put your hat on yet... "
	"Press the #bHotkey I#k to open the inventory window, #rthen double-click on the equipment#k.\r\n"
	"You can see what you have equipped by pressing the #bHotkey E#k.")
	sm.dispose()

sm.sendNext("Putting on equipment's easy, huh? "
"It's a good thing, because you're gonna need a lot better gear to be a real knight! "
"Keep an eye out for new stuff all the time.")

sm.completeQuest(parentID)
sm.sendSayOkay("I found Kinu in a pile of books. "
"He'll tell you what you need to know, or possibly just put you to sleep. Or both.")