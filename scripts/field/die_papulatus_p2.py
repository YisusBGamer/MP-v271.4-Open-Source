# Papulatus' last phase falls (8500002 Easy, 8500012 Normal, 8500022 Chaos): the alarm, tweezers and lasers stop
tid = {220080100: 8500002, 220080200: 8500012}.get(sm.getFieldID(), 8500022)
sm.endPapulatusFight(tid)
