# 2159347 Vita at the Mine Entrance (931000620), Vita's Disappearance (Resistance 23143, checks record 23149 = 1).
# She isn't a spy: Gelimer took her. Claudine hears it in the quest's end dialogue.
if sm.hasQuest(23143) and sm.getQRValue(23149) != "1":
    sm.sendNext("#h0#...? You came all the way out here... for me?")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Everyone thinks you broke out of your cell to join the Black Wings. Is it true?")
    sm.setSpeakerID(parentID)
    sm.sendSay("No! I'd never... It was #p2154009#. He took me from the hideout. He says he still needs me for his experiments...")
    sm.sendSay("He's coming back, #h0#. If he finds you here... Please, go! Tell #p2152004# I'm not a spy!")
    sm.startQuest(23149)
    sm.setQRValue(23149, "1")
    sm.removeNpc(parentID)
    sm.chatScript("Vita... Go back to Claudine and tell her what happened.")
sm.dispose()
