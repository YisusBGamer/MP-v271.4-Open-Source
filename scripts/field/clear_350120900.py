# Heroes of Maple, Act 2: Abraxas (33912) - stage 1/2 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33912
NEXT_MAP = 350123600
NEXT_MOBS = ((8240173, 1300, -868), (8240173, 1700, -868), (8240173, 2100, -868), (8240173, 2500, -868))
if sm.hasQuest(ACT):
    chr.chatMessage('[Heroes of Maple] Wolf dogs hunt you down on the way to Abraxas.')
    sm.warpInstanceIn(NEXT_MAP, 0)
    field = chr.getField()
    if field.getId() == NEXT_MAP:
        for mob, x, y in NEXT_MOBS:
            field.spawnMob(mob, x, y, False, 0)
