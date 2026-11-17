from net.swordie.ms.constants import BossConstants

dict = {
    9390630 : [863010400],
    9390631 : [863010300, 863010310, 863010420],
    9390633 : [863010200],
    9390634 : [863010220],
    9390635 : [863010210, 863010230],
    9390638 : [863010320],
    9390640 : [863010410]
}

clearObjects = {
    863010310 : ["open", "clear"],
    863010410 : ["open", "clear"]
}

fieldId = field.getId()
mobId = mob.getTemplateId()

if sm.hasMobsInField(parentID, mobId):
    sm.createFallingCatcher("DropStoneGiantBoss1", 40, 3, 30)
    sm.createFallingCatcher("DropStoneGiantBoss2", 40, 2, 40)
    sm.createFallingCatcher("DropStoneGiantBoss3", 40, 3, 30)
else:
    if not field.hasProperty(BossConstants.GOLLUX_FINISHED_KEY):
        field.setProperty(BossConstants.GOLLUX_FINISHED_KEY, True)
        # named map objects that open once the area is cleared; other areas have none
        for name in clearObjects.get(fieldId, []):
            sm.golluxPortalOpen(name)
sm.showGolluxMiniMap()