# 31429 - Secret Assembly
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tonero (3001004)

sm.setSpeakerID(3001004)  # Tonero
if sm.sendNext("Hello there! Oh, are you surprised my overwhelming alacrity? Do not be! I'm a merchant. I know how to move, and, or, shake it! Besides, I know a few secret back parlors to pass through, here and there.\r\n#L0##bOh. Huh. 'Cause I sure didn't see any paths...#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Haha! Wound tight by the wizardry of my words! Let me introduce the visage that stands before you! I am #b#p3001004##k, a humble merchant.\r\n#L0##bCool. I'm Kaiser, an awesome warrior.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("I knew that. The knight, right? Anyway, that transmitter you used is something that merchants typically use only with one another, so I knew I was safe in coming here. Besides, #p3001002# told me to expect a knight.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I certainly don't care about your hometown or the heritage you acquired. Let's just talk the good talk of business. Shall we?")
