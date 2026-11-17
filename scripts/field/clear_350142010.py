# Heroes of Maple, Final Act: The World Tree (33914) - stage 1/2 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33914
NEXT_MAP = 350144000
NEXT_MOBS = ((8240179, -600, 16), (8240179, -150, 16), (8240179, 300, 16), (8240179, 750, 16), (8240179, 1200, 16), (8240179, 1650, 16))
if sm.hasQuest(ACT):
    chr.chatMessage('[Heroes of Maple] Imps swarm the World Tree Summit.')
    sm.warpInstanceIn(NEXT_MAP, 0)
    field = chr.getField()
    if field.getId() == NEXT_MAP:
        for mob, x, y in NEXT_MOBS:
            field.spawnMob(mob, x, y, False, 0)
