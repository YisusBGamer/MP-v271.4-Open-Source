# 37601 [Reverse City] What Washed Up - start: Legato (3003111), Nameless Town
# Official summary: Legato has something to say about an item that washed up on the river; you check the
# walkie-talkie that washed up. The walkie-talkie is checked here, so the quest is finished in the same talk.
sm.setSpeakerID(3003111) # Legato
sm.sendNext("#h0#, something washed up on the river this morning. I've never seen anything like it.")
sm.setPlayerAsSpeaker()
sm.sendSay("(It's a #bwalkie-talkie#k. Battered, but it still works. Static crackles out of it...)")
sm.sendSay("(...Voices come through the noise. Someone is talking about their city being turned #bupside-down#k.)")
sm.setSpeakerID(3003111) # Legato
sm.sendSay("Things drift down the Arcane River from all sorts of places, but a whole city? If it came from upstream, there's an old #bsubway station#k up there.")
sm.startQuest(parentID)
sm.setQRValue(parentID, "dummy", "1")
sm.completeQuestNoCheck(parentID)
sm.sendSay("Talk to me again when you're ready to look into it.")
