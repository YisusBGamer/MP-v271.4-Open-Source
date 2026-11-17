# 912090000 Way to the Beach (see CustomFieldScripts): where Tess ends up after the Six Path Crossway.
# Following the Pirate (32253) / Following the Bowman (32263) wait for their own record to say "1" (having followed
# him here). Pirate versus Rabbit (32254) / Bowman Versus Rabbit (32264): the masked rabbit is back for a character
# still on the quest.
for follow in (32253, 32263):
    if sm.hasQuest(follow) and sm.getQRValue(follow) != "1":
        sm.setQRValue(follow, "1")
        sm.chatScript("Tess is confronting... a rabbit? Talk to Tess.")
for fight in (32254, 32264):
    if sm.hasQuest(fight) and not sm.isComplete(fight) and not sm.hasMobsInField(912090000, 9300802):
        pos = chr.getPosition()
        sm.spawnMob(9300802, pos.getX() + 250, pos.getY(), False)
        break
sm.dispose()
