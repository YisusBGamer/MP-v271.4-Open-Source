# NPC script: npc_9401826
# NPCs: 9401826 (Penni), 9401849 (Penni), 9401850 (Penni), 9401851 (Penni), 9401852 (Penni), 9401853 (Penni), 9401854 (Penni)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Penni.")
