# 2150 - Truth of the Rumor-Ayan
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ayan (1022007)

sm.setSpeakerID(1022007)  # Ayan
if sm.sendNext("Hi, traveler. What brings you here today? How can I help you?\r\n#b#L0#Do you know anything about a ghost tree?#l") != 0:
    sm.dispose()
if sm.sendSay("Oh, my! You also heard about the story? #p1012108# of #m100000000# said she saw the ghost on the way back to her home after visiting #m102000000#for her mom's errand.\r\n#b#L0#Are you serious?#l") != 0:
    sm.dispose()
if sm.sendSay("Yes, she was going back to #m100000000# late at night. She stepped or tripped over a tree branch or something in the dark. She got scared and looked around, and you know what? She found a big eye staring into her as if it would eat her alive.\r\n#b#L0#That's terrible...#l") != 0:
    sm.dispose()
sm.sendSay("#p1012108# was terrified and simply passed out at that moment. After dawn, a number of adults went up there, but found nothing. It must be ghosts. What can we do? I don't think I can even get out of town. I'm too scared!")
sm.startQuest(parentID)
