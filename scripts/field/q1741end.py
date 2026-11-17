# Field script: q1741end
# Hooks: onUserEnter
# Maps: 956040100, 956040101, 956040102, 956040103, 956040104, 956040105, 956040106, 956040107, ... (10 total)
# Dialogo portado de Quest.wz/Say.img/1741/0 (2 pagina(s))
# Evidencia Quest.wz/Check.img/1741/0: lvmin 50, req 1740 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q1741s).
if not sm.hasHadQuest(1741) and sm.hasQuestCompleted(1740):
    sm.sendNext("This place looks like El Nath. But how did I end up here? All I did was touch #p9074200#'s painting--OH CRAP. Am I stuck in a painting?! I'm gonna kill #p9074200# when I see him!")
    sm.sendSay("First, I've gotta get out of this valley before I freeze. Then, I find #p9074200# and pull that stupid hat down around his ears.")
