# [Convergence] (39712-39722, Lv 260): the chase after Grendel from Ereve through Lachelein, Morass, Esfera and Limina
# to the world's end, between Aftermath (39701) and Cernium (39801). Shared by its quest, field, portal and NPC scripts
# (execfile). Quest.wz has no Say or Act data for these quests: the lines below follow each quest's QuestInfo texts.
# Each stage starts at an NPC in a story map; its portal leads on, through the stage's fight maps (own copy, Map.wz
# life) when it has them; the stage's quest ends on arriving in the next story map.

OUTPOST = 993120000  # Maple Alliance Outpost (Aftermath)
WORLDS_END = 993135000  # Barrier Between Worlds
CERNIUM_ARRIVAL = 410000510  # where [Cernium (Before)] A Traveler from Beyond the Sea begins
FIRST, LAST = 39712, 39722
NAMES = {3004203: "Neinheart", 3004301: "Neinheart", 3004302: "Elwin", 3004303: "Lily", 3004304: "the Protective Mask"}

# quest: (start NPC, its story map, the portal's next map or fight maps, the story map it ends in, start line, end line)
STAGES = {
    39712: (3004203, OUTPOST, None, 993130000,
            "Something urgent came up after the Black Mage fell. Will, the King of Spiders, is being questioned by "
            "Grendel the Really Old somewhere beneath Ereve. Come with me, we'll see them both.",
            "(You arrive at Ereve with Neinheart... and a deafening roar shakes the ground beneath your feet.)"),
    39713: (3004301, 993130000, (993130150, 993130300), 993130500,
            "That came from the underground cell where Will is held. The cell is to the left of here. Go, I'll follow!",
            "(You beat back the monsters in the ruined cell. Hawkeye says Will set off a Core he was hiding, and "
            "Grendel the Really Old vanished in the chaos.)"),
    39714: (3004301, 993130500, None, 993131000,
            "The Core Will used held the Black Mage's memories, visions of a possible future. Grendel grew unstable "
            "and opened a portal to the Arcane River. Go with Elwin and Lily and find him.",
            "(You set out with Elwin and Lily and arrive in Lachelein, the Dreaming City.)"),
    39715: (3004304, 993131000, (993131100, 993131150, 993131200), 993131500,
            "You came after Grendel? I have something to tell you. Meet me at the Clocktower.",
            "(Inside Lucid's dream, you see her speaking with Grendel the Really Old. He mutters something about the "
            "Black Mage and vanishes.)"),
    39716: (3004303, 993131500, None, 993132000,
            "The Protective Mask pulled Lucid out of her nightmare; she's safe now. The traces of Grandpa's gate won't "
            "last. Take the portal, quick!",
            "(You hurry through the gate to the next place: Morass, the Swamp of Memory.)"),
    39717: (3004302, 993132000, (993132100, 993132150, 993132200), 993132500,
            "Morass, the Swamp of Memory. Grandpa's trail goes on through this portal. Let's keep going.",
            "(Past the monsters stands a likeness of the White Mage, made of Erda. He says the Transcendents came to "
            "be to end the chaos of the world before them. Grendel didn't find his answer here.)"),
    39718: (3004302, 993132500, None, 993133000,
            "Grandpa still hasn't found what he's looking for about the Transcendents. I've opened another portal.",
            "(Morass has copied the Mirror World. These must be the Black Mage's memories, planted in Grendel.)"),
    39719: (3004302, 993133000, (993133100, 993133150, 993133200), 993133500,
            "Morass has copied the Radiant Temple now. Clear out the monsters in there. Press up at a mirror to move "
            "through it.",
            "(You watch the past replay: you turned down destiny and refused to kill Tana. Grendel says the world "
            "can only be free once the last Transcendent falls to the Adversary, then vanishes again.)"),
    39720: (3004302, 993133500, None, 993134000,
            "We need another portal to follow Grandpa. Lily, help me with this one. Come on, use it with us.",
            "(You couldn't get Will's book from the copy of Melange. There's nothing to do but press on.)"),
    39721: (3004302, 993134000, (993134100, 993134150, 993134200), 993134500,
            "This is Limina. Take the portal on the right and push on, we're right behind you.",
            "(You defeat every monster that came at you in Limina.)"),
    39722: (3004302, 993134500, None, WORLDS_END,
            "We cleared Limina, but there's no sign of Grandpa. The space here keeps shifting... we must be getting "
            "close to the Black Mage. Let's pick up the trail.",
            "(The Black Mage is gone, and you reach the world's end. Grendel the Really Old is there. Before the "
            "Transcendents, he says, Maple World and Grandis were one world. Then an unfamiliar figure arrives...)"),
}


def stage_at(field):
    for q in range(FIRST, LAST + 1):
        if STAGES[q][1] == field:
            return q
    return 0


def stage_of_fight(field):
    for q in range(FIRST, LAST + 1):
        fights = STAGES[q][2]
        if fights and field in fights:
            return q
    return 0


def offer(q):
    # start script: the stage's NPC asks, the quest starts
    npc, field, fights, end, start_line, end_line = STAGES[q]
    sm.setSpeakerID(npc)
    if sm.hasQuestCompleted(q) or not sm.hasQuest(q) and not sm.sendAskAccept(start_line):
        return
    if not sm.hasQuest(q):
        sm.startQuest(q)
    if sm.hasQuest(q) and q in (FIRST, LAST):
        leave(q)  # no portal for these: the NPC takes you


def finish(q):
    sm.setPlayerAsSpeaker()
    sm.sendNext(STAGES[q][5])
    if sm.hasQuest(q):
        sm.completeQuestNoCheck(q)


def leave(q):
    # the stage's way on: its first fight map (own copy), or straight to the next story map
    fights = STAGES[q][2]
    if fights:
        sm.warpInstanceIn(fights[0], 0)
    else:
        sm.warp(STAGES[q][3], 0)


def portal():
    # a story map's portal: on to the stage started here
    q = stage_at(sm.getFieldID())
    if q == 0:
        return
    if sm.hasQuest(q):
        leave(q)
    elif sm.hasQuestCompleted(q):
        sm.warp(STAGES[q][3], 0)
    else:
        sm.chat("Talk to %s first." % NAMES[STAGES[q][0]])


def fight_next():
    # a fight map is clear: the next one, or the stage's end
    field = sm.getFieldID()
    q = stage_of_fight(field)
    if q == 0 or chr.getInstance() is None:
        return
    fights = STAGES[q][2]
    i = list(fights).index(field)
    if i + 1 < len(fights):
        sm.warp(fights[i + 1], 0)
    else:
        sm.warpInstanceOut(STAGES[q][3], 0)


def fight_portal():
    if stage_of_fight(sm.getFieldID()) == 0:
        return  # the same portal script in another story's map
    if sm.hasMobsInField():
        sm.chat("Defeat the monsters first.")
    else:
        fight_next()


def arrive():
    # a story map's enter script: the stage that ends here
    field = sm.getFieldID()
    for q in range(FIRST, LAST + 1):
        if STAGES[q][3] == field and sm.hasQuest(q):
            finish(q)
            if q == LAST:
                sm.warp(CERNIUM_ARRIVAL, 0)
            return
