# id 2430947 (D-03 Transmitter), field 401020300
# Eurenth's Advice (31821) and Kaiser's Secret Assembly (31429) both ask you to use it at the Secret Merchant Meet-Up:
# the merchant crew answers, and their record (31822 / 31548) is what each quest's end check waits for.
used = False
if sm.getFieldID() == 401020300 and sm.hasQuest(31821):
    sm.setSpeakerID(3001004) # Tonero
    sm.setParam(4)
    sm.setInnerOverrideSpeakerTemplateID(3001004) # Tonero
    sm.sendNext("Who are you? That Transmitter ID code belongs to #b#p3001002##k.")
    sm.setParam(2)
    sm.sendSay("I'm one of the Maple World adventurers that came to help Nova. Are you the Shadowdealer?")
    sm.setParam(4)
    sm.sendSay("Oh, are you from this world? #b#p3001002##k mentioned something about having an uninvited guest. I guess he was talking about you.")
    sm.sendSay("Ugh the static is getting worse. I'll go there myself. Just wait.")
    sm.setParam(2)
    sm.sendSay("(The communication terminated. There's an error message on the D-03 Transmitter.)")
    sm.startQuest(31822)
    sm.setQRValue(31822, "1", False)
    sm.startQuest(31575)
    used = True
if sm.getFieldID() == 401020300 and sm.hasQuest(31429) and sm.getQRValue(31548) != "1":
    sm.setSpeakerID(3001004) # Tonero
    sm.setParam(4)
    sm.setInnerOverrideSpeakerTemplateID(3001004) # Tonero
    sm.sendNext("Who are you? That Transmitter ID code belongs to #b#p3001002##k.")
    sm.sendSay("Ugh the static is getting worse. I'll go there myself. Just wait.")
    sm.setParam(2)
    sm.sendSay("(The communication terminated. There's an error message on the D-03 Transmitter.)")
    sm.startQuest(31548)
    sm.setQRValue(31548, "1")
    used = True
# Cadena: Ultra Cannon Prepper (34714) calls the Heliseum branch (Romero answers), Eurenth the Sage (34717) the
# Shadowdealers (Tonero answers). Both quests' own record is "Use the D-03 Transmitter".
if sm.getFieldID() == 401020300 and sm.hasQuest(34714) and sm.getQRValue(34714) != "1":
    sm.setQRValue(34714, "1")
    sm.chat("The Heliseum branch answered the call. Talk to Romero.")
    used = True
if sm.getFieldID() == 401020300 and sm.hasQuest(34717) and sm.getQRValue(34717) != "1":
    sm.setQRValue(34717, "1")
    sm.chat("Tonero, instead of Romero, answered your summons. Talk to him about Eurenth.")
    used = True
if not used:
    sm.chat("You aren't using this in the right map.")
