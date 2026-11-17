# Portal script exit_9110049000
# Lo invoca el atributo script del portal 18 del mapa 911004900 (Map.wz/Map).
# El WZ declara el destino 911000000: la unica arista portalNum=18 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(911000000)
