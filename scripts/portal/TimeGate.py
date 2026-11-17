# 320000000 Zero's Temple (and 327090300 Zero Shrine), out00: the Temporal Gate. Maple World, and the Mirror World towns
# Zero's story has reached (Classic Zeros; every town for the others).
from net.swordie.ms.client.character import PlayStyleModule

TOWNS = (
    (321100000, "Leafre", 40901), (322000000, "Ariant", 40910), (323000000, "Henesys", 40920),
    (324000000, "Happy Valley", 40930), (325000000, "Edelstein", 40940), (326000000, "Magatia", 40950),
    (327000000, "Ludibrium", 40960), (328000000, "Dark Temple of Time", 40970), (329000000, "Kritias", 40974),
)
classic = PlayStyleModule.isClassic(chr)
options = [100000000]
text = "Where will you go?\r\n#L0##bMaple World (Henesys)#k#l"
for town, name, quest in TOWNS:
    if not classic or sm.hasQuest(quest) or sm.hasQuestCompleted(quest):
        options.append(town)
        text += "\r\n#L%d##bMirror World: %s#k#l" % (len(options) - 1, name)
sel = sm.sendNext(text)
if 0 <= sel < len(options):
    sm.warpInstanceOut(options[sel], 0)
sm.dispose()
