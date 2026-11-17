# NPC script: npc_9063664
# NPCs: 9063664 (Bull), 9063665 (Slink)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Bull.")
