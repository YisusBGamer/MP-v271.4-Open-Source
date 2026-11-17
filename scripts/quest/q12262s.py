# 12262 - [Lucky 3!3!3! Event] Census Week Two Check 
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("I think the Lucky 3!3!3! Event is the PERFECT time to do a census. After all, I want to make sure I have precise records of EVERY citizen in Maple World. Purely to keep my files up to date, of course. Nothing nefarious. \r\nIf you #rlog in and complete an attendance check at least 3 times a week#k between #b5/27/2015#k and #6/2/2015#k, I'll even throw you into a raffle for some free #bMaple Points#k. What do you say?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Evading the census, eh? I wonder what you're trying to hide. Don't worry, I WILL find out.")
