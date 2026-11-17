# Field script: q2614M
# Hooks: onUserEnter
# Maps: 910350200
# Dialogo portado de Quest.wz/Say.img/2614/0 (1 pagina(s), rama yes 2, rama no 1)
# Evidencia Quest.wz/Check.img/2614/0: npc 1052103, lvmin 10, job 0/400/430/431/432/433/434/435, req 2613 state 1
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q2614s).
if not sm.hasHadQuest(2614) and sm.hasQuest(2613):
    sm.setSpeakerID(1052103) # Nella
    if sm.sendAskYesNo("That Dark Lord sent you? Perfect timing! EMERGENCY! We need help in the Pharmacy!"):
        sm.sendSayOkay("The Pharmacy is a mess! The old fart, I mean, old dude at the Pharmacy is too busy filling orders to do any cleaning, but I just know the mess is driving him crazy. YOU HAVE TO HELP HIM!")
        sm.sendSayOkay("There are a bunch of #rStirges#k in the basement. Wipe them off the face of Maple World!")
    else:
        sm.sendSayOkay("Didn't the Dark Lord send you to help us?!")
