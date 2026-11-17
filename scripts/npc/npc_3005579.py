# NPC script: npc_3005579
# NPCs: 3005579 (Abandoned Worktable)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Abandoned Worktable.")
