# Selazar (3006760), Odium Residential District (410007000): the Epic Dungeon window, Angler Company first.
# Lv. 270, after 38319 and Sun God Mitra (26216): Selazar's song ([Epic Dungeon] Angler Company, 27111) opens the
# Angler Company; story mode until Outside the Building (27114), then the weekly run (8 stages, resets Thursday).
# The medal (Delver of the Deep, 27115) once the story is told. The run itself is AnglerCompanyInstanceHelper.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR_ODIUM)
if chr.getLevel() < ED.ANGLER_LEVEL:
    sm.sendSayOkay("My new song is about the #bAngler Company#k, and it's for those of #bLv. 270#k and above. "
                   "Come back when you're stronger.")
elif not sm.hasQuestCompleted(ED.ANGLER_INTRO_QUEST):
    if not sm.hasQuestCompleted(38319) or not sm.hasQuestCompleted(ED.HM_STORY_QUEST):
        sm.sendSayOkay("Odium still has troubles of its own, and the tale of #bHigh Mountain#k comes first. "
                       "Hear that one out, then come and listen to my new song.")
    else:
        sm.sendNext("Ah, a familiar face. I've written a new song. It's about #bSenya Angler#k, and the day she "
                    "joined the #bAngler Company#k...")
        sm.sendNext("Deep under the sea, the company keeps its secrets. Robots guard every floor, and somewhere at "
                    "the bottom, a machine called #bCosim#k waits for someone to come.")
        sm.sendSayOkay("Would you see how the song ends with your own eyes? Talk to me again, and I'll show you "
                       "the way into the #bAngler Company#k.")
        if not sm.hasQuest(ED.ANGLER_INTRO_QUEST):
            sm.startQuestNoCheck(ED.ANGLER_INTRO_QUEST)
        sm.completeQuestNoRewards(ED.ANGLER_INTRO_QUEST)
elif sm.hasQuestCompleted(ED.ANGLER_STORY_QUEST) and not sm.hasQuestCompleted(ED.ANGLER_MEDAL_QUEST):
    if not sm.canHold(ED.ANGLER_MEDAL):
        sm.sendSayOkay("I've made something for you, but your Equip inventory is full. Make some room first.")
    else:
        sm.sendNext("Cosim's tale is told, and you were there to the very end. Here, a little something to "
                    "remember the deep by.")
        if not sm.hasQuest(ED.ANGLER_MEDAL_QUEST):
            sm.startQuestNoCheck(ED.ANGLER_MEDAL_QUEST)
        sm.completeQuestNoRewards(ED.ANGLER_MEDAL_QUEST)
        sm.giveItem(ED.ANGLER_MEDAL)
        ED.openWindow(chr, ED.REGION_ANGLER)
else:
    ED.openWindow(chr, ED.REGION_ANGLER)
sm.dispose()
