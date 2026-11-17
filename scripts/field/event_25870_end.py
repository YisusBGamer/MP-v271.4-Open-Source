# Field script: event_25870_end
# Hooks: onUserEnter
# Maps: 940010090
# Dialogo portado de Quest.wz/Say.img/25870/1 (2 pagina(s), rama yes 1, rama no 0)
# Evidencia Quest.wz/Check.img/25870/1: sin condiciones
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q25870e).
# El slot tiene ademas 1 linea(s) en stop/: recordatorios del cliente al volver sin terminar, sin disparador equivalente en un hook de mapa; se documentan y no se suenan aqui.
if sm.hasQuest(25870):
    sm.setSpeakerID(3000000) # Cartalion
    sm.sendNext("Owww....")
    if sm.sendAskAccept("How could Magnus be so powerful with those injuries? Kaiser and Velderoth couldn’t do any more than me. Magnus wasn’t supposed to come in person!"):
        sm.sendSayOkay("I hope Kaiser and Veldie are okay...")
