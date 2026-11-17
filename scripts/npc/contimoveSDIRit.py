# Olaf
# 1002101
# Lith Harbor (104000000)
# Warps Explorers to Southperry Port.
# Also sails to Slumbering Dragon Island for Evan's story (Shipshape 22579 onward, through The Past, Onyx Dragons,
# Black Mage 22591).

from net.swordie.ms.constants import JobConstants

SDI_QUESTS = (22580, 22588, 22589, 22590, 22591)

def isAdventurer(adv):
    result = (adv == 0
    or JobConstants.isAdventurerWarrior(adv)
    or JobConstants.isAdventurerMage(adv)
    or JobConstants.isAdventurerArcher(adv)
    or JobConstants.isAdventurerThief(adv)
    or JobConstants.isAdventurerPirate(adv))
    return result

def wantsSDI():
    return sm.hasQuestCompleted(22579) or any(sm.hasQuest(q) for q in SDI_QUESTS)

# Check character's job category
adv = chr.getJob()

if wantsSDI():
    if sm.sendAskYesNo("The ship is ready. Shall we set sail for #bSlumbering Dragon Island#k?"):
        sm.warp(914100000, 0)
elif isAdventurer(adv):
    response = sm.sendAskYesNo("Would you like to go back to #bMaple Island#k?")
    if response:
        sm.warp(2000100) # Move to Southperry Port
else:
    sm.sendSayOkay("Sorry, only Explorers can go to #bMaple Island#k.")
