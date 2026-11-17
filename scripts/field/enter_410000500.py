# Cernium Square (410000500), on entering.
# The Cernium story's first quest ([Cernium (Before)] A Traveler from Beyond the Sea, 39801) ends with Edea on the beach
# where Neinheart's ship lands (410000510), which no portal of the town leads to. While it's under way, the town offers
# the trip there (players come here with @go Cernium).
#
# The map holds the town's NPCs for every point of the story, several on the same spot. The live server spawns only
# those of the player's point; here the rest are hidden for this player:
#  - a story NPC shows only while it's needed: it hands in a quest under way, or starts the next one that can be taken
#    (once shown, a story NPC's own record keeps it visible to the client, so it has to be hidden here afterwards);
#  - the town's other people show as one set per chapter: while the Cernium story is under way, after it (Cernium
#    Unified, 39818), and in Burning Cernium (from Burning Sky, 39901);
#  - everything else (the daily Flame Priest, the Merchant, the Storage Keeper, the service NPCs) always stays.

FIRST_QUEST = 39801
BEACH = 410000510
UNIFIED = 39818
BURNING = 39901

# story NPC -> (quests it hands in, quests it starts); each chain quest needs the one before it
STORY = {
    3004403: ([39803], [39804]), # Old Man
    3004404: ([39804], [39805]), # Little Kid
    3004407: ([39806], [39807]), # Little Kid
    3004416: ([39814, 39817], [39815]), # Seren
    3004417: ([39815], [39816]), # Carlisle
    3004427: ([], [39818]), # Carlisle
    3004428: ([39818], []), # Edea
    3004500: ([39901], [39902]), # Edea
    3004504: ([39906], [39907]), # Seren
    3004511: ([39913], [39914]), # Carlisle
    3004512: ([39914], []), # Court Knight
}
# the town's other people, by chapter
DURING = [3004421, 3004423, 3004420, 3004422, 3004425] # Dawn Priest, Court Knights, Magne, Joy, Sean
AFTER = [3004448, 3004452, 3004455, 3004423, 3004420, 3004422, 3004425] # + Syrinne, Thunderhammer
BURNING_SET = [3004520, 3004519, 3004423, 3004420, 3004422] # Dawn Priest, Heite, Court Knights, Magne, Joy
TOWN = set(DURING + AFTER + BURNING_SET)
ALWAYS_HIDDEN = [3004419, 3004521] # Flame Priests on the daily Flame Priest's spot (3004540 gives the dailies)


def done(q):
    return sm.hasQuestCompleted(q)


def under_way(q):
    return sm.hasQuest(q) and not done(q)


def startable(q):
    return not sm.hasQuest(q) and not done(q) and done(q - 1)


def needed(npc):
    ends, starts = STORY[npc]
    return any(under_way(q) for q in ends) or any(startable(q) for q in starts)


if done(BURNING) or sm.hasQuest(BURNING):
    town = BURNING_SET
elif done(UNIFIED):
    town = AFTER
else:
    town = DURING

for npc in sm.getField().getNpcs():
    tid = npc.getTemplateId()
    if tid in ALWAYS_HIDDEN:
        hide = True
    elif tid in STORY:
        hide = not needed(tid)
    elif tid in TOWN:
        hide = tid not in town
    else:
        continue
    if hide:
        # every copy (two Court Knights), a moment after entering, once the client has the map's NPCs
        sm.invokeAfterDelay(500, "hideNpcInField", sm.getFieldID(), npc.getObjectId(), True)

if under_way(FIRST_QUEST):
    sm.setSpeakerID(3004466) # Edea
    if sm.sendAskYesNo("You came here from beyond the sea, didn't you? Edea is waiting for you on the beach where "
                       "you landed.\r\n\r\nShall I take you to #b#m" + str(BEACH) + "##k?"):
        sm.warp(BEACH)
sm.dispose()
