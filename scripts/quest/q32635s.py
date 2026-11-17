# Evidence in Ellin Forest (32635, Grand Athenaeum Episode I). The quest asks for 3 Omens, which nothing
# spawned: they appear around the Fairy Queen's Nest when it starts.
if sm.questStartDialogue(parentID):
    field = chr.getField()
    if field.getId() == 302020100:
        for x in (-1700, -1000, -300):
            field.spawnMob(8230047, x, 90, False, 0)
sm.dispose()
