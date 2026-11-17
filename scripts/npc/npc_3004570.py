# NPC script: npc_3004570
# NPCs: 3004570 (Bull), 3004571 (Slink)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Bull.")
