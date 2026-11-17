# Balrog altar entrance (1061014), Sleepywood 105100100
MODES = [
    # name, level, battle map; v271 BossParty has only Easy at Lv65
    ("Easy", 65, 105100400),
]

sm.setSpeakerID(1061014)
if not hasattr(sm, "spawnBalrog"):
    sm.sendSayOkay("The temple's seal is still holding. Come back a little later.")
else:
    text = "#e<Boss: Balrog>#n\r\nThe Balrog stirs beneath the temple. Will you face it?\r\n\r\n#b"
    for i in range(len(MODES)):
        text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, MODES[i][0], MODES[i][1])
    text += "#L9#Never mind.#l"
    sel = sm.sendNext(text)
    if 0 <= sel < len(MODES):
        name, lv, toField = MODES[sel]
        if sm.getParty() is None:
            sm.createSoloParty()
        if not sm.isPartyLeader():
            sm.sendSayOkay("Please have your party leader talk to me if you wish to face the Balrog.")
        elif sm.checkParty(None, lv):
            sm.warpInstanceIn(toField, 0, True)
            sm.setInstanceTime(20 * 60, 105100100)
sm.dispose()
