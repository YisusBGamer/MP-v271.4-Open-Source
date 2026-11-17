# 31909 - [Twilight Perion] Unsettling Future 6
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Dances with Balrog (1022000)

sm.setSpeakerID(1022000)  # Dances with Balrog
if sm.sendNext("It's good to see you again, friend. Before you tell me anything, I'd like to see if our nightmares line up with your experiences. I'm going to ask you some questions. \r\n#L0# #bLet's do it.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("What was the massive structure being built in the future Perion?\r\n#L0##bA big Pig Statue.#l\r\n#L1#A giant Siege Weapon.#l\r\n#L2#A giant Black Mage Statue.#l\r\n#k") != 2:
    sm.sendSayOkay("That is not our nightmare.")
    sm.dispose()
if sm.sendSay("What happened to the Warrior's Shrine?\r\n#L0##bIt was destroyed.#l\r\n#L1#It was expanded.#l\r\n#L2#The Black Mage's minions reside there.#l\r\n#k") != 0:
    sm.sendSayOkay("That is not our nightmare.")
    sm.dispose()
if sm.sendSay("Is my descendant a boy or a girl?\r\n#L0##bA boy.#l\r\n#L1#A girl.#l\r\n#k") != 0:
    sm.sendSayOkay("That is not our nightmare.")
    sm.dispose()
if sm.sendSay("Which group is occupying Perion?\r\n#L0# #bThe Resistance#l\r\n#L1# The Black Wings#l\r\n#L2# The Commanders#l\r\n#k") != 0:
    sm.sendSayOkay("That is not our nightmare.")
    sm.dispose()
sm.sendSay("You have seen our nightmares. This is what you saw when you went through the Gate to the Future?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you. This knowledge is dire, but will help the citizens ease their minds. I would appreciate your continued interest in the matter. The dreams weigh on our hearts...")
