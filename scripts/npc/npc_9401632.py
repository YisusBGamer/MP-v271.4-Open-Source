# NPC script: npc_9401632
# NPCs: 9401632 (GC_Showcase_3_dummy)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am GC_Showcase_3_dummy.")
