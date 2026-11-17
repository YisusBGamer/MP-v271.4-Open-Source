# Chaos Zakum's altar (280030000). As in GMS, coming in without an Eye of Fire gives one (dropped in the map,
# ZakumInstanceHelper calls him).
item = 4001017
if sm.getChr() is not None and sm.getChr().getInstance() is not None and not field.hasProperty("ZakumSpawned") \
        and not sm.hasItem(item) and sm.canHold(item):
    sm.giveItem(item)
