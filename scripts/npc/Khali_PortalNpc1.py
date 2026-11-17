# NPC script: Khali_PortalNpc1
# NPCs: 3006230 (Shalala), 3006231 (Shalala)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Shalala.")
