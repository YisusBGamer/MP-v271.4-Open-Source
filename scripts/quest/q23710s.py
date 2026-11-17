# 23710 - Clash of the Xenoroids
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Roo-D (2300000)

sm.setSpeakerID(2300000)  # Roo-D
if sm.sendNext("#b#h0##k, there's a message for you.\r\n#L0##bReally?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I know, I was surprised too. It's from #p2159391#. She wants to settle things with you once and for all. She's claiming she's willing to destroy Edelstein if you don't show up. I think she's pretty serious.\r\n#L0##bI see. I suppose I have to fight her...#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Are you sure this is a good idea? She's pretty mad. You may not come out come back in one piece.\r\n#L0##bWhat are you so worried about?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I guess these humans are corrupting my programming, hehe. Anyway, just be careful for me, okay? She sent us the coordinates...\r\n\r\n#L0##bThis is...#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("It's the lab we escaped from. \r\n#L0##bI believe the humans would call this poetic. Beryl must really want an end.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("I'm afraid she does. You can't go into this with any doubts. Let me know when you're prepared, and I'll take you to the lab.\r\n#r(Press Yes to go with Roo-D.)#k"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Don't rush into it. I don't want you getting blown up. I'll take you when you're ready.")
