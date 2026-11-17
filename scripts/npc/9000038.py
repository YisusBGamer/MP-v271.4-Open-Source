# Agent Kitty: the Special Agent Enrichment Program (the Secret Agent event, see SecretAgentEvent)
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_KITTY)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_KITTY)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want to "
                   "become an agent.")
    return False

if recruited():
    kills = Agent.enrichmentKills(chr)
    if kills < 0:
        if sm.sendAskYesNo("I'm #bAgent Kitty#k. The #bSpecial Agent Enrichment Program#k is training in the "
                           "field: defeat #b%d monsters#k near your level today for 20 Agent Points. Start today's "
                           "program?" % Agent.ENRICH_KILLS):
            _agent_action("startEnrichment", chr)
            sm.sendSayOkay("Off you go! I'll keep count.")
    elif kills >= Agent.ENRICH_KILLS:
        sm.sendSayOkay("You've finished today's Enrichment Program. Come back tomorrow!")
    else:
        sm.sendSayOkay("Enrichment Program: #b%d/%d#k monsters near your level today." % (kills, Agent.ENRICH_KILLS))
