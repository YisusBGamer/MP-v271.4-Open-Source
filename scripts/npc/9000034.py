# Agent O: Special Agent Badges (the Secret Agent event, see SecretAgentEvent)
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_O)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_O)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    if sm.getFieldID() == HQ:
        sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want "
                       "to become an agent.")
    else:  # Agent O also works out in the dungeons
        sm.sendSayOkay("...Shh. I'm on a secret mission. If you want to help hunt down #e#rMaster M#k#n, talk to "
                       "#bAgent E#k in any major town.")
    return False

if recruited():
    Agent.retryPointRewards(chr)  # the gloves, if they're due and didn't fit before
    n = sm.getQuantityOfItem(Agent.BADGE)
    total = int(Agent.record(chr).getOrDefault("badges", "0"))
    if n > 0:
        taken = _agent_action("turnInBadges", chr)
        if taken > 0:
            sm.sendSayOkay("%d Special Agent Badge%s. Not bad, rookie. Every #b10#k badges earn you #bAgent O's "
                           "Nylon Gloves#k (7 days), and #b30#k in all earn my favorite #bLeather Gloves#k. (%d delivered "
                           "so far.)" % (taken, "" if taken == 1 else "s", total + taken))
        else:
            sm.sendSayOkay("Make some room in your Equip inventory first.")
    else:
        sm.sendSayOkay("...Special agents everywhere upload their intel onto badges. Monsters near your level have "
                       "been grabbing them. Bring me #bSpecial Agent Badges#k #i4031988#: every #b10#k earn #bAgent "
                       "O's Nylon Gloves#k, #b30#k in all earn #bAgent O's Leather Gloves#k. (%d delivered so far.)" % total)
