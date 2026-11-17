# Rondo (Six Path Crossway 104020000). Rondo's Hat (36326): the player startles him, his hat flies into the bushes,
# and the player searches them for it.
OLD_WISCONSIN = 4036848
QUEST = 36326

sm.setSpeakerID(1541007)
if sm.hasQuest(QUEST) and not sm.hasItem(OLD_WISCONSIN):
    sm.sendNext("Waah! You scared me! Don't sneak up on people like that!")
    sm.sendSay("My hat! It flew off! It must have landed in the #bbushes#k around here. Please, you have to find it!")
    sm.setPlayerAsSpeaker()
    sel = sm.sendNext("#b(Which bush should you search?)#k\r\n#L0#The bush by the signpost#l\r\n"
                      "#L1#The bush under the tree#l\r\n#L2#The bush by the road#l")
    if sel != 2:
        sm.sendSay("#b(There's nothing but leaves here... Wait, something is sticking out of the next bush.)#k")
    sm.giveItem(OLD_WISCONSIN)
    sm.sendSayOkay("#b(You found an old hat in the bushes. Is this the one Rondo was wearing? Give it back to him.)#k")
elif sm.hasQuest(QUEST):
    sm.sendSayOkay("Is that my hat? Give it here, quick!")
elif sm.hasQuestCompleted(QUEST) and not sm.hasQuestCompleted(36327):
    sm.sendSayOkay("You want to know about my hat? ...Fine. Ask me properly.")
else:
    sm.sendSayOkay("Hm? What do you want? Don't look at my hat.")
sm.dispose()
