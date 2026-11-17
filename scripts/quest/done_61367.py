# Masteria Epic (Lost Hero's Call, 61336): the quests follow one another with their own Quest.wz text once you
# reach the Phantom Forest / Crimsonwood Mountain. Data: masgen/gen_mas.py.
# After Dr. Yoon's Question (61367): the next part of the story.

CHAIN = [61350, 61351, 61352, 61353, 61354, 61355, 61356, 61357, 61358, 61359, 61361, 61360, 61362, 61363, 61364, 61365, 61366, 61367]
PRE = {61350: (61336,), 61351: (61350,), 61352: (61351,), 61353: (61352,), 61354: (61353,), 61355: (61354,), 61356: (61355,), 61357: (61355,), 61358: (61357,), 61359: (61358,), 61361: (61359,), 61360: (61361,), 61362: (61360,), 61363: (61362,), 61364: (61363,), 61365: (61364,), 61366: (61365,), 61367: (61366,)}
TEXT = {61350: 'Protect the lost hero from the monsters.', 61351: 'Talk to the strange scientist you met in the Phantom Forest.', 61352: "Use the Subconscious Reading Device to get inside Lukan's memories.", 61353: 'What is the password for Subconscious Reading Device?', 61354: 'Examine Crimsonheart, who lies still on the ground.', 61355: "You have gone through all of Lukan's memories.  Talk to Foxwit.", 61356: 'You have met Crimsonheart and his Grandmasters, 1,000 years in the past. Talk to them.', 61357: 'Dr. Jang has sent you to Versal. Learn more about the enormous ore in front of you.', 61358: 'Defeat the monsters so you can move on with the story.', 61359: 'Continue exploring the story.', 61361: "What was Crimsonheart's decision about Naricain and the Antellion? Find out what happened.", 61360: "Defeat Dr. Jang's Ultimate Weapon.", 61362: 'Hear what Crimsonheart tells Numenal.', 61363: 'Hear what Crimsonheart tells Delgrund.', 61364: 'Crimsonheart intends to stay behind in Masteria to destroy the Antellion. Say farewell to him.', 61365: 'You are back to the present. Talk to Lukan.', 61366: 'Someone called Dr. Yoon has seen your name in an ancient book found in Crimsonwood Keep... How is that possible?', 61367: 'Talk to Dr. Yoon.'}


def advance():
    # the next quest of the chain whose quests before it are done: start it and finish it (it asks for nothing and
    # has no NPC to report to); quest/done_<that quest> then moves on to the one after
    for q in CHAIN:
        if sm.hasQuestCompleted(q):
            continue
        if not all(sm.hasQuestCompleted(p) for p in PRE[q]):
            continue
        if not sm.hasQuest(q):
            sm.startQuest(q)
        if sm.hasQuest(q):
            sm.chatScript("[Masteria Epic] " + TEXT[q])
            sm.completeQuest(q)
        return

advance()
sm.dispose()
