# Portal script BPReturn_vellud
# Lo invoca el atributo script del portal 1 del mapa 863010000 (Map.wz/Map).
# El WZ declara el destino 863000100: la unica arista portalNum=1 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(863000100)
