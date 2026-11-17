# 63353 - [Mechanical Hearts] Soak Up the Sun
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spencer (9400431)

sm.setSpeakerID(9400431)  # Spencer
sm.sendNext("Hello #h0#. Welcome to #bAspire Industries#k, manufacturers of joy and peace-of-mind.")
sm.sendSay("As I'm sure you've heard, we're releasing a line of premium androids called #cWondroids#. I'd like to make you a special offer in exchange for, um, helping spread the word about our products.")
if sm.sendAskAccept("Hold onto this nifty, proprietary Aspire #b#esolar battery#n#k as you go about your business. #b#e30 minutes#n#k should be enough for a full charge, and turn some heads.\r\nThen bring it straight back here.\r\nIn return, I will give you an #bAspire Visitor's ID#k ypu can use to take a tour of our company, and an #bAspire Commemorative Coin#k that can be used to purchase various souvenirs. Sound good?"):
    sm.startQuest(parentID)
    sm.sendNext("Good. I'll see you in 30 minutes.\r\n#fs10#Phew... We're gonna need as much as we can get our hands on...\r\nW-what? I didn't say anything. You must be hearing things. Thanks for visiting #bAspire#k!")
else:
    sm.sendNext("Y-you don't want to take the tour? I guess it can't be helped.\r\nHave a good day.")
