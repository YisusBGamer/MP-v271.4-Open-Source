# Can I hold the Cannon? (31614), end: Kaiser at the Path to the Reclamation HQ (Angelic Buster's Heliseum story).
# The client has no lines for this part; written from the quest's own Quest.wz summaries ("Beldar tricked you!
# Hurry and follow Kaiser to Heliseum." / "Kaiser was shocked. Wait for him to calm down and talk to him again.").
KAISER = 3001010
sm.setSpeakerID(KAISER)
sm.sendNext("Angelic Buster?! What are you doing here? This road runs straight into Specter territory!")
sm.setPlayerAsSpeaker()
sm.sendSay("Beldar said you'd gone on ahead with the Blue Dragon Cannon. All by yourself! Did you really think I'd let you hog all the action?")
sm.setSpeakerID(KAISER)
sm.sendSay("...I... Give me a moment. I need to think.")
sm.completeQuest(parentID)
sm.dispose()
