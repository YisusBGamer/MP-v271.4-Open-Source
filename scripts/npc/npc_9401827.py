# NPC script: npc_9401827
# NPCs: 9401827 (Penni), 9401855 (Penni), 9401856 (Penni), 9401862 (Penni)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Penni.")
