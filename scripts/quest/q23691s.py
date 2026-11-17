# 23691 - A Rough Welcoming
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendNext("Ah, this place brings back memories. Look at all that dust! I used to clean that! \r\n#L0##bDo these robots look new to you?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I sure don't know any of them. I think #p2154009# knew you'd come back. \r\n#L0##bWhat a warm welcome.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("They should be no match for you, but they're going to hinder our investigation. Take out #b100#k #b#o9300645##k robots!"):
    sm.startQuest(parentID)
    sm.sendNext("I know we're here to get your memories back, but this is really drudging up some nasty times for me. Let's get moving, okay?")
else:
    sm.sendNext("What's the matter? Don't get all nostalgic on me.")
