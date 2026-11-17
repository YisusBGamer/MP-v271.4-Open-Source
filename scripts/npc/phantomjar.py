# 2420023 Pot (Shabby House, Mirror World Ariant) - A Letter from the Sand Bandits (41063): 50,000 mesos for Tigun.
FEE = 50000
if sm.hasQuest(41063) and sm.getQRValue(41063, "clear") != "clear":
    if sm.sendAskYesNo("#b(An old clay pot sits in the corner. This must be the pot from Jano's letter.)#k\r\n\r\n"
                       "Put #r50,000 mesos#k into the pot?"):
        if sm.getMesos() >= FEE:
            sm.deductMesos(FEE)
            sm.setQRValue(41063, "clear", "clear")
            sm.sendSayOkay("#b(You dropped 50,000 mesos into the pot. Tigun should keep quiet now. "
                           "Better tell Jano at the hideout.)#k")
        else:
            sm.sendSayOkay("#b(You don't have 50,000 mesos.)#k")
elif sm.hasQuest(41063):
    sm.sendSayOkay("#b(The mesos are in the pot. Jano is waiting at the Sand Bandits' hideout.)#k")
else:
    sm.sendSayOkay("#b(An old clay pot. It's empty.)#k")
sm.dispose()
