# Death script of the Tiguru of Exam (9001011), set by portal/enterSecondDH.py and the Chief Knights' NPC scripts:
# each kill in the Second Drill Hall counts for the Knighthood Exam (20871-20875); the 30th gives that exam's Proof of
# Exam (each Knighthood has its own).
EXAMS = {20871: 4036867, 20872: 4036883, 20873: 4036884, 20874: 4036885, 20875: 4036886}
KILLS = 30

for c in list(field.getChars()):
    csm = c.getScriptManager()
    exam = 0
    for q in sorted(EXAMS):
        if c.hasQuestInProgress(q):
            exam = q
            break
    if exam == 0 or csm.hasItem(EXAMS[exam]):
        continue
    raw = csm.getQRValue(exam, "kill")
    done = int(raw) + 1 if raw and raw.isdigit() else 1
    csm.setQRValue(exam, "kill", str(done))
    if done >= KILLS:
        csm.giveItem(EXAMS[exam])
        c.chatMessage("You defeated 30 Tigurus of Exam and earned the Proof of Exam. Report to your Chief Knight.")
