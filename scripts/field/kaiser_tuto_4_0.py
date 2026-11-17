# 940001110 Heliseum Outskirts - Kaiser's tutorial, scene 4: the first battle as Kaiser
# (clear_940001110 continues once they're defeated)
if sm.getFieldID() == 940001110 and not sm.hasMobsInField():
    for x in (-2100, -1800, -1500, -1200, -900, -600, -400, -200):
        sm.spawnMob(9307011, x, 178, False)
    sm.chatScript("Use the Kaiser's power and drive back the Black Mage's creatures!")
