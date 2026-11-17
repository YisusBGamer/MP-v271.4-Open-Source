# Honnou-ji Temple (807050503-807050505): The Demon's Lair 3 (Hayato 57160, Kanna 57456), Takigawa
# Kazumasu, one of Oda's Four Heavenly Kings. Beaten, he's turned in to (field/die_kazumasu).
if ((sm.hasQuest(57160) or sm.hasQuest(57456)) and not sm.hasMobsInField()
        and chr.getField().getLifeByTemplateId(9130078) is None):
    pos = chr.getPosition()
    sm.spawnMob(9421562, pos.getX() + 300, pos.getY(), False)
    sm.chat("Defeat Takigawa Kazumasu, one of Oda's Four Heavenly Kings.")
