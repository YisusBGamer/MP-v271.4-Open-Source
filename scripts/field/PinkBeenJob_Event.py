# id 270050100 (Deep Place of Temple : Twilight of Gods), field 270050100

# Quest 3522 asks the player to witness the intruder at the Goddess's Mirror, before boss entry.
# This script is also assigned to the safe 270050000 story map in v232.
if sm.hasQuest(3522) and sm.getQRValue(7402) != "1":
    sm.setPlayerAsSpeaker()
    sm.sendNext("There's someone at the Goddess's Mirror! I should stop him before he touches it.")
    sm.setSpeakerID(2141000) # Kirston
    sm.sendNext("With the Mirror of the Goddess, I can summon the Black Mage again! But... what is this strange energy?")
    sm.setPlayerAsSpeaker()
    sm.sendNext("He touched the mirror before I could stop him. I need to tell the Temple Keeper what happened.")
    sm.setQRValue(7402, "1", False)
