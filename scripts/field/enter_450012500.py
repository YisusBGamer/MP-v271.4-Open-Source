# 450012500 Limina: Giant's Heart. [Limina] The Final Foe (35849, v265; 35813 before): the Weakened Black Mage
# (8645050), in the copy of the map Cygnus sends you to (quest/q35849s). The Black Mage boss run goes on through the
# in_boss portal as before.
if (sm.hasQuest(35849) or sm.hasQuest(35813)) and chr.getInstance() is not None and not sm.hasMobsInField(450012500, 8645050):
    pos = chr.getPosition()
    sm.spawnMob(8645050, pos.getX() + 300, pos.getY(), False)
