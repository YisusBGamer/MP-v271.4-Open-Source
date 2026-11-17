# Cliff (Happyville, Cliff's Hut) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 2001000
from net.swordie.ms.world import ChristmasEvent as X

ACTIVE = X.isActive()


def can_start(q, *prev):
    # the event is on, the level is right, the quests before it are done, and it isn't started yet
    if not ACTIVE or chr.getLevel() < X.MIN_LEVEL or sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def finish(q):
    return X.finish(chr, q)


def default():
    line = sm.getRandomNpcDefaultLine(NPC)
    sm.sendSayOkay(line if line is not None else "Merry Christmas!")

sm.setSpeakerID(NPC)


if can_start(X.CLIFF) and sm.hasQuest(X.HANGING):
    for line in ['You look happy young man. I have never felt this empty in my life when I should be singing and dancing for the Christmas... Recently, as Santa Claus has gained too much weight, 4 Rudolphs were never enough to lead him and his sledge full of presents. He was quite worried about that. So... I had to let him take my #bTorr#k who I have been taking care of since I was young.', "I know that Santa Claus will take a good care of him but I am still worried if other Rudolphs might make fun of him since Torr is so weak and faint-hearted. Torr has always been next to me throughout the years and now that I don't have him here with me, this is what makes feel so lonely.", "But, there are some good things that I can enjoy too. Christmas is the only chance for me to see Santa Claus and Rudolphs flying beautifully in the sky. That's why I have climbed up to the 2nd floor but it is still hard to see because of the other tall houses... And I know better than anyone I can't climb up anymore with old body of mine..."]:
        sm.sendNext(line)
    if sm.sendAskAccept('So, will you be kind enough to make it to the highest point of the town and take a picture of my #bTorr#k who will be leading the sledge in a dashing figure?'):
        sm.startQuestNoCheck(X.CLIFF)
        sm.sendNext('I could tell from the first sight that you will be able to do me a favor. Your eyes are so kind. Thanks. Now, climb up to the highest point of the town and you will find a tripod to hold the camera up there. Please take a picture of brave Torr leading the sledge there.')
        sm.sendSayOkay("Torr was the 5th one to be joined in the Santa's sledge club so he will be leading the sledge in the last, 5th row. Take a good picture of him, showing his face clearly. That picture will be my only pleasure to get over my yearning for Torr this year.")
    else:
        sm.sendSayOkay("You have lots of things to do in this town. I understand... It's always busy during this time around. When you have some time to hear me out, please come back to me. I will be standing here...")
elif sm.hasQuest(X.CLIFF):
    if sm.hasItem(X.PHOTO):
        sm.sendNext('So, did you bring the picture with you?')
        sm.sendNext('Sure you did! You did take the picture! Let me see it! Santa Claus is as busy as he was for past years. Ho ho ho!')
        sm.sendNext("It's weird! How come there is only one Rudolph? There should be #bTorr#k at the end of the line but he is not in here. What happened? Did Torr leave the house? Is he hurt? You took this photo right? Something must have happened to Torr... What shall I do now?")
        if finish(X.CLIFF):
            sm.sendNext("Some people told me that they have seen a Rudolph crying around the corner of Happyville. I am worried that the one people are talking about might be #bTorr#k. Since I am away from home, he won't be able to go inside the house... He should be freezing outside.")
            if sm.sendAskAccept("Anyhow, it will be faster than an oldman like me if you can look for him and see if it's really #bTorr#k that people are talking about. Can you do it for me, you young handsome, pretty, kind Mapler? If it's #bTorr#k, please tell him to come to me. I will be waiting for him on the 2nd floor of center tower."):
                sm.startQuestNoCheck(X.TORR)
                sm.sendSayOkay("I have a feeling that it is #bTorr#k. I shouldn't be relaxing like this if it really is Torr. I have to prepare to greet him. Please go find if Torr is the one they are talking about. And if so, deliver my words to him. Hurry... Thank you...")
            else:
                sm.sendSayOkay("It shouldn't be that hard for you... Well if you say you are busy... Okay... But I recommend you to come find me again before you leave this town.")
    else:
        sm.sendSayOkay("Hmm... You didn't take the photo yet. You will be able to find a tripod on the highest place of the town. Be careful not to fall off from there.")
elif can_start(X.TORR, X.CLIFF):
    sm.sendNext("Some people told me that they have seen a Rudolph crying around the corner of Happyville. I am worried that the one people are talking about might be #bTorr#k. Since I am away from home, he won't be able to go inside the house... He should be freezing outside.")
    if sm.sendAskAccept("Anyhow, it will be faster than an oldman like me if you can look for him and see if it's really #bTorr#k that people are talking about. Can you do it for me, you young handsome, pretty, kind Mapler? If it's #bTorr#k, please tell him to come to me. I will be waiting for him on the 2nd floor of center tower."):
        sm.startQuestNoCheck(X.TORR)
        sm.sendSayOkay("I have a feeling that it is #bTorr#k. I shouldn't be relaxing like this if it really is Torr. I have to prepare to greet him. Please go find if Torr is the one they are talking about. And if so, deliver my words to him. Hurry... Thank you...")
elif sm.hasQuest(X.TORR):
    sm.sendSayOkay('Please go check if it is #bTorr#k or not... And if it is... please tell him what I said!.. Please hurry, poor baby is freezing out there.')
else:
    sm.sendSayOkay("Merry Christmas! Feel free to show off your holiday spirit with ornaments!")

sm.dispose()
