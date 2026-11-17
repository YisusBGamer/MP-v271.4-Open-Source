# NPC 9000260's Npc.wz script name is "mRhythm3_leave", so this file runs when it is clicked instead of 9000260.py.
# Keep the two the same.
# Dr. Kim (M-Forcer Operations HQ: the Secret Agent event's HQ): the way out.
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(9000260)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(9000260)
if sm.sendAskYesNo("Leaving the HQ, agent? I'll send you back to town."):
    sm.warp(Agent.returnTown(chr))
