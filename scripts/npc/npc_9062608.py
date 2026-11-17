# NPC script: npc_9062608
# NPCs: 9062608 (Alphard), 9062621 (Athena Pierce), 9062622 (Dances with Balrog), 9062623 (Dark Lord), 9062624 (Kyrin), 9062625 (Grendel the Really Old), 9062626 (Goddess of Maple World)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Alphard.")
