# [Ellinel Fairy Academy] You Can Do It
# Fanzy's official lines (Quest.wz Say.img)

FANZY = 1500010 # NPC ID
YOU_CAN_DO_IT = 32102 # QUEST ID

sm.setSpeakerID(FANZY)
sm.sendNext("Meow... This is the #bEllinel Fairy Academy#k. Try not to be intimidated by the brainpower on display.\r\n#L0##bEllinel Fairy Academy?#l#k")
sm.sendNext("This is where little fairies learn to sprinkle their dust and turn up their noses to lesser beings. \r\n#L0##bWhy's it hidden so deep in the forest?#l#k")
sm.sendNext("Don't you know anything? #bEllinia#k used to be a fairy town, before the Black Mage and his goons came and swatted them all like craneflies? That's when the humans moved in. \r\n#L0##bBut there are fairies outside of Ellinia.#l#k")
sm.sendNext("And there are birds that don't run when they see me. Some fairies accept humans. The ones in the #bEllinel Fairy Academy#k are not those fairies.\r\n#L0##bYou think Cootie got captured by these human-hating fairies?#l#k")
response = sm.sendAskAccept("Most likely. I know I thought about using him as a scratching post a few times. Master #bGrendel#k and I tried to befriend the fairies, but they just weren't listening. I think we should use more... forceful methods.\r\n#b#h0##k, let me ask... are you a good swimmer?")

if response:
    sm.startQuestNoCheck(YOU_CAN_DO_IT)
    sm.sendSayOkay("Why don't you go for a swim! Show us how brave you are, meow... \r\n#b(Cross the lake to the right.)#k")
else:
    sm.sendSayOkay("Do you want to jump in the water to amuse me anyway?")
sm.dispose()
