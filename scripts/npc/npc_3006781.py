# NPC script: npc_3006781
# NPCs: 3006781 (Ailene), 3006793 (Ailene)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Ailene.")
