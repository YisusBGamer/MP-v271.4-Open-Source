# 20710 - Hidden Inside the Trash Can
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matthias (1103002)

sm.setSpeakerID(1103002)  # Matthias
if sm.sendAskAccept("You don't really instill confidence in me, but since you're a Cygnus Knight and all... and since no one else can go on a search right now... Okay, let me explain to you what this mission is about."):
    sm.startQuest(parentID)
    sm.sendNext("According to the information we acquired recently, the subways in #m103000000# are apparently full of strange dolls. Jake told me this, so I can say this with confidence. Those dolls do seem quite suspiscious... so I want you to bring #t4032136# back to me.")
    sm.sendSay("Jake said any dolls found inside the subway have all been thrown out, so if you go through the trash can, then maybe you'll be able to find one. Good luck!")
else:
    sm.sendNext("What? Are you declining the mission? Fine, do it like that. I'll just report it straight to #p1101002#.")
