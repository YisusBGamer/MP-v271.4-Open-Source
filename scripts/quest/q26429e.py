# 26429 - [Maple Rewards] 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
sm.sendNext("Did the Reward Points Bag do its thing? \r\nWell, let's see what you got. I'll get my calculator. ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You earned 600 #b#i2431872# #t2431872# items!#k \r\n\r\nLet's see how much mileage you have...\r\nWait... That's only 100...")
    sm.sendSay("...Psych!\r\n\r\n200!\r\n...300!\r\n...400!\r\n...500!\r\n...600!!\r\n\r\n#b#eYou're up to 600!#k#n\r\nSeriously, how much mileage did you earn today? \r\nSimply amazing!")
    sm.sendSay("Congratulations! The Reward Points Bag is completely worn out. I'm so proud of that bag. Don't worry, it will be full of energy and life and goodies again tomorrow. \r\n\r\n #b#h0##k, I'll look forward you see you again at the #bPremium PC Cafe#k once again! \r\n\r\nThe unprecendented Reward Points event! \r\nYou've completed the event with me, Matilda, and the Reward Points Bag!")
