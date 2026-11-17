# Maple Tour 'nextstage' portal of every stage except the final one.
# Each region is a chain of six maps spaced 15 apart (Stage 1 x00 ... Final Stage x75);
# the final stage uses mTour_final instead. Map.wz Graph.img marks this portal as a
# script portal (targetMap 999999999), so the next stage is chosen here.
sm.warp(sm.getFieldID() + 15, 0)
sm.dispose()
