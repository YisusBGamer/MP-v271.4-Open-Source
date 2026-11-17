# Portal script pt_foxout.
# Fox Mountain paths (Mushroom Shrine Tales): Fox's Bluff, the Guardian's Cave, Kurama's mind (Fury of the Nine-Tailed
# Fox, 58768) or back to the Mushroom Shrine.
options = []
if sm.hasQuest(58768) and sm.getQRValue(58768, "kuraKill") != "1":
    options.append(("kurama", "Enter Kurama's mind"))
if sm.getFieldID() != 800023015:
    options.append(("bluff", "Fox's Bluff"))
if sm.getFieldID() != 800023018:
    options.append(("cave", "Guardian's Cave"))
options.append(("shrine", "Mushroom Shrine"))
text = "Where do you want to go?\r\n\r\n#b"
for i in range(len(options)):
    text += "#L{}#{}#l\r\n".format(i, options[i][1])
sel = sm.sendNext(text)
if 0 <= sel < len(options):
    where = options[sel][0]
    if where == "kurama":
        sm.warpInstanceIn(800023020, 1)
        field = chr.getField()
        if field.getId() == 800023020:
            field.spawnMob(9400045, 300, 92, False, 0)
    elif where == "bluff":
        sm.warp(800023015, 5)
    elif where == "cave":
        sm.warp(800023018, 2)
    else:
        sm.warp(800000000, 0)
sm.dispose()
