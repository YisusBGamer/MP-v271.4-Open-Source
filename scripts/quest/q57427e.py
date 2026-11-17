# 57427 - Audience with the Empress
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cygnus (1101000)

sm.setSpeakerID(1101000)  # Cygnus
sm.sendNext("Welcome to Ereve. Neinheart told me everything. It can't be easy to carry out Neinheart's grueling requests in a land still so new to you, but you've put all of our minds at ease. Thank you for all of your hard work.")
sm.sendSay("According to reports from Hersha and others, Nobunaga and his army were behind the monster disturbances. Some reports even say that Nobunaga conspires to ally with the nefarious Black Wings.")
sm.sendSay("This means that you're no longer the only one in danger now. My knights and other powerful forces in Maple World will need to help stop Oda and the Black Wings.")
sm.sendSay("I already got in touch with Athena Pierce in Henesys, and I'll reach out to other heroes all around Maple World. I'll ask for full cooperation from your people as well. Would you deliver the message to them?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I hope that we can work together to bring peace to Maple World and your people.")
