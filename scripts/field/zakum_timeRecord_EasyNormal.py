# Zakum's altar, Easy (280030200) and Normal (280030100). As in GMS, coming in without the item that calls Zakum gives
# one: the Eye of Fire Chunk on Easy, the Eye of Fire on Normal (dropped in the map, ZakumInstanceHelper calls him).
EASY_ALTAR = 280030200
item = 4001796 if sm.getFieldID() == EASY_ALTAR else 4001017
if sm.getChr() is not None and sm.getChr().getInstance() is not None and not field.hasProperty("ZakumSpawned") \
        and not sm.hasItem(item) and sm.canHold(item):
    sm.giveItem(item)
