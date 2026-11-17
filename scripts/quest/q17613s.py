# [Commerci Republic] The Minister's Son (17613), start - Mayor Berry (9390201), San Commerci outskirts (865010200).
# QuestInfo: Mayor Berry sends you to Leon Daniella; the quest is then turned in after 12 of the cats (9390847) that
# set on him are defeated (Check.img), with Leon's scene (q17613e).

if sm.getFieldID() == 865010200:
    sm.setSpeakerID(9390201) # Mayor Berry
    sm.sendNext("Find #e#bLeon Daniella#k#n in the guest house on the east end of this village.")
    if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
        sm.startQuest(parentID)
    sm.dispose()
else:
    sm.setSpeakerID(9390202) # Leon Daniella
    sm.sendSayOkay("Oi")
    sm.dispose()
