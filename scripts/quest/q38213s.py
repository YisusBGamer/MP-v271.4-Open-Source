# 38213 [Karote] Reunion - start (Nicky, Summit). Nicky reveals himself as T-Boy; no end NPC.
NICKY = 3005253
KALOS = 3005265

sm.setSpeakerID(NICKY)
sm.setBoxChat()
sm.sendNext("You followed me. Of course you did.")
sm.setSpeakerID(KALOS)
sm.sendNext("...VESSEL OF THE ADVERSARY, RECOGNIZED. INTRUDER... HOSTILE. ELIMINATE.")
sm.setPlayerAsSpeaker()
sm.sendNext("(The machine guarding the summit, #bKalos#k, let you pass, but turned its weapons on Nicky.)")
if sm.sendAskAccept("Nicky, why is Kalos attacking you?"):
    sm.startQuest(parentID)
    sm.setSpeakerID(NICKY)
    sm.sendNext("Heh... I guess there's no point pretending anymore.")
    sm.sendNext("There's no Nicky. There never was. I'm #rT-Boy#k, of the #rAngler Company#k.")
    sm.sendNext("I needed someone the guardian would let through. Thanks for the escort, Adversary.")
    if sm.hasQuest(parentID):
        sm.completeQuestNoCheck(parentID)
sm.dispose()
