# NPC script: npc_9201855
# NPCs: 9201855 (Frill), 9201866 (Frill)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Frill.")
