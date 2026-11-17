# Korean Folk Town: after Wiz's Gratitude (30458), Moni's letter (A Letter from Moni, 30459). In GMS it
# started at a letter NPC (2074100) only a scene placed; the letter is read here instead.
MONI = 2074100
if not sm.hasQuestCompleted(30459):
    sm.setQRValue(30459, "end", "1")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(A letter arrives from Korean Folk Town.)")
    sm.setSpeakerID(MONI)
    sm.sendNext("Dear adventurer,\r\n\r\nThe village is quiet again. Nolbu and Hongbu share their meals now, Chil "
                "Sung and Chil Nam are building a house together, and Kong Ji tells me the Magistrate visits every "
                "day.")
    sm.sendNext("And my old friend from the mountain comes down to help with the rice cakes. The children aren't "
                "afraid of him any more. I think they never really were. We grown-ups just forgot how to look.")
    sm.sendNext("Thank you for helping Chumji gather the stones. Come visit us whenever you like.\r\n\r\n- Moni")
    sm.startQuestNoCheck(30459)
    sm.completeQuestNoCheck(30459)
sm.dispose()
