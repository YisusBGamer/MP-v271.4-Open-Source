# Field script: enter_23264
# Hooks: onUserEnter
# Maps: 931050211
# Dialogo portado de Quest.wz/Say.img/23264/0 (3 pagina(s), rama yes 1, rama no 1, ask=1)
# Evidencia Quest.wz/Check.img/23264/0: npc 2151009, lvmin 90, normalAutoStart, job 3110/3120/3111/3121/3112/3122/3113/3123, req 23263 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q23264s).
if not sm.hasHadQuest(23264) and sm.hasQuestCompleted(23263):
    sm.setSpeakerID(2151009) # Mastema
    sm.sendNext("#h0#, I finally found it! The way to turn myself back!\r\n#L0##bStill haven't given up, eh?#l\n#k")
    sm.sendSay("Of course! I am #p2151009#, and I never give up! The tail braid last time was a complete failure though... I think I'm allergic to them, actually. I was sneezing all night long.\r\n#L0##bThat's too bad... Wait, allergic to fur?#l\n#k")
    if sm.sendAskYesNo("Anyway! This time it's for real. I hear there is an alchemy potion that transforms you to the #blook of your choice#k. This has to be my chance to turn back to my old self. Can you help me get the key ingredient, #h0#?"):
        sm.sendSayOkay("I need you to go to #m261000000#, the capital of alchemy, and get a #r#t4000419##k from #r#o9800045##k in the #bZenumist #m261010003##k.")
    else:
        sm.sendSayOkay("Hey, I would do it myself if I was in my original form. But then, I wouldn't NEED to do it in the first place, would I? See, that's the thing...if you help me do this, I can help YOU even more! Everybody wins!")
