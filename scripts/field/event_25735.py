# 940000000-940000019 Pantheon Vacant Lot - Evil Auras! (25735, Kaiser): the Nefarious Priests behind the aura.
# Beating them (field/clear_940000000.py) leads to where they escape (event_25735_end0). The exit leads back to
# Pantheon; leaving early means forfeiting the quest, as it says.
if sm.hasQuest(25735) and sm.getQRValue(25802) != "1" and not sm.hasMobsInField():
    for mob, x in ((9300536, 250), (9300537, 450), (9300538, 650)):
        sm.spawnMob(mob, x, 29, False)
    sm.chatScript("Nefarious Priests! They're the source of the evil aura!")
sm.dispose()
