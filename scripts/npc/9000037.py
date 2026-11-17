# Agent Meow: the Speed Quiz (the Secret Agent event, see SecretAgentEvent); points scored count even if the quiz is
# closed early.
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_MEOW)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_MEOW)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want to "
                   "become an agent.")
    return False

from java.lang import System

QUESTIONS = 10
SECONDS = 10

if recruited():
    wait = Agent.quizWaitMs(chr)
    if wait > 0:
        sm.sendSayOkay("Meow! The next Speed Quiz starts in #b%d minute%s#k. Every 30 minutes, 10 questions about "
                       "Maple World's items and monsters." % ((wait + 59999) // 60000,
                                                              "" if wait < 60000 else "s"))
    elif sm.sendAskYesNo("Meow! Ready for the #bSpeed Quiz#k? #b%d questions#k about Maple World's items and "
                         "monsters. Answer each one within #b%d seconds#k for a point. (Today's points: %d.)"
                         % (QUESTIONS, SECONDS, int(Agent.record(chr).getOrDefault("qpts", "0")))):
        _agent_action("startQuizRound", chr)
        score = 0
        credited = False
        try:
            for i in range(QUESTIONS):
                q = Agent.randomQuestion()
                text = "#eQuestion %d/%d#n\r\n%s\r\n#b" % (i + 1, QUESTIONS, q.text())
                answers = list(q.answers())
                for j in range(len(answers)):
                    text += "#L%d#%s#l\r\n" % (j, answers[j])
                asked = System.currentTimeMillis()
                pick = sm.sendNext(text)
                late = System.currentTimeMillis() - asked > SECONDS * 1000
                if pick == q.correct() and not late:
                    score += 1
                    sm.sendNext("#gCorrect!#k")
                elif pick == q.correct():
                    sm.sendNext("Right, but too slow! Meow. (More than %d seconds.)" % SECONDS)
                else:
                    sm.sendNext("#rWrong!#k The answer was #b%s#k." % answers[q.correct()])
            _agent_action("finishQuizRound", chr, score)
            credited = True
        finally:
            if not credited:
                _agent_action("finishQuizRound", chr, score)  # closing the quiz early keeps the points scored
        sm.sendSayOkay("That's the quiz! You scored #b%d/%d#k. Meow!" % (score, QUESTIONS))
