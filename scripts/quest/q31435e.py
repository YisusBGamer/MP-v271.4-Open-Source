# 31435 - Tonero and Romero
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Romero (3001005)

sm.setSpeakerID(3001005)  # Romero
if sm.sendNext("Let's continue our trade, Kaiser! This will be a simple one. I'll give you the final piece of information, and you'll give me that transmitter. I've wanted it for a while!\r\n#L0##bI'm not sure that's a good idea...#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Don't stress, I'll give you the one I had, yeah? Mine works. There shouldn't be any problems. It's not like you're using any of the special features anyway.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Awesome! Good trade, friend.\r\n\r\n#b(...Did I just trade the #b#t2430947# for #t4033374#? I'm not even sure what happened... Maybe they're more like magicians than merchants?)#k")
    sm.sendSay("#b(What #b#p3001002##k may say worries me, but eh. I guess it wouldn't hurt to give this away. I made a promise with #b#p3001005##k.)")
    sm.sendSay("Here's your last piece of information. Take it to #b#p3001002##k. See ya!\r\n")
