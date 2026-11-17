# v265 Mihile story (36601-36630, Lv 1-30): shared by its quest, field, portal and NPC scripts (execfile).
# Quest.wz has no Act/Say data for these quests: the rewards below are QuestInfo/reward. Dialogue up to The Keepsake
# (36620) follows the official v271 client (plans/mihile-v271-transcript.md); 36621-36630 still follow the quest log.

# box-chat speakers: illustrated (Npc.wz illustration2) or, without one, their sprite
MIHILE = 1541258
ATAN = 1541215
NEINHEART = 1541211
CYGNUS = 1541212
GIRL = 1541272  # "???" with Cygnus's face, until Neinheart names her
VOICE = 1541220  # "???" silhouette
CHROMILE = 1541222
SHINSOO = 1541218
KIDAN = 1541216
ELDER = 1541217
STRAY_DOG_SAY = 1541214
SPARROW_SAY = 1541213

# NPC visibility record (Npc.wz conditionN: key "h1" shows, "h0" hides)
STORY_QR = 36600

PROLOGUE = 993201000
OPENING = 993201001
LOST_PROPERTY_FIELD = 993201002
CLEARING_SCENE = 993201003
BEDROOM_SCENE = 993201005
HOUSE_SCENE = 993201006
FIELD_SCENE = 993201007
GUESTS_SCENE = 993201009
WILDLAND_SCENE = 993201010
ARRIVAL_SCENE = 993201011
GARDEN_SCENE = 993201064
ATANS_HOUSE_SCENE = 993201066
LITTLE_ROOM = 993201056
REMOTE_HOUSE = 993201057
FRONT_YARD = 993201058
FOREST_FIELD = 993201059
FOREST_PLAIN = 993201060
FOREST_CLEARING = 993201061
WILDLAND = 993201062
DRILL_HALL = 993201067
CROSSROADS = 993201068
KNIGHTS_CHAMBER = 993201069
ATTACKED_EREVE = 993201070
EREVES_END = 993201071
DUEL = 993201019
SHINSOO_REFUGE = 993201063
EREVE = 130000000
GUSTY_PEAK = 102020500

SWORD = 1302077  # Beginner Warrior's Sword
SPROUT_POTION = 2000059
FRUIT = 4036875  # Wild Pig's Stolen Fruit
EGG = 4036876  # White Sparrow Egg
TOKEN = 4036872  # Knight's Token
KEEPSAKE = 4036877  # Empress Aria's Keepsake
MEDAL = 1142399  # Newborn Light
SOUL_SHIELD = 1098000
STRAY_DOG = 3645304

REWARDS = {
    36603: (1300, ()),
    36604: (1430, ((SPROUT_POTION, 20),)),
    36605: (1690, ((SPROUT_POTION, 20),)),
    36606: (1820, ((SPROUT_POTION, 20),)),
    36607: (2080, ()),
    36608: (2470, ()),
    36609: (4043, ()),
    36610: (4851, ()),
    36611: (5660, ()),
    36612: (6064, ()),
    36613: (6873, ()),
    36614: (7277, ()),
    36615: (7681, ()),
    36616: (2688, ()),
    36617: (3225, ()),
    36618: (3763, ()),
    36619: (4300, ()),
    36620: (4838, ()),
    36621: (5106, ()),
    36623: (3337, ()),
    36624: (4004, ()),
    36625: (4338, ()),
    36627: (5339, ()),
    36628: (5673, ()),
    36629: (6007, ()),
    36630: (6340, ()),
}

# Effect/Direction29.img/mikhail_2022/illust: which picture each index holds is not in the XML export (1x1
# placeholders); the order of first appearance in the story is assumed
ILLUST = {"teardrop": 0, "knight": 1, "photo": 2, "hand": 3, "family": 4}


def say(npc, text, face=0):
    # v271: every speaker, Mihile too, stands on the right of the box
    sm.setSpeakerType(3)
    sm.setParam(37)
    sm.setColor(1)
    sm.setInnerOverrideSpeakerTemplateID(npc)
    return sm.sendNext("#face%d#%s" % (face, text))


