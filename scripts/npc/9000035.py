# Agent P: briefing and the agent's status board (the Secret Agent event, see SecretAgentEvent)
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_P)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_P)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want to "
                   "become an agent.")
    return False

if recruited():
    sm.sendNext("#eBriefing: the hunt for Master M#n\r\n\r\n"
                "#bAgent E#k - Today's Mission, special operations, Agent Points.\r\n"
                "#bAgent W#k - Chumji's watermelon field: Pink Flower Tubes.\r\n"
                "#bAgent O#k - Special Agent Badges: Agent O's Gloves.\r\n"
                "#bAgent C#k - Intelligence Documents from party quests: Agent C's Receivers.\r\n"
                "#bAgent Meow#k - the Speed Quiz, every 30 minutes.\r\n"
                "#bAgent Kitty#k - the Enrichment Program, once a day.\r\n\r\n"
                "Agent Points earn the #bAgent Suits#k, the #bAgent Cape#k and #bAgent N's Receivers#k.")
    sm.sendSayOkay("#eYour file, Agent #h0##n\r\n\r\n" + _agent_action("statusBoard", chr))
