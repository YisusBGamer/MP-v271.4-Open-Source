# Field script: zeroProl1000
# Hooks: onUserEnter
# Maps: 321001000
# Dialogo portado de Quest.wz/Say.img/1000/0 (4 pagina(s), rama yes 1, rama no 1)
# Evidencia Quest.wz/Check.img/1000/0: npc 2101, job 0
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q1000s).
if not sm.hasHadQuest(1000):
    sm.setSpeakerID(2101) # Heena
    sm.sendNext("You must be the new traveler. I'll be giving you important information so listen up. First, if you want to talk to anyone, #bclick#k the person with your mouse.")
    sm.sendSay("The #bleft and right arrows#k allow you to move. Press #bAlt#k to jump. Jump diagonally by pressing both together.")
    sm.sendSay("Man, it's scorching hot today! The sun is burning my beautiful skin! Can you get me a #bmirror#k from #r#p2100##k, please? I have to check my skin!")
    if sm.sendAskYesNo("If you want to check your progress in a quest, open the #bQuest Window#k by pressing the #bQ key#k, then press #bQuest Helper#k. You'll be able to view the progress of quests registered to the Quest Helper."):
        sm.sendSayOkay("Thank you. #r#p2100##k is on the hill to the east, hanging laundry. The mirror looks like this #i4031003#.")
    else:
        sm.sendSayOkay("Don't want to? Fine, come back when you change your mind.")
