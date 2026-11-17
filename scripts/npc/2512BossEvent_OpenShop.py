# NPC script: 2512BossEvent_OpenShop
# NPCs: 9063883 (Basel), 9063885 (Basel)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Basel.")
