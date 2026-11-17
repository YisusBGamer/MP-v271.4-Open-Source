# 9732 - To Fool a Liar
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hengki (9010013)

sm.setSpeakerID(9010013)  # Hengki
sel = sm.sendNext("Hey, aren't you a human being? What's a human being doing here? Something smells fishy here...?  \r\n#L0##b You're the one that's planning on attacking the Maplers, right? I can't let you do that!  \r\n#L1# Hey, I am just here to help you out. \r\n#L2# I... actually... um... like you. I've been meaning to say that. ")
if sel != 1:
    wrong = {
        0: "What? How did you find out? My friends here won't let you leave, that's for sure!",
        2: "Re...really? Well, I think you're a great person, but monsters and humans aren't meant for one another...",
    }
    if sel in wrong:
        sm.sendSayOkay(wrong[sel])
    sm.dispose()
sel = sm.sendSay("Help? What help? How can I trust you? You look creepy!\r\n#L0##b What are you saying? That's not true! Look at me in the eyes! My innocent-looking eyes...!~ \r\n#L1# Who's calling me creepy-looking? You look creepier than me! \r\n#L2# Hmph... honestly, I've been hearing that for a while...")
if sel != 0:
    wrong = {
        1: "What? What part of my face screams deception? I am about as pure as one can get!",
        2: "I see... it's okay. One day you'll find someone that'll look past your creepy looks.",
    }
    if sel in wrong:
        sm.sendSayOkay(wrong[sel])
    sm.dispose()
sel = sm.sendSay("Hmmm... fine, I am still not sure about this, but I'll hear you out. What do you want from me?\r\n#L0##b I heard you were looking for something that will turn you into a human being, so I brought the best transformation item out there. \r\n#L1# I thought it was interesting to see a monster talk, so.... \r\n#L2# I am going to make sure you will never turn into a human being!")
if sel != 0:
    wrong = {
        1: "What are you saying? If you're really that bored, go talk to the game admin.",
        2: "...wait a minute, how did you know of my plans? ",
    }
    if sel in wrong:
        sm.sendSayOkay(wrong[sel])
    sm.dispose()
sel = sm.sendSay("What? You brought the stuff needed to transform? Thanks!... wait, you really thought I meant that? Are you kidding me? How am I supposed to know that what you brought is real? Isn't it really something else? \r\n#L0##b Dang, how did I get caught. Well... how about 100 mesos for a Red Potion?\r\n#L1# Wat, how did you know? Well... this is actually a leaf of transformation where you won't be able to tranform at all...! \r\n#L2# Well... don't tell anyone, but... I'm actually a monster... but I took it, and look at me now. Isn't it amazing? You should take this as well...")
if sel != 2:
    wrong = {
        0: "What? 100 mesos for a Red Potion? What a rip off! I am not buying anything from you!",
        1: "I knew it. I knew something was weird when a human being came up to me to talk about something. I am not falling for your lies, so get out of here right now!",
    }
    if sel in wrong:
        sm.sendSayOkay(wrong[sel])
    sm.dispose()
sm.sendSay("What? You're a monster too? I see... well, your eyes did seem a little like that of a monster's... Okay, I feel like I can trust you now. I'll take your transformation leaf. Thanks!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Wait a minute, what's going on here? Why am I not turning into a human being? I just sniffed this, yet I'm still... me. Wait... is this... is this #t4031585#?! Did you just stop me from turning into human? You lied! How dare you!")
