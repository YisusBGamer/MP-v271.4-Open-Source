# Heroes of Maple, Act 1: Slumbering Dragon Island (33910) - stage 1/2 cleared (short version of the story). The story's
# own scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33910
NEXT_MAP = 350112100
NEXT_MOBS = ((8240134, -700, 351), (8240134, -400, 351), (8240134, -100, 351), (8240134, 200, 351), (8240134, 500, 351))
if sm.hasQuest(ACT):
    chr.chatMessage("[Heroes of Maple] Demon soldiers guard the island's interior.")
    sm.warpInstanceIn(NEXT_MAP, 0)
    field = chr.getField()
    if field.getId() == NEXT_MAP:
        for mob, x, y in NEXT_MOBS:
            field.spawnMob(mob, x, y, False, 0)
