# NPC script: npc_9401637
# NPCs: 9401637 (GC_Showcase_8_dummy)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am GC_Showcase_8_dummy.")
