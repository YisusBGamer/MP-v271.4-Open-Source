# Selazar (3007044), Empress Road Operations Center (410007520): the Epic Dungeon window, Nightmare Paradise first.
# Lv. 280, after the Angler Company medal (27115) and The Battle's Aftermath (38653): Selazar's ballad ([Epic Dungeon]
# Nightmare Paradise, 27140) opens Nightmare Paradise; story mode until Ember of Life (27143), then the weekly run
# (7 stages, resets Thursday). The medal (Wakebringer, 27144) once the story is told. The run itself is
# NightmareParadiseInstanceHelper.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR_NP)
if chr.getLevel() < ED.NP_LEVEL:
    sm.sendSayOkay("My new ballad is about #bNightmare Paradise#k, and it's for those of #bLv. 280#k and above. "
                   "Come back when you're stronger.")
elif not sm.hasQuestCompleted(ED.NP_INTRO_QUEST):
    if not sm.hasQuestCompleted(ED.ANGLER_MEDAL_QUEST) or not sm.hasQuestCompleted(ED.NP_INTRO_PREREQ):
        sm.sendSayOkay("Every song in its order. Hear the whole tale of the #bAngler Company#k first, and see "
                       "Arteria's battle through to its end. Then come and listen to my new ballad.")
    else:
        sm.sendNext("Ah, you came. My new ballad is about #bBaekyeon#k, one of the Four Symbols, imprisoned in "
                    "Kunlun...")
        sm.sendNext("Shangri-La's elders betrayed it, and a nightmare spread through the shrine and the temple. "
                    "The Four Symbols, Shensung, Ara and Zigang among them, were contaminated one by one.")
        sm.sendSayOkay("Would you see how the ballad ends with your own eyes? Talk to me again, and I'll show you "
                       "the way into #bNightmare Paradise#k.")
        if not sm.hasQuest(ED.NP_INTRO_QUEST):
            sm.startQuestNoCheck(ED.NP_INTRO_QUEST)
        sm.completeQuestNoRewards(ED.NP_INTRO_QUEST)
elif sm.hasQuestCompleted(ED.NP_STORY_QUEST) and not sm.hasQuestCompleted(ED.NP_MEDAL_QUEST):
    if not sm.canHold(ED.NP_MEDAL):
        sm.sendSayOkay("I've made something for you, but your Equip inventory is full. Make some room first.")
    else:
        sm.sendNext("The nightmare is over, and you were there to wake from it. Here, a little something to "
                    "remember Paradise by.")
        if not sm.hasQuest(ED.NP_MEDAL_QUEST):
            sm.startQuestNoCheck(ED.NP_MEDAL_QUEST)
        sm.completeQuestNoRewards(ED.NP_MEDAL_QUEST)
        sm.giveItem(ED.NP_MEDAL)
        ED.openWindow(chr, ED.REGION_NIGHTMARE)
else:
    ED.openWindow(chr, ED.REGION_NIGHTMARE)
sm.dispose()
