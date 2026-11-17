# NPC script: npc_9201662
# NPCs: 9201662 (Libae), 9201663 (Libae)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Libae.")
