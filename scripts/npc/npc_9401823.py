# NPC script: npc_9401823
# NPCs: 9401823 (Pan), 9401841 (Pan), 9401842 (Pan), 9401843 (Pan), 9401844 (Pan), 9401845 (Pan), 9401846 (Pan), 9401847 (Pan), 9401863 (Pan)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Pan.")
