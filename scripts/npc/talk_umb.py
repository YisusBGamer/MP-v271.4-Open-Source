# NPC script: talk_umb
# NPCs: 2131012 (Big Mushroom)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Big Mushroom.")
