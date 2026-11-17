# Tangyoon (1097000) | Cooking with Tangyoon: Tangyoon's Kitchen / Party Quest Entry hub
from net.swordie.ms.world.field.instance.instancehelper.pq import TangyoonInstanceHelper as TY

sm.setSpeakerID(1097000)
selection = sm.sendNext("#e<Party Quest: Cooking with Tangyoon>#n\r\nDo you want to make some delicious dishes for the "
                        "crew of the Nautilus? I can teach you how.\r\n\r\n#b"
                        "#L0#Start Cooking with Tangyoon.#l\r\n"
                        "#L1#Get Tangyoon's Chef Outfit.#l\r\n"
                        "#L2#Listen to the explanation about Cooking with Tangyoon.#l\r\n"
                        "#L3#View remaining attempts for today.#l")

if selection == 0:
    if sm.getFieldID() != TY.KITCHEN:
        sm.sendNext("Cooking is not easy. But if you're still up for it, then follow me.")
        sm.warp(TY.KITCHEN, 0)
    else:
        blocker = TY.getEntryBlocker(chr)
        if blocker is not None:
            sm.sendSayOkay(blocker)
        else:
            TY.recordEntries(chr)
            sm.warpInstanceIn(TY.CAULDRON, 0, True)

elif selection == 1:
    selection = sm.sendNext("A Chef Outfit? Only the most talented cooks can get those.\r\n"
                            "#L10#Give me a Chef Outfit (2 Chef Certificates)#l\r\n"
                            "#L11#Give me a Chef Hat (3 Chef Certificates)#l")
    item, need = (1052578, 2) if selection == 10 else (1003762, 3)
    if sm.hasItem(TY.CHEF_CERTIFICATE, need) and sm.canHold(item):
        sm.consumeItem(TY.CHEF_CERTIFICATE, need)
        sm.giveItem(item)
        sm.sendSayOkay("Wear it with pride!")
    else:
        sm.sendSayOkay("Are you sure you have " + str(need) + " Chef Certificates? If so, make sure your Equip tab "
                       "isn't full. And don't try to trick me! I can smell deceit.")

elif selection == 2:
    sm.sendNext("Welcome to the Nautilus, the finest pirate ship in Maple World! I'm Tangyoon, the head chef in charge "
                "of the galley. These pirates eat so much I need assistants!")
    sm.sendNext("I'll pick a dish. Then for each of #b5 ingredients#k, your party leader picks a monster to hunt for "
                "#b90 seconds#k. Only one of the five is right for the dish! Watch out for #rTasteburners#k at the "
                "fourth ingredient: put them all out or the dish is ruined.")
    sm.sendSayOkay("A good dish brings out #bMr. Chomps#k, a ruined one... the #bTrash Can#k. Defeat it and I'll give "
                   "you Chef Certificates: 2 for a good dish, 1 for a ruined one.\r\n\r\n"
                   "#e-Level#n: 60 or above #r(Recommended Level: 60-90)#k\r\n"
                   "#e-Time Limit#n: 20 minutes\r\n#e-Players#n: 1-3\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Rewards#n:\r\n#v1052578# Tangyoon's Chef Outfit (2 certificates)\r\n"
                   "#v1003762# Tangyoon's Chef Hat (3 certificates)")

elif selection == 3:
    sm.sendSayOkay("You can cook #b" + str(TY.getRemainingEntries(chr)) + "#k more time(s) today.")
