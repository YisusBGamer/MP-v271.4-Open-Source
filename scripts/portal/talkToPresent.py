# Gate to the present
# Explorers on The Gate of the Present Opens (32308) / The Black Mage (32309) pass through to the Black Mage's Temple
# (927020200 -> 927020210 -> 927020220), in their own copy.
# Shade on A Foreigner At Home (38066) to The Broken Seal (38068) passes through to the Black Mage's Antechamber
# (927030060 -> 927030070). Not an instance: completing 38068 moves you back to the Three Doors (Act transferField).


def explorer_story():
    return sm.hasQuest(32308) or sm.hasQuest(32309) \
        or (sm.hasQuestCompleted(32308) and not sm.hasQuestCompleted(32309))


def shade_story():
    return sm.hasQuest(38066) or sm.hasQuest(38067) or sm.hasQuest(38068) \
        or (sm.hasQuestCompleted(38066) and not sm.hasQuestCompleted(38068))


def pass_through():
    if explorer_story():
        sm.warpInstanceIn(927020200, 0)
    elif shade_story():
        sm.warp(927030060, 0)
    else:
        sm.chatScript("The Gate of the Present is still closed.")


if sm.hasQuestCompleted(1478): #V-matrix quest
    sm.setSpeakerID(1520021)
    selection = sm.sendNext("At the center of the Temple of Time stands an enormous door, the Gate of the Present.\r\n #b#L0#Step through into the Arcane River.#l \r\n #L1#Pass through the Gate of the Present. #l")
    if selection == 0:
        sm.warp(450001003)
    elif selection == 1:
        pass_through()
elif explorer_story() or shade_story():
    pass_through()
