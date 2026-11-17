# 20533 - The Birth of Shinjo
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Shinsoo (1101001)

sm.setSpeakerID(1101001)  # Shinsoo
if sm.sendNext("#t1902006# you have with you is an illusory creature I summoned. You are the one who gave your Mount the strength to exist in this world, but it has weakened over time and exhausted its strength by participating in your battles. It may be time to let #t1902006# go.\r\n#b#L0#Isn't there a way I can be with my Mount?#l\r\n#L1#I will let my Mount go.#l\r\n#k") != 0:
    sm.sendSayOkay("Don't you have any regrets?")
    sm.dispose()
if sm.sendSay("With the power of #t1902006#, you have become incredibly strong. Even if you kept #t1902006# in this world, it will not perform as it had in the past. It will only burden you with extra responsibilities. Would you still like to keep #t1902006#?\r\n#b#L0#Then I do not wish to keep #t1902006# anymore.#l\r\n#L1#I still want to be with my Mount.#l\r\n#k") != 1:
    sm.sendSayOkay("You're extremely cold-hearted.")
    sm.dispose()
if sm.sendSay("#t1902006# has become exhausted over time and is suffering from fatigue. You may be happy to have a companion by you, but dragging #t1902006# around may only exacerbate its condition. #t1902006# is in desperate need of rest, but you still wish to keep your Mount in this world?\r\n#b#L0#If #t1902006# is suffering from pain, I'll let my Mount go.#l\r\n#L1#I still wish to be with my Mount.#l\r\n#k") != 0:
    sm.sendSayOkay("Must you do something Mimio really does not want?")
    sm.dispose()
sm.sendSay("If you say so. I will send #t1902006# back to where it belongs.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I've sent your Mount back once, but it has come back on its own... #t1902006# seems to want to stay by you.")
    sm.sendSay("Your sincere request and your #t1902006#'s strong will combined have recreated #t1902006#. #bShinjo#k is its name. You don't need my help any more. You two will be just fine.")
    sm.sendSay("Congratulations on acquiring an eternal companion.")
