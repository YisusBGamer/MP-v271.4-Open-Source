# NPC script: cmdbookopen
# NPCs: 2500021 (Book of the Commander)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Book of the Commander.")
