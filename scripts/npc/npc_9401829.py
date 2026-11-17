# NPC script: npc_9401829
# NPCs: 9401829 (Paeon), 9401857 (Paeon), 9401858 (Paeon)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Paeon.")
