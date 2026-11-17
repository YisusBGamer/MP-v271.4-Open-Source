# Takigawa Kazumasu (9421562), Honnou-ji Temple (807050503-807050505)
# "The Demon's Lair 3" (Hayato 57160, Kanna 57456) is turned in to the beaten Kazumasu (NPC 9130078), who stands in
# no map: he's brought out where he fell.
KAZUMASU_NPC = 9130078
if field.getLifeByTemplateId(KAZUMASU_NPC) is None:
    pos = mob.getPosition()
    sm.spawnNpc(KAZUMASU_NPC, pos.getX(), pos.getY())
for chr in field.getChars():
    if chr.hasQuestInProgress(57160) or chr.hasQuestInProgress(57456):
        chr.chatMessage("Takigawa Kazumasu is beaten. Talk to him.")
