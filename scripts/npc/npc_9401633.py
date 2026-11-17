# NPC script: npc_9401633
# NPCs: 9401633 (GC_Showcase_4_dummy)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am GC_Showcase_4_dummy.")
