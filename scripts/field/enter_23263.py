# Field script: enter_23263
# Hooks: onUserEnter
# Maps: 931050210
# Dialogo portado de Quest.wz/Say.img/23263/0 (4 pagina(s), rama yes 1, rama no 1, ask=1)
# Evidencia Quest.wz/Check.img/23263/0: npc 2151009, lvmin 70, normalAutoStart, job 3110/3120/3111/3121/3112/3122/3113/3123
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q23263s).
if not sm.hasHadQuest(23263):
    sm.setSpeakerID(2151009) # Mastema
    sm.sendNext("#h0#! #h0#! I've learned something very important for us!\r\n#L0##bDid you find out who is leading the Black Wings?#l\n#k")
    sm.sendSay("No, I found a way to turn back to my original form!\r\n#L0##bWhat.#l\n#k")
    sm.sendSay("What's with that look? Anyway, rumor has it that braiding the tail furs of #b#o5120001#, #o5120002#, #o5120003##k and placing the braid it under your pillow when you sleep will change your appearance. Those monsters reside in #m200000000#.\r\n#L0##bAre you feeling okay, #p2151009#? 'Cause that is the dumbest-#l\n#k")
    if sm.sendAskYesNo("Um, because it's probably true? I mean, yeah, there's a tiny, tiny, TINY chance that this random rumor about a magical replica ray gun is not entirely, 100% true... But it's still worth a try! Right, o kind and wise master?"):
        sm.sendSayOkay("Look, #b#o5120001#, #o5120002#, and #o5120003##k appear in the #b#m200000000# Garden#k. Bring me 30 of each monster's #rTail Fur#k. We gotta try it, right?\r\n")
    else:
        sm.sendSayOkay("Please? This is very important for me! Pleeeeeease?")
