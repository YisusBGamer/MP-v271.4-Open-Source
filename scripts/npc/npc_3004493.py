# NPC script: npc_3004493
# NPCs: 3004493 (Priest's Medallion)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Priest's Medallion.")
