# NPC script: npc_9062867
# NPCs: 9062867 (Magic Slate)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Magic Slate.")
