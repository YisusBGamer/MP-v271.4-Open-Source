# Monster Park gate 3 (951000000 portal in02): opens the Monster Park window for this gate (MONSTER_PARK_RESULT);
# its Enter button comes back on 703 (MonsterParkHandler). Rules in MonsterPark.java (solo, level, story quest and
# the GMS daily clears). Until the window builder is live, a dialog does the same.
from net.swordie.ms.client.character import MonsterPark
from net.swordie.ms.connection.packet import ContentPacket

GATE = 3

if hasattr(ContentPacket, "monsterParkWindow"):
    chr.write(ContentPacket.monsterParkWindow(chr, GATE))
else:
    sm.setSpeakerID(9071004)
    dungeons = MonsterPark.getDungeons(GATE)
    text = "#eToday is #b" + sm.getDay() + "#k.#n\r\n\r\n"
    for i in range(len(dungeons)):
        d = dungeons[i]
        colour = "#b" if MonsterPark.getEntryBlock(chr, d) is None else "#d"
        text += "#L" + str(i) + "#" + colour + d.label() + "#k#l\r\n"
    selection = sm.sendNext(text)
    d = dungeons[selection]
    block = MonsterPark.getEntryBlock(chr, d)
    if block is not None:
        sm.sendSayOkay(block)
    elif sm.sendAskYesNo("Selected dungeon: #b" + d.label() + "#k\r\n\r\nEnter the dungeon?"):
        MonsterPark.enter(chr, d)
sm.dispose()
