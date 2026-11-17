# Mirror-touched Sea 7 (450007160) -> Slumbering Deep (450007170). v265 opens the barrier once the Executor has been
# strengthened ([Esfera] Her Barrier, 37865); 34578 is the old story's quest (blocked in v265).
if sm.hasQuestCompleted(37865) or sm.hasQuestCompleted(34578):
    sm.warp(450007170)
else:
    sm.chat("A barrier is blocking you.")
