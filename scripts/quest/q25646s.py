# 25646 - Joining the Fight
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Agent N (1032212)

sm.setSpeakerID(1032212)  # Agent N
if sm.sendNext("Hey. I get that you're busy, but you're going to want to hear this.\r\n#L0##bWhat news do you bring?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Have you ever heard of the Silent Crusade?\r\n#L0##bA crusade that is silent? Explain.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("It looks like they're a secret organization that hunts down monsters. We don't know the exact details, but it sounds like they only go after the Black Mage's personal pets.\r\n#L0##bThen they are friends as far as I am concerned.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("I thought you'd say that. You should check them out. If they end up being good guys, you could help each other a lot."):
    sm.startQuest(parentID)
else:
    sm.sendNext("Fair enough. I'm sure you've already got plenty on your plate.")
