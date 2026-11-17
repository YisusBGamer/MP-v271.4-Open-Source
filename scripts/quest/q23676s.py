# 23676 - Black Wings on the Move
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Professor Dreamboat (2300001)
# The accept line says you're taken to the map at once: the Abandoned Hideout Entrance (910600300), whose hidden path
# leads down to the Puppeteer's Hidden Cave (portal/enterDMxn.py, The Secrets of the Puppeteer 23677).

sm.setSpeakerID(2300001)  # Professor Dreamboat
sm.sendNext("#b#h0##k, we've got reports of possible #bBlack Wings#k members near Henesys.")
sm.sendSay("This is urgent. I need you out there right away!")
if sm.sendAskAccept("Make sure you're ready.\r\n#r(You'll be taken to the map as soon as you accept.)#k"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warp(910600300, 0)
else:
    sm.sendNext("Make sure you're prepared. This mission could be dangerous.")
