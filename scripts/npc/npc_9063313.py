# Wandering Blacksmith (9063313): Exceptional Enhancement guide
sel = sm.sendNext("...Is it power you want?\r\n\r\n#b"
                  "#L0#What is Exceptional Enhancement?#l\r\n"
                  "#L1#Which items can be enhanced?#l\r\n"
                  "#L2#Where do I get Exceptional Hammers?#l")
if sel == 0:
    sm.sendNext("Some boss accessories hide more power than they show. An #bExceptional Hammer#k draws it out.\r\n\r\n"
                "Open your inventory, press #bEnhance#k and choose the #bExceptional#k tab. "
                "The item must be #buntradable#k.\r\n\r\n"
                "#e1st enhancement:#n 100%, no mesos.\r\n"
                "#e2nd:#n 1 hammer 50% (2.5b mesos), 2 hammers 100% (5b mesos).\r\n"
                "#e3rd:#n 1 hammer 25% (5b mesos), 2 hammers 50% (10b mesos).\r\n"
                "A failed try uses up the hammers but the item is not hurt.")
    sm.sendSayOkay("Changed your mind? #bExtract#k takes one enhancement back out as a hammer, for 10b mesos. "
                   "Keep a free slot in your Use tab.")
elif sel == 1:
    sm.sendSayOkay("#e1 enhancement#n: #i1132308# #t1132308#, #i1012632# #t1012632#, "
                   "#i1022278# #t1022278#, #i1032316# #t1032316#.\r\n"
                   "Each adds #bAll Stats +20, ATT/M.ATT +15, Max HP/MP +1,000#k (face and eye: +15, +10, +750).\r\n\r\n"
                   "#e3 enhancements#n: #i1143471# #t1143471#, All Stats +20, ATT/M.ATT +15, Max HP/MP +1,000 each.")
elif sel == 2:
    sm.sendSayOkay("Each hammer fits one item:\r\n"
                   "#i2644204# #t2644204#\r\n#i2644205# #t2644205#\r\n#i2644206# #t2644206#\r\n"
                   "#i2644207# #t2644207#\r\n#i2644208# #t2644208#\r\n\r\n"
                   "Extreme bosses drop them: Kalos the Guardian the Eye hammer, Kaling the Earrings hammer, "
                   "the First Adversary the Medal hammer. Old Exceptional Parts "
                   "(#t2644200# and the like) still give the first enhancement.")
