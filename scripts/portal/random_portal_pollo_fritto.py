from net.swordie.ms.client.surprisemission import SurpriseMissionCompleter

POLLO = 9001059
FRITTO = 9001060

ids = [POLLO, FRITTO]

text = {
    POLLO : "Hello, I am #rPollo#k the bounty hunter.\r\nMy brother #bFritto#k and I are the best at what we do! Ha ha!",
    FRITTO : "Hello, I am #bFritto#k the bounty hunter.\r\nMy brother #rPollo#k and I are the best at what we do! Ha ha!"
}

text2 = {
    POLLO : "People look at me and instantly see that I am not to be trifled with. How would you like to come with me"
            "and witness my greatness?\r\n#L0#Yeah, let's go.#l\r\n#L1#Nah, not now.#l",
    FRITTO : "Many people doubt my skills, but one look proves them wrong. How would you like to come with me and"
             "witness my greatness?\r\n#L0#Yeah, let's go.#l\r\n#L1#Nah, not now.#l"
}

# the minigames that work here: Pollo's Bounty Hunt and Guard the Castle Gates, Fritto's Egg Stealing and Eagle Hunt
# (Midnight Hunt, Stormwing and Treasure Catch aren't coded), each with its time limit in seconds. Courtship Dance
# (993000400) is left out: the v265 client builds fieldType 133 as a plain field, so its dance UI never appears.
maps = {
    POLLO : [
        [993000000, 180],  # Bounty Hunt
        [993000100, 180],  # Guard the Castle Gates
     ],
    FRITTO : [
        [993000300, 30],   # Egg Stealing
        [993000200, 30],   # Eagle Hunt
    ]
}

rand = sm.getRandomIntBelow(2)
npc = ids[rand]
field = chr.getField()
sm.setSpeakerID(npc)
sm.sendNext(text[npc])
answer = sm.sendNext(text2[npc])
if answer == 0:
    chr.setPreviousFieldID(chr.getFieldID())
    game = maps[npc][sm.getRandomIntBelow(len(maps[npc]))]
    sm.warpInstanceIn(game[0])
    sm.setInstanceTime(game[1])
    field.removeLife(objectID, False)

    if chr is not None:
        SurpriseMissionCompleter.completeUseHiddenPortal(chr)
