# Who Is Baroq? Part 2 (36463): "Mihile" in Tiru's Forest is Baroq in disguise. Keep him talking: three questions only
# the real Mihile could answer. A wrong pick gets brushed off and he talks on (ask again); three right ones expose him
# and get Ereve's Lost Treasure back. Then report to Neinheart.
MIHILE = 1541108
TREASURE = 4036863
QUESTIONS = (
    ("Ah, a new recruit! Neinheart sent you to help me look for the intruder, I suppose.",
     ("Which Chief Knight trained you?", "Do you know where the intruder went?", "Nice weather today."), 0,
     "I... trained under... well, all of them, really. Every knight did."),
    ("Ahem. As I said, I've been searching these woods all morning.",
     ("Your armor looks different today.", "What were you searching for?", "Shouldn't you be guarding the Empress?"), 2,
     "The Empress? She's... perfectly safe. Neinheart has it covered, yes."),
    ("Why all these questions? A knight's duty is to act, not to talk.",
     ("You're right, sorry.", "Then show me your sword arm, Sir Mihile.", "The real Mihile never leaves her side."), 2,
     "...Hah! Sharper than you look, recruit."),
)


if sm.questStartDialogue(parentID, MIHILE) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MIHILE)
    for line, options, right, reply in QUESTIONS:
        menu = line + "\r\n" + "".join("#L%d##b%s#k#l\r\n" % (i, o) for i, o in enumerate(options))
        while sm.sendNext(menu) != right:
            sm.sendNext("Hmm? I don't have time for idle chatter, recruit.")
        sm.sendNext(reply)
    sm.sendNext("Enough! Yes, I am Baroq, the Master of Disguise of the Black Wings! You'll never catch me!")
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(Baroq fled in a puff of smoke... and dropped something: Ereve's Lost Treasure!)#k")
    if not sm.hasItem(TREASURE):
        sm.giveItem(TREASURE)
    if sm.completeQuest(parentID):
        sm.chat("You exposed the Master of Disguise and got Ereve's treasure back. Report to Neinheart.")
sm.dispose()
