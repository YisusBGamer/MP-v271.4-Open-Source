# id 9130115 (100-Generation Blacksmith), field 801000000
# For Will of the Five Planets 3 (58952) he takes the Scrap Metal and the mesos and makes the Golden Family Plaque, for
# the price he names. The lines are the recording's own.
SCRAP, PLAQUE, PRICE = 4034138, 4034139, 1000000

sm.setSpeakerID(9130115) # 100-Generation Blacksmith
if not (sm.hasQuest(58952) and sm.hasItem(SCRAP) and not sm.hasItem(PLAQUE)):
    sm.sendSayOkay("Hello, hello! My family has practiced the art of smithing for 100 generations. How may I help you?")
else:
    sm.sendNext("Hello, hello! My family has practiced the art of smithing for 100 generations. How may I help you?")
    sm.setParam(2)
    sm.sendSay("I hear you can fix this #i4034138##t4034138#.")
    sm.setParam(0)
    sm.sendSay("Is this...? I never thought I would see it myself. The final page of our legacy blacksmithing guide is about this...")
    sm.setParam(2)
    sm.sendSay("Is something wrong?")
    sm.setParam(0)
    res = sm.sendNext("Hm? No, no... Well, it's just that it's been tough for a blacksmith around here lately. I'll have to charge a pretty hefty price. 1,000,000 mesos will see it done in a jiffy. \r\n#b\r\n#L0# Pay 1,000,000 to fix it. #l\r\n#L1# Doesn't seem worth the money. #l")
    if res == 0:
        if sm.getMesos() < PRICE:
            sm.sendSayOkay("You don't have 1,000,000 mesos. Come back when you do.")
        elif not sm.canHold(PLAQUE):
            sm.sendSayOkay("Make some room in your Etc inventory first.")
        else:
            sm.deductMesos(PRICE)
            sm.consumeItem(SCRAP, 1)
            sm.giveItem(PLAQUE)
            sm.sendNext("#b*THUNK THINK* Okay, it's done.#k")
sm.dispose()
