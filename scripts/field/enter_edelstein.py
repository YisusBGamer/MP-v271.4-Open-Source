# 310000000 (Edelstein) - Explorer story: the little Black Wing (1520014) who helps you near the Black Wing base
# (Enemy Territory 32284, Help from the Resistance 32285)
if sm.hasQuest(32284) or sm.hasQuest(32285) or sm.hasQuestCompleted(32283) and not sm.hasQuestCompleted(32285):
    sm.spawnNpcIfAbsent(1520014, 1700, -14)

# Resistance story (23937-23940, 23970): open to every Resistance job, but its start record 23977 was only set by the
# Demon's job change, and the scenes that set the end records (23984, 23982, 23979) aren't on this server: set them
# here, the start record for any Resistance character and each end record while its quest is open
if chr.getJob() // 1000 == 3 and sm.getQRValue(23977) != "1":
    sm.setQRValue(23977, "1", False)
for quest, record in ((23938, 23984), (23970, 23982), (23940, 23979)):
    if sm.hasQuest(quest) and sm.getQRValue(record) != "1":
        sm.setQRValue(record, "1", False)
