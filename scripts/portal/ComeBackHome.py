# Maple Leaf High School / Sengoku High: back to the town Schrodinger sent you in from (Perion if none is kept)
back = sm.getReturnField()
sm.warp(back if back > 0 and back != sm.getFieldID() else 102000000, 0)
