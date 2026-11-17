# [Mushroom Shrine Tales] Unholy Water (58737). Handing in the Dusk Viper Venom gets you the Unholy Water, which
# The Plight of the Pale Specter (58744) asks for.
WATER = 4034857
if sm.questEndDialogue(parentID):
    if not sm.hasItem(WATER) and sm.canHold(WATER):
        sm.giveItem(WATER)
sm.dispose()
