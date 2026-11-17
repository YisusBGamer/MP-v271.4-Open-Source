# Lambent Essence (2430564). Infiltrate (20755, Mihile): used inside the Dimensional Schism it destroys Arkarium's
# barrier (quest record 20755 = "Complete", which the quest checks). Report back to the knight.
field_id = sm.getFieldID()
if sm.hasQuest(20755) and 272030000 <= field_id <= 272030300:
    if sm.getQRValue(20755) != "Complete":
        sm.setQRValue(20755, "Complete", False)
    sm.sendSayOkay("(The Lambent Essence flares, and Arkarium's barrier shatters.)")
elif sm.hasQuest(20755):
    sm.sendSayOkay("(The barrier is inside the #bDimensional Schism#k. Use the essence there.)")
else:
    sm.sendSayOkay("(A shimmering essence of light.)")
sm.dispose()
