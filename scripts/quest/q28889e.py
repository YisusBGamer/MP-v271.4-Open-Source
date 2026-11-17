# 28889 - Delivering A Letter To All The Towns
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lucas (9201203)

sm.setSpeakerID(9201203)  # Lucas
sm.sendNext("[Perion]\r\nWho are you? That's some strange equipment you've got. Don't think I've seen you around before. You have something for me?")
sm.sendSay("This is from Lucas...? Dang, I was hoping you'd give me some of that fancy gear you're wearing... ")
sm.sendSay("[Kerning City]\r\nYou surprised me! You don't look like any Exploror I've ever seen. What are you?")
sm.sendSay("Explorer ? You really are an Explorer? Where'd you get that fancy gear? I heard rumors of continents beyond Victoria... Are you from across the sea?")
sm.sendSay("Cygnus Knight ? You seem more dignified than other Explorers. Isn't it rude, putting on airs like that?")
sm.sendSay("Aran ? And what's with that huge axe? Polearm? Can you even fight with that thing? I heard about a hero from the past who fought with such a weapon, but that's just fairytales and make-believe.")
sm.sendSay("Evan ? Where'd you get that dragon? Is it a new pet? It looks stronger than you are! ")
sm.sendSay("Resistance ? You look more like a rebel than an Explorer. Don't you start anything with me, you here? We don't want trouble.")
sm.sendSay("Resistance Mechanic ? What's that funny looking machine? A new mount? Everybody else is riding cool-looking mounts, and here you are, riding around in a pile of scrap metal...")
sm.sendSay("Mercedes? You're so beautiful! Are you a model? And what about your ears? Is it some kind of costume?")
sm.sendSay("Demon Slayer ? Your wings are creeping me out. You can actually fly? What strange fashion... ")
sm.sendSay("This invitation is from Lucas? I can see why he had you deliver it. So... How'd you like to come work for me? ")
sm.sendSay("[Ellinia]\r\nYou don't seem like the other Explorers... What brings you here?")
sm.sendSay("Lucas's invitation... Thanks. But I'm more interested in finding out who you are. You don't seem to be from this place...or time. I can't see anything in this crystal ball. Who are you?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You've delivered all the invitations? ")
    sm.sendSay("I knew they'd be interested in you. Thanks for helping me out!")
