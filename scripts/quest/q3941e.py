# Stealing Queen's Order of Silk
# 3941
# Karcasa (2101013)

from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat

karcasa = 2101013
tigun = 2101004
silk = 4031571
isTigun = sm.getnOptionByCTS(CharacterTemporaryStat.Morph) == 6

sm.setSpeakerID(karcasa)
if isTigun:
    sm.sendNext("Okay, here it is. Please handle it with care. This silk is very hard to get, and if any part of this "
    "fabric is ripped, the queen will put you in jail, #p" + repr(tigun) + "#. \r\n"
    "#fUI/UIWindow2.img/QuestIcon/4/0# \r\n"
    "1 " + sm.formatInlineItem(silk))
    if sm.canHold(silk):
        sm.completeQuest(parentID)  # gives the silk (Act.img); the script used to give a second one
    else:
        sm.sendSayOkay("Er, #p" + repr(tigun) + "#, it looks like you can't hold the silk. Talk to me again after you make some space.")
    
else:
    sm.sendSayOkay("Okay, here it is. Please handle this with... huh? #p" + repr(tigun) + "#, where did you go?")