# Hieizan Temple 5th Floor (811000500), first enter (v265 onFirstUserEnter): the shrine spirits and Princess No.
# In v265 the fight is on this floor; PrincessNoInstanceHelper counts the weekly clear when she falls.
sm.spawnNpc(9130110, -519, -389)
sm.spawnNpc(9130111, -430, -569)
sm.spawnNpc(9130112, -7, -682)
sm.spawnNpc(9130113, 540, -390)
sm.spawnNpc(9130114, 441, -571)
sm.showEffectOnPosition("Effect/DebuffObjEff.img/poisonObj/ing", 5010, 441, -571)
if not sm.hasMobsInField():
    sm.spawnMob(9450022, -8, -205, False)
