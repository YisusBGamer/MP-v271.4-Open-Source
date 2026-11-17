# id 34330 ([Lachelein] Nightmare Clocktower 4F), field 450003530
sm.setSpeakerType(3)
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(3003209) # Gray Mask
sm.sendNext("Hm... The dream is getting weaker.")
sm.sendSay("Take this for now. You don't know when you'll transform into a Dreamkeeper again.")
sm.sendSay("I discovered this when I was a Dreamkeeper. I don't know if it'll help you.")
sm.sendSay("I see Protective Mask couldn't make it in the end. The shock must have been too much. ")
sm.sendSay("But don't worry. He will recover. ")
sm.sendSay("#h0#...")
sm.setSpeakerType(4)
sm.setSpeakerID(3003210) # Gray Mask
sm.setParam(2)
sm.sendSay("I'm going to stop Lucid.")
sm.setSpeakerType(3)
sm.setParam(4)
sm.sendSay("I won't stop you. End this, #h0#. For yourself, and for us.")
if sm.canHold(1712003):
    sm.completeQuest(34330)  # gives the Arcane Symbol: Arcana (Act.img); the script used to give a second one
    sm.startQuest(34331)
    sm.completeQuestNoCheck(34331)
    sm.sendSay("#rDue to Swordie Plus custom changes you don't need to Kill Easy Lucid, you can continue with Arcana once you reach level 225.")
else:
    sm.sendNext("Please make room in your EQUIP inventory. I have something very important to give you.")
