# 1434 - Chiefs of El Nath: 3rd job advancement (Magician), v265: given by the chief

sm.setSpeakerID(2020009)
if chr.getLevel() < 60:
    sm.sendSayOkay("Come back when you've reached #blevel 60#k.")
    sm.dispose()
job = chr.getJob()
if job % 10 != 0:
    sm.sendSayOkay("You've already made your 3rd job advancement.")
    sm.dispose()
if sm.sendAskYesNo("You've come a long way. Are you ready to take on your 3rd job?"):
    sm.jobAdvance(job + 1)
    sm.completeQuest(parentID)
    sm.sendSayOkay("Your 3rd job advancement is complete. Use your new power well.")
else:
    sm.sendSayOkay("Come back when you're ready.")
