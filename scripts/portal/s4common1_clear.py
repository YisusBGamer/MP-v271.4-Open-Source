# Portal script s4common1_clear
# Lo invoca el atributo script del portal 3 del mapa 921100300 (Map.wz/Map).
# El WZ declara el destino 921100301: la unica arista portalNum=3 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(921100301)
