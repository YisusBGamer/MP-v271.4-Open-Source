# Chumji's watermelon (the Secret Agent event): one watermelon a run, then back to the HQ.
from net.swordie.ms.world import SecretAgentEvent as Agent

if Agent.pickWatermelon(chr):
    sm.chatScript("You carefully pick one of Chumji's watermelons. Back to Agent W!")
    sm.warp(Agent.HQ)
elif not Agent.isAgent(chr):
    sm.chatScript("Chumji's watermelons. Better not touch them.")
elif Agent.melonsLeftToday(chr) <= 0:
    sm.chatScript("That's enough watermelons for today.")
    sm.warp(Agent.HQ)
else:
    sm.chatScript("Make some room in your Etc inventory first.")
sm.dispose()
