# Agent C: Intelligence Documents (also in the party quests' bonus rooms) (the Secret Agent event, see SecretAgentEvent)
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_C)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_C)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    if sm.getFieldID() == HQ:
        sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want "
                       "to become an agent.")
    else:  # Agent C also works out in the party quests
        sm.sendSayOkay("...Shh. I'm on a secret mission. If you want to help hunt down #e#rMaster M#k#n, talk to "
                       "#bAgent E#k in any major town.")
    return False

if recruited():
    Agent.retryPointRewards(chr)  # the receiver, if it's due and didn't fit before
    n = sm.getQuantityOfItem(Agent.DOCUMENT)
    total = int(Agent.record(chr).getOrDefault("docs", "0"))
    if n > 0:
        taken = _agent_action("turnInDocuments", chr)
        if taken > 0:
            sm.sendSayOkay("%d Intelligence Document%s. Everything is in order. Every #b5#k documents earn you "
                           "#bAgent C's Old Receiver#k (7 days), and #b30#k in all earn #bAgent C's Titanium Receiver#k. "
                           "(%d delivered so far.)" % (taken, "" if taken == 1 else "s", total + taken))
        else:
            sm.sendSayOkay("Make some room in your Equip inventory first.")
    else:
        sm.sendSayOkay("I handle intelligence. The monsters in the #bparty quests#k are #e#rMaster M's#k#n "
                       "subordinates, and his spies hide among monsters near your level everywhere else: investigate "
                       "them and bring me #bIntelligence Documents#k #i4001192#. They turn up most in party quests. "
                       "Every #b5#k earn #bAgent C's Old Receiver#k (7 days), #b30#k in all earn #bAgent C's "
                       "Titanium Receiver#k. (%d delivered so far.)" % total)
