# Field script: 2010MoonWating
# Hooks: onUserEnter
# Maps: 926140100
# Dialogo portado de Quest.wz/Say.img/2010/0 (3 pagina(s))
# Evidencia Quest.wz/Check.img/2010/0: npc 1002100, lvmin 15, job 100/110/120/130/111/121/131/112/122/132/200/210/220/230/211/221/231/212/222/232/300/310/320/311/321/312/322/400/410/420/430/411/421/431/412/422/432/433/434/500/510/520/511/521/512/522/1100/1110/1111/1112/1200/1210/1211/1212/1300/1310/1311/1312/1400/1410/1411/1412/1500/1510/1511/1512/2100/2110/2111/2112/2200/2210/2211/2212/2213/2214/2215/2216/2217/2218/3200/3210/3211/3212/3300/3310/3311/3312/3500/3510/3511/3512
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q2010s).
if not sm.hasHadQuest(2010):
    sm.setSpeakerID(1002100) # Jane
    sm.sendNext("My dream is to travel freely around the world, just like you! But, argh, my dad won't let me. I tell him I'm ready to spread my wings a little, but he just won't listen.")
    sm.sendSay("Hmm. You look pretty strong. Can...can I ask you a favor? Please bring me #e10#n #bWild Boar Teeth#k and #e10#n #b#t4000015#s#k. If you can do this for me, my father might give me permission to travel the world. Please, I'm begging you...")
    sm.sendSay("A #t4000020# looks like #i4000020# and a #t4000015# looks like #i4000015#. You can get them from #o2230102# and #o2110200#. Oooh, I'm so excited about this!")
