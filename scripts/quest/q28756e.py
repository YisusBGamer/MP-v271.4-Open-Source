# 28756 - [Repeatable] The Alien Mainframe
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9201050)  # Icebyrd Slimm
    sm.sendNext("Ooh! Did you really destroy the #b#o9400802##k?! Amazing... you are tough as can be!")
    sm.sendSay("Too bad about that matter-cloner they have, though! The #b#o9400802##k you took down is just going to get remade later. Bummer, huh? But maybe you bought us some time!")
    sm.sendSay("If you want to shut down their operation, you're going to have to destroy that #b#o9400802##k a whoooole bunch of times. It may be an endless fight. That's why I'm pretty sure YOU are the perfect person to do it.")
