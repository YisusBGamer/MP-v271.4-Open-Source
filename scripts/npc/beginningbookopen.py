# NPC script: beginningbookopen
# NPCs: 2500017 (Book of the Beginning)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Book of the Beginning.")
