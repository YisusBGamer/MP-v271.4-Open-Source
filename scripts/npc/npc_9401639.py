# NPC script: npc_9401639
# NPCs: 9401639 (GC_Showcase_10_dummy)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am GC_Showcase_10_dummy.")
