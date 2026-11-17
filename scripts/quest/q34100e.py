# [Vanishing Journey] Amnesiac Temple Keeper Kao
from net.swordie.ms.world.field.fieldeffect import GreyFieldType
KAO = 3003131
KAO_ILLUSTRATION = 3003113


if not sm.hasQuestCompleted(1466):
    sm.setSpeakerID(KAO)
    sm.sendNext("..What should we do to assuage their anger?\r\n"
                "#b(Complete the A Greater Power quest and obtain an Arcane Symbol.)")
else:
    sm.removeEscapeButton()
    sm.lockInGameUI(True, False)
    sm.setFieldColour(GreyFieldType.Field, 100, 100, 100, 2000)
    # official lines (Quest.wz Say.img)
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(You asked him what happened.)#k")
    sm.setSpeakerID(KAO_ILLUSTRATION)
    sm.setBoxChat()
    sm.sendNext("There's something I have to warn you about. Have you seen the large lake at the outskirts of town? "
                "The townspeople call it the Lake of Oblivion. If you fall in, you'll forget everything. Every last memory.")

    sm.showOffFieldEffect("Map/Effect2.img/ArcaneRiver1/tree1")
    sm.sendNext("Maybe it's due to living so close to the lake, but the people around here have been gradually losing their memory every day. "
                "That why this Tree of Memory was created. Precious memories are hung on this tree. "
                "The townspeople can come by to look at them every day until the memories wither and vanish...")

    sm.sendNext("When I heard about the tree from the townspeople, my heart started racing. "
                "I thought there might be memories related to me there, so I came to investigate. But...")

    sm.showOffFieldEffect("Map/Effect2.img/ArcaneRiver1/tree2")
    sm.sendNext("I still don't know why. The moment I touched the tree...")

    sm.showOffFieldEffect("Map/Effect2.img/ArcaneRiver1/tree3")
    sm.sendNext("All those memories... The precious memories of the townspeople... They all scattered. Everyone was heartbroken. "
                "Their daily routines came to a halt. Even the boat that sails across Lake of Oblivion stopped running.")

    sm.setFieldColour(GreyFieldType.Field, 255, 255, 255, 1000)
    sm.lockInGameUI(False)
    sm.completeQuest(parentID)