# 23968 - Muscat-2000's Thoughtfulness
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Muscat-2000 (2154003)

sm.setSpeakerID(2154003)  # Muscat-2000
if sm.sendNext("I received the transmission sent by #p2154007# before his functions were disrupted. Thank you for helping my siblings. Thanks to you, both of them have been saved. I believe you are what people mean by the word 'kindness'.\r\n#L0##bDid you know that this would happen?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I knew that a day would come when Doctor #p2154009# would abandon #p2154012#. Amongst all of us, #p2154012# was designed specifically for combat. However, that means his energy consumption was the highest. I knew that #p2154012# would be the first to be abandoned.\r\n#L0##bThen why did you make #t4032774#'s heart instead of #p2154012#'s heart?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I am not capable of recreating a #p2154009# type heart perfectly. Had I made #p2154012#'s heart, the output would be insufficient when #p2154012# was called into combat. Since #p2154007# is not designed for combat, the heart that I made should be sufficient.\r\n#L0##b(#p2154003# appears to have put a lot of thought into this.)#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("However, even with #p2154007# using it, the heart I made doesn't have high energy efficiency. Ergo, the energy needs to be charged in advance. Charge the energy while delivering the heart to #p2154007#. I trust that you will be able to help us."):
    sm.startQuest(parentID)
    sm.sendNext("You can fill it by hunting monsters the same way you did with the Energy Cartridge. Please charge the energy to 100% and deliver it to #p2154007#. Thank you.")
else:
    sm.sendNext("#p2154007# was never afraid even as his functions became disrupted. It was possibly because he trusted you. ")
