# 28620 - Can You Resist?
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Has it been 30 minutes already? That felt fast. So...are you going to give the Golden Statue back? I know you're hesitant...it IS such a beautiful and desirable statue. But, you have to return it now. Can you do it?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Wow, I'm impressed! You were able to overcome your powerful desires to run off with the statue, I guess. I don't think I could have done that... But never mind about that. You did great, and your amazing self-control deserves a reward! Here, this is for you.\r\n\r\nYou're welcome to challenge yourself again anytime. Just let me know! Me and my statue will be waiting...")
