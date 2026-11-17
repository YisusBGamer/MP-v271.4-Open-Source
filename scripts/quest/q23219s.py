# 23219 - True Awakening
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mastema (2151009)

sm.setSpeakerID(2151009)  # Mastema
if sm.sendNext("How your training going, #h0#?\r\n#L0##b#p2151009#. I was preparing to speak with you.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Really? Cool! I mean, um, yes, #h0#. What is it?\r\n#L0##bI wish to repeat my previous training.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Previous training? Guess you've hit another wall, then. I don't think repeating that training is going to help, #h0#...\r\n#L0##bI understand, but I seem to be out of options.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Hm. Wait...wait! I've got an idea. Ohh, I've got a really good idea! You want to get back to your old power level, right? In that case, you should practice against you! Or, more specifically, you from the past."):
    sm.startQuest(parentID)
else:
    sm.sendNext("I know it sounds crazy, but I'm sure it's just crazy enough to work!")