def me(text, face=0):
    return say(MIHILE, text, face)


def ask(npc, text, face=0):
    sm.setSpeakerType(4)
    sm.setParam(36)
    sm.setColor(1)
    sm.setSpeakerID(npc)
    sm.setInnerOverrideSpeakerTemplateID(npc)
    return sm.sendAskAccept("#face%d#%s" % (face, text))


def plain(npc, text):
    # speakers without an illustration
    sm.setSpeakerType(4)
    sm.resetParam()
    sm.removeEscapeButton()
    sm.setSpeakerID(npc)
    return sm.sendNext(text)


def caption(text):
    sm.progressMessageFont(3, 20, 0, 0, text)


def mono(text, end=0):
    # centered captions over an illustration, one line per call; end=1 closes the page
    sm.sayMonologue(text, end)


def illust(name, fade=500):
    sm.onLayer(fade, "mihileIllust", 0, -80, -1, "Effect/Direction29.img/mikhail_2022/illust/%d/0" % ILLUST[name],
               4, True, -1, False)


def illust_off(fade=500):
    sm.offLayer(fade, "mihileIllust", False)


def show(*keys):
    for k in keys:
        sm.setQRValue(STORY_QR, k, "h1")


def hide(*keys):
    for k in keys:
        sm.setQRValue(STORY_QR, k, "h0")


def begin(q):
    if not sm.hasQuest(q) and not sm.hasQuestCompleted(q):
        sm.startQuestNoCheck(q)


def finish(q, take=()):
    # completes q once (start too if needed), takes its items and gives its QuestInfo rewards
    if sm.hasQuestCompleted(q):
        return False
    begin(q)
    for item, count in take:
        if sm.hasItem(item, count):
            sm.consumeItem(item, count)
    sm.completeQuestNoCheck(q)
    exp, items = REWARDS.get(q, (0, ()))
    for item, count in items:
        sm.giveItem(item, count)
    if exp:
        sm.giveExp(exp)
    return True


def scene_start():
    sm.lockInGameUI(True, False)
    sm.removeEscapeButton()


def scene_end():
    sm.lockInGameUI(False, True)


def black(time=500):
    sm.blind(1, 255, 0, time)
    sm.sendDelay(time)


def unblack(time=500):
    sm.blind(0, 0, 0, time)
    sm.sendDelay(time)


def knighthood():
    # Preparation for Knighthood (20036) ends: Lv 10, Mihile (5100), Soul Shield, Newborn Light, Sprout Potion x50
    # (same results as the tutorial skip in map_913070000)
    say(ATAN, "Listen carefully, Mihile. As I've told you before, a knight must always live by the #bThree Sacred Rules#k.")
    say(ATAN, "First, #brecognize the truth and stand by it#k.")
    say(ATAN, "Second, #brespect and defend those weaker than yourself#k.\r\nUse your strength only to stop aggression -- "
              "preferably before it starts. Be a knight, not a bully.")
    say(ATAN, "And finally, perhaps most importantly...")
    me("'#bTake special care to protect your loved ones above all#k,' right?")
    say(ATAN, "That's right. You remembered.")
    me("My loved ones...")
    me("I'm ready, Father.")
    say(ATAN, "Good. Just a moment.")
    sm.completeQuestNoCheck(20036)
    if chr.getLevel() < 10:
        sm.addLevel(10 - chr.getLevel())
    sm.setJob(5100)  # gives the job's SP
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    if not sm.isEquipped(SOUL_SHIELD):
        sm.giveNewSecondary(SOUL_SHIELD)
    sm.giveItem(MEDAL)
    sm.giveItem(SPROUT_POTION, 50)
    sm.startQuestNoCheck(29976)
    sm.completeQuestNoRewards(29976)
    me("One last check before training starts...\r\n\r\n#b(* Use the default hotkey (S) or Secondary key binding (C) to "
       "open the character window and distribute attribute points (AP).)#k")
    me("I should review my skills as well.\r\n\r\n#b(* Use the default key binding (K) to open the skill window and "
       "distribute skill points (SP). Be sure to assign any frequently used skills to your skill hotkeys.)#k")
