# 273060000 Twilight Perion: Spirit Zone Limbo (see CustomFieldScripts): [Twilight Perion] Manji's Trust (31930) - the
# Ghostwood Stumpy (8620012) appears for a character with the quest.
if sm.hasQuest(31930) and not sm.isComplete(31930) and not sm.hasMobsInField(273060000, 8620012):
    pos = chr.getPosition()
    sm.spawnMob(8620012, pos.getX() + 300, pos.getY(), False)
    sm.chat("The Ghostwood Stumpy appears! Show Manji what you've got.")
