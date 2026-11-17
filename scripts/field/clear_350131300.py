# Heroes of Maple, Act 3: Unfamiliar World (33913) - stage 1/2 cleared (short version of the story). The story's own
# scenes weren't in the server files; each act is a few real fights in its maps.
ACT = 33913
NEXT_MAP = 350132400
NEXT_MOBS = ((8240164, -600, 123), (8240164, -300, 123), (8240164, 0, 123), (8240164, 300, 123), (8240164, 600, 123))
if sm.hasQuest(ACT):
    chr.chatMessage('[Heroes of Maple] Demon soldiers wait in the restricted area.')
    sm.warpInstanceIn(NEXT_MAP, 0)
    field = chr.getField()
    if field.getId() == NEXT_MAP:
        for mob, x, y in NEXT_MOBS:
            field.spawnMob(mob, x, y, False, 0)
