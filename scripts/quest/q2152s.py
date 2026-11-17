# 2152 - Truth of the Rumor-Betty
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Betty (1032104)

sm.setSpeakerID(1032104)  # Betty
if sm.sendNext("Welcome. How can I help you?\r\n#b#L0#Do you know anything about a ghost tree?#l") != 0:
    sm.dispose()
if sm.sendSay("You must be helping Doctor #p1022006#'s research, right? Well, I did a bit of research myself after he asked me, but I didn't get much. The only thing I figured out was that the forest on the border between #m102000000# and Ellinia is drying up very quickly. It may not look so fast from the surface, but it is very drastic.\r\n#b#L0#Indeed. Thank you very much for your time.#l") != 0:
    sm.dispose()
sm.sendSay("I'm sorry I can't be of much help.")
sm.startQuest(parentID)
