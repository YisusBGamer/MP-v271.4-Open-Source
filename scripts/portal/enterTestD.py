# Mirror World towns, out00: the dungeon portal (Zero). Lists the town's dungeons (unlocked by Zero's story
# chapters for Classic Zeros, all of them otherwise) and the story places the current quests need, and sends you into
# your own copy of the one you pick. Generated (zgen/gen_dungeon.py).
from net.swordie.ms.client.character import PlayStyleModule

DUNGEONS = {321100000:
    [('mirrorD_321_0_', 'Western Minar Forest', '100-103', 321110000, 40901, 0),
    ('mirrorD_321_1_', 'Eastern Minar Forest', '103-105', 321120000, 40901, 0),
    ('mirrorD_321_2_', 'Twilight Forest', '105-107', 321130000, 40901, 0),
    ('mirrorD_321_3_', 'Wyvern Gorge', '107-109', 321140000, 40901, 0)],
    322000000:
    [('mirrorD_322_0_', 'Outside the West Entrance of Ariant', '110-113', 322010000, 40910, 0),
    ('mirrorD_322_1_', 'Outside East Entrance of Ariant', '113-115', 322020000, 40910, 0),
    ('mirrorD_322_2_', 'Outside North Entrance of Ariant', '115-117', 322030000, 40910, 0),
    ('mirrorD_322_3_', 'Pyramid', '117-119', 322040000, 40910, 0)],
    323000000:
    [('mirrorD_323_0_', 'Small Mushroom Park', '120-123', 323010000, 40920, 0),
    ('mirrorD_323_1_', 'Golem Territory', '123-125', 323020000, 40920, 0),
    ('mirrorD_323_2_', 'Golden Coast', '125-127', 323030000, 40920, 0),
    ('mirrorD_323_3_', 'Under the Sleeping Forest', '127-129', 323040000, 40920, 0)],
    324000000:
    [('mirrorD_324_0_', 'Master Training Center', '130-131', 324010000, 40930, 0),
    ('mirrorD_324_1_', 'Sky Forest', '131-133', 324020000, 40930, 0),
    ('mirrorD_324_2_', 'Bear Territory.', '133-135', 324030000, 40930, 0),
    ('mirrorD_324_3_', 'Herb Garden', '135-137', 324040000, 40930, 0),
    ('mirrorD_324_4_', 'Pirate Hideout', '137-139', 324050000, 40930, 0)],
    325000000:
    [('mirrorD_325_0_', 'Streetlight Street', '140-143', 325010000, 40940, 0),
    ('mirrorD_325_1_', 'Perilous Rocky Mountain', '143-145', 325020000, 40940, 0),
    ('mirrorD_325_2_', 'Rue Mine Depths', '145-147', 325030000, 40940, 0),
    ('mirrorD_325_3_', 'Mine Security Area', '147-149', 325040000, 40940, 0)],
    326000000:
    [('mirrorD_326_0_', 'Zenumist Laboratory A', '150-153', 326010000, 40950, 0),
    ('mirrorD_326_1_', 'Alcadno Laboratory A', '153-155', 326020000, 40950, 0),
    ('mirrorD_326_2_', 'Zenumist Laboratory B', '155-157', 326030000, 40950, 0),
    ('mirrorD_326_3_', 'Alcadno Laboratory B', '157-159', 326040000, 40950, 0)],
    327000000:
    [('mirrorD_327_0_', 'Toy Factory', '160-163', 327010000, 40960, 0),
    ('mirrorD_327_1_', 'Clocktower Interior', '163-165', 327020000, 40960, 0),
    ('mirrorD_327_2_', 'Clocktower Lower Floor', '165-167', 327030000, 40960, 0),
    ('mirrorD_327_3_', 'Clocktower Bottom Floor', '167-169', 327040000, 40960, 0)],
    328000000:
    [('mirrorD_328_0_', 'Memory Lane', '170-172', 328010000, 40970, 0),
    ('mirrorD_328_1_', 'Road of Regrets', '172-176', 328020000, 40970, 0),
    ('mirrorD_328_2_', 'Road to Oblivion', '176-180', 328030000, 40970, 0)],
    329000000:
    [('mirrorD_329_0_', 'Outside Kritias (Hard)', '140-250', 329010000, None, 4033923),
    ('mirrorD_329_1_', 'Outside Kritias (Easy)', '100-140', 329020000, None, 4033923)]}
STORY = {321100000: [(321190200, "Afrien's Cave", [40053]),
    (321190300, 'Glowcaves', [40055, 40056, 40058, 40059, 40060, 40061])],
    322000000: [(322090110, 'South Desert', [40104])],
    323000000: [(323090100, 'Henesys Fork', [40202, 40203, 40204]),
    (323090320, "Larhen's House", [40208]),
    (323090200, 'Northern Forest Path', [40207, 40209, 40210])],
    324000000: [(324090210, 'Misty Forest', [40307, 40308])],
    325000000: [(325090030, 'Streetlight Row', [40401]),
    (325090310, 'Rocky Road', [40407])]}
STARTABLE = {40055: 40054, 40208: 40207, 40209: 40208}


def unlocked(quest):
    return quest is None or not PlayStyleModule.isClassic(chr) or sm.hasQuest(quest) or sm.hasQuestCompleted(quest)


def story_shown(quests):
    for q in quests:
        if sm.hasQuest(q):
            return True
        prev = STARTABLE.get(q)
        if prev is not None and sm.hasQuestCompleted(prev) and not sm.hasQuestCompleted(q):
            return True
    return False


town = sm.getFieldID()
options = []
text = "#e#bDungeon Portal#k#n\r\nWhere will you go?"
for name, label, level, first, quest, item in DUNGEONS.get(town, []):
    if unlocked(quest):
        options.append((first, item))
        text += "\r\n#L%d##b%s#k (Lv. %s)#l" % (len(options) - 1, label, level)
for m, label, quests in STORY.get(town, []):
    if story_shown(quests):
        options.append((m, 0))
        text += "\r\n#L%d##r[Story] %s#k#l" % (len(options) - 1, label)
if not options:
    sm.sendSayOkay("The dungeon portal is quiet. It will open as Zero's story continues.")
else:
    sel = sm.sendNext(text)
    if 0 <= sel < len(options):
        target, item = options[sel]
        if item and not sm.hasItem(item):
            sm.sendSayOkay("You need a #b#t%d##k to enter." % item)
        else:
            if item:
                sm.consumeItem(item, 1)
            sm.warpInstanceIn(target, 0)
sm.dispose()
