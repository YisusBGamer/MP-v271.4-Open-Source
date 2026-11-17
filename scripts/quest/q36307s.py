# The Adventure Begins (36307) - Sugar in Lith Harbor helps a new Explorer pick a path. The pick is record 1406
# ("1" Warrior .. "5" Pirate), which the instructors' Path of the Warrior..Pirate (1401-1405, from Lv. 10) check at
# the end; a Dual Blade is sent on to Ryden (Thief of the Shade, 2604).
SUGAR = 1541002
PATH_RECORD = 1406
RYDEN_MAP = 103050000  # Kerning City Back Alley

PATHS = [
    ("Warrior", "Dances with Balrog", "Perion", "great strength and high HP, fighting up close"),
    ("Magician", "Grendel the Really Old", "Ellinia", "powerful spells cast from afar"),
    ("Bowman", "Athena Pierce", "Henesys", "arrows shot from a safe distance"),
    ("Thief", "the Dark Lord", "Kerning City", "quick, sneaky attacks"),
    ("Pirate", "Kyrin", "the Nautilus", "flashy chains of skills"),
]

sm.setSpeakerID(SUGAR)
if chr.getSubJob() == 1:
    sm.sendNext("We made it to Victoria Island! Oh, and while you were looking around, a man in black came asking for you. He said his name was #bRyden#k.")
    sm.sendSay("He's waiting in the #bKerning City Back Alley#k. He looked like he had a very secret job in mind for you...")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    if sm.sendAskYesNo("Want me to show you the way to Ryden?"):
        sm.warp(RYDEN_MAP, 0)
    else:
        sm.sendNext("Okay! He's in the back alley of #bKerning City#k. Good luck, #h0#!")
else:
    sm.sendNext("We made it to Victoria Island! Every adventurer here picks a path, and each path has a Job Instructor who teaches it.")
    menu = "Which path sounds the most like you?\r\n\r\n#b"
    for i, p in enumerate(PATHS):
        menu += "#L%d#%s: %s#l\r\n" % (i, p[0], p[3])
    choice = sm.sendNext(menu)
    if choice < 0 or choice >= len(PATHS):
        choice = 0
    name, instructor, town, _ = PATHS[choice]
    sm.createQuestWithQRValue(PATH_RECORD, str(choice + 1))
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.sendNext("A #b%s#k! I'll let #b%s#k in #b%s#k know you're coming." % (name, instructor, town))
    if chr.getLevel() >= 10:
        sm.sendSay("You're already strong enough to advance. Look for the #bPath of the %s#k in your quest list, and %s will take it from there!" % (name, instructor))
    else:
        sm.sendSay("Once you reach #bLv. 10#k, %s will call for you. Until then, hunt a little around Victoria Island!" % instructor)
sm.dispose()
