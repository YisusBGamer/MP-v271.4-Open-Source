# 2613 - Investigating the Dark Lord
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ryden (1057001), taken from the other quest phase

sm.setSpeakerID(1057001)  # Ryden
if sm.sendAskAccept("The Dark Lord has accepted you into his fold. Don't worry about how I know. I just do. On to phase two of your mission!  #bStealthily interrogate the people of Kerning City and dig up some dirt on the Dark Lord.#"):
    sm.startQuest(parentID)
    sm.sendNext("Don't waste your time talking to everyone. Just talk to those that are closest to the Dark Lord: Nella, Shumi, JM, and Don Giovanni. Do it quickly.\r\nOh, and you'll need something to help you with the investigation. You can get it from #b#p1055002##k in #r#m103050300##k.")
else:
    sm.sendNext("How can I have faith in you if you don't have faith in yourself?")
