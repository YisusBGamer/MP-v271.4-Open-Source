# Fraidy Cat (9120012), Showa Town (801000000)
# It used a feeding feature whose constants (ContentConstants) don't exist on this server, so clicking it failed;
# it says its own lines now (Npc.wz d0 / d1)

sm.setSpeakerID(9120012)
if sm.sendAskYesNo("I'm hungryyy... Can you hear me out?"):
    sm.sendSayOkay("Meow! Why don't you take me home? I'd make a nice pet!")
