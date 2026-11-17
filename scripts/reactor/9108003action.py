PRIMROSE_SEED = 4001453

instance = chr.getInstance()
helper = instance.getInstanceHelper() if instance is not None else None
if sm.hasItem(PRIMROSE_SEED, 1) and helper is not None:
    if hasattr(helper, "plantSeed"):
        if helper.plantSeed(chr.getField(), reactor):
            sm.consumeItem(PRIMROSE_SEED, 1)
    elif reactor.getState() == 0:
        reactor.setState(1)
        reactor.broadcastChangeState()
        sm.consumeItem(PRIMROSE_SEED, 1)
        grown = len([r for r in chr.getField().getReactors() if 9108000 <= r.getTemplateId() <= 9108005 and r.getState() == 1])
        if grown > helper.getSeedsPlanted() and helper.getSeedsPlanted() < 6:
            helper.setSeedsPlanted(grown - 1)
            helper.incSeedsPlanted()
sm.dispose()
