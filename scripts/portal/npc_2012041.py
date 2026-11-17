# Portal script npc_2012041
# Map 941000001 portal 1 (pt_out)
# Map 941000100 portal 2 (pt_enter)
# The client requests this script but Map.wz gives the portal no target (tm):
# the original script decided the destination itself and its body is not in the data.
# Documented no-op: warping anywhere would be invented.
sm.dispose()
