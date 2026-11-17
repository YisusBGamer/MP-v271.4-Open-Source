# 222020000 - Ludi tower: Helios Tower <Library>
# Phantom story, Unwritten Records (25432): the Library Vault, where Wiz is possessed (viewHRpt)
if sm.hasQuest(25432) and not sm.isComplete(25432):
    sm.warpInstanceIn(922030200, 0)
# CoK 3rd job portal
elif not sm.hasQuest(20881) and not sm.hasQuest(20882) and sm.hasQuestCompleted(20883): # 3rd job quest
    sm.chat("Only knights looking to job advance to the third job may enter here.")
else:
    sm.warpInstanceIn(922030400, 0)
