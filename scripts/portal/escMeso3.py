# M-Forcer Operations HQ exit (the Secret Agent event's HQ): back to the town the agent came from.
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.warp(Agent.returnTown(chr))
sm.dispose()
