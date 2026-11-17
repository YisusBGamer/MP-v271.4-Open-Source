# 3173 - [Lion King's Castle] The Mistrustful King
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ifia (2161001)

sm.setSpeakerID(2161001)  # Ifia
sm.sendNext("I've been watching this castle for hundreds of years, but I still can't believe my eyes. It used to be so beautiful. Seeing this place in all its glory used to warm my heart... ")
sm.sendSay("And the same goes for the king. Leon... He was a master swordsman, but he was still so warm-hearted and kind. He didn't speak much, but I could feel his generous heart. How could he have changed like that...")
if sm.sendAskAccept("What has the #rBlack Mage#k done to him? Why has he become so...? Has he forgotten about me?  Please...could you speak to Leon for me?"):
    sm.startQuest(parentID)
    sm.sendNext("He's not answering my voice, but I'm certain that he's in the #bAudience Room#k. Please find out whether #bLeon#k remembers me or not, and if he does, why he isn't answering me. ")
else:
    sm.sendNext("I'm sorry... He'll probably attack anyone who enters the Audience Room. That's a lot to ask of you, I'm sure.")
