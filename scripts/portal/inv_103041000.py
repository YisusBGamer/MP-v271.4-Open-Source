# Portal script inv_103041000.
# Kerning Tower Ground Floor, hidden door (Kerning Tower story):
#   Day One as a Casting Manager (34439): the lobby where the street performer is (the visit is recorded).
#   Day of the Debut / The Spirit of Rock (34423-34424): the live performance stage.
#   After the Debut / Co-CEO (34425, 34432): Blake's penthouse.
if sm.hasQuest(34439):
    sm.setQRValue(34437, "c1", "1")
    sm.warp(910310502, 0)
elif sm.hasQuest(34423) or sm.hasQuest(34424):
    sm.warp(910310510, 1)
elif sm.hasQuest(34425) or sm.hasQuest(34432) or sm.hasQuestCompleted(34424):
    sm.warp(103041004, 1)
else:
    chr.chatMessage("Staff only.")
sm.dispose()
