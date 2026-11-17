# Dello (2400001, Npc.wz script zeroPro) | Zero's Temple (320000000), training
# Who's the Traitor? (40504): each of the temple's 7 priests is looked over once for a spider mark (record 40514,
# count=<priests checked>; <npc id>=1 per priest so nobody counts twice)
def inspect_for_spider(npc, line):
    if not sm.hasQuest(40504) or sm.getQRValue(40514, str(npc)) == "1":
        return False
    count = sm.getQRValue(40514, "count")
    count = int(count) if count is not None and count.isdigit() else 0
    if count >= 7:
        return False  # already done
    count += 1
    sm.setQRValue(40514, str(npc), "1")
    sm.setQRValue(40514, "count", str(count))
    sm.setSpeakerID(npc)
    sm.sendSayOkay(line)
    if count >= 7:
        sm.chatScript("You've looked over all seven priests. Report to Zero.")
    return True


if not inspect_for_spider(2400001, "#b(No spider-shaped mark on Dello.)#k"):
    sm.setSpeakerID(2400001)
    sm.sendSayOkay("I'll tell you everything I know.")
