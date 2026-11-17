# 23677 - The Secrets of the Puppeteer
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Francis (2159394)

sm.setSpeakerID(2159394)  # Francis
if sm.sendNext("How could #p2159395# abandon me like that?! *Sniff-sniff*\r\n#L0##bPlease stop crying. I need information.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You think you're so tough? How would YOU like it if you kept getting your butt kicked?! Everybody I ever meet is just another dumb thug!\r\n#L0##bI do not want to fight you any further. Tell me more about #p2154009#.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("How can I trust you?\r\n#L0##bI'll keep my promise.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You're lying, I don't trust your-your... spooky eyes!\r\n#L0##bWould it help if I ran a tear simulation?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You're a creep. DON'T CRY, I'll believe you... jeez...\r\n#L0##bCrying together is a good way to convince people to do things. Thank you for the observation. Now tell me what #p2154009# ordered you to do.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("H-He just told me to find #ba suspicious guy carrying a stretching sword#k... He told me to keep it a secret, even from #bOrchid#k. Wait! That's you! \r\n#L0##bOrchid, the head of the Black Wings?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Yeah, that's weird, isn't it? #p2154009# must have some kinda crazy scheme if he wants to keep Orchid out of it... But I will not let him prevail! I am #p2159394#, genius puppet shaman and Orchid fanboy!\r\n#L0##bIf that's the case, you shouldn't have to follow #p2154009#'s order to capture me, right?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I, uh, suppose you have a point.\r\n#L0##bIf #p2154009# questions you, tell him I escaped after you defeated me in battle.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("R-really? Okay, that doesn't sound too bad. Ahem, you better be thankful!\r\n#L0##bOf course.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Hmph. I don't know WHY #p2154009# has a smart person like you in his sights, but I appreciate your understanding how amazing I am. Take this...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("It's not because I like you. It's just that I enjoy when people respect me.")
