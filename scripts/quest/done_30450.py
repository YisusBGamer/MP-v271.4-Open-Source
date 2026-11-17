# Korean Folk Town: after Black Mountain Flames (30450). In GMS the next five quests were scenes on the scorched
# mountain with a Goblin and with Moni, played by NPCs (2074034, 2074019) only those scenes placed: Goblin? (30451),
# Chumji and the Goblin (30452), A Goblin's Heart (30453), Moni's Agony (30454) and Moni Remembers (30455). They're
# played here, one after the other (none has anything to hand in). Then Chumji: Time Goes By (30456).
GOBLIN = 2074034
MONI = 2074019
CHUMJI = 2074000


def scene(q, lines):
    if sm.hasQuestCompleted(q):
        return
    for speaker, text in lines:
        if speaker == 0:
            sm.setPlayerAsSpeaker()
        else:
            sm.setSpeakerID(speaker)
        sm.sendNext(text)
    sm.startQuestNoCheck(q)
    sm.completeQuestNoCheck(q)

scene(30451, [
    (0, "(Where the fire started, a small figure is stamping out the last embers... it's a goblin!)"),
    (GOBLIN, "Go away! I didn't start it! I was putting it OUT! ...Don't look at me like that. Everyone always looks "
             "at me like that."),
])
scene(30452, [
    (CHUMJI, "So you're the one the villagers have been whispering about. A goblin on Black Mountain..."),
    (GOBLIN, "Chumji! You of all people... You were the one who gathered the stones, weren't you? The anger, the "
             "regret, the greed... I felt them leave the village, one by one."),
    (CHUMJI, "Then you know why the mountain burned. The last stone was hidden up here."),
])
scene(30453, [
    (GOBLIN, "I didn't take it to hurt anyone. Moni dropped it, long ago, and I kept it safe. I thought if I kept "
             "it, she'd come back for it one day... and remember me."),
    (0, "(The goblin's eyes are wet. He holds out a dull grey stone with both hands.)"),
])
scene(30454, [
    (MONI, "That voice... I know that voice. When I was little, I was lost on this mountain, and someone carried "
           "me home through the dark. I never saw his face."),
    (GOBLIN, "...You were very small. You cried the whole way."),
    (MONI, "It was you. All this time, everyone said the goblin was a monster, and I never told them otherwise."),
])
scene(30455, [
    (MONI, "I remember now. The stone fell from my pocket that night. I was so ashamed that I'd been afraid of "
           "you that I tried to forget the whole thing... and the stone kept the forgetting."),
    (GOBLIN, "It doesn't matter. You remember now."),
    (MONI, "It matters to me. Chumji, take the stone. And... let him come down to the village. Please."),
])
sm.setSpeakerID(CHUMJI)
sm.sendSayOkay("Every stone is back. Let's go down to the village, both of you. There's someone in town I'd like "
               "you to meet.\r\n#b(Talk to Chumji in Korean Folk Town.)#k")
sm.dispose()
