# Moonflower (reactors 9250000-9250005, this script for each) in Primrose Hill (933001000): a Primrose Seed (4000884,
# from the pigs) grows it. Six grown flowers end stage 1: the pigs stop, the stage-2 monsters come back and the Spring
# Bunny appears.
SEED = 4000884
PIGS = [9300900, 9300901]
SPRING_BUNNY = 9300902
MOON_BUNNY = 9300907
FLOWERS = range(9250000, 9250006)

flower = parentID
if field.getProperty("Stage1Cleared"):
    sm.chat("The flowers are already in bloom.")
elif sm.getReactorState(flower) != 0:
    sm.chat("A seed has already been planted here.")
elif not sm.hasItem(SEED):
    sm.chat("You need a Primrose Seed to plant here.")
else:
    sm.consumeItem(SEED)
    sm.changeReactorState(flower, 1)
    grown = len([f for f in FLOWERS if sm.getReactorState(f) == 1])
    field.setProperty("Stage1FlowerData", grown)
    if grown >= 6:
        field.setProperty("Stage1Cleared", True)
        field.clearRespawn()
        field.setNoRespawn(PIGS)
        sm.killMobs()
        sm.spawnMob(SPRING_BUNNY, -198, -189, False)
        sm.spawnMob(MOON_BUNNY, -198, -189, False)
        sm.chatBlue("All six Primrose Seeds have been planted. Protect the Moon Bunny!")
sm.dispose()
