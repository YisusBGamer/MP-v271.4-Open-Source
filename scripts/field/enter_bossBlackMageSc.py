# 450013810 / 450013830 / 450013850 / 450013870 - variante "Sc" del Mago Oscuro.
#
# El WZ (Map.wz/Map4) define:
#   onFirstUserEnter = "firstenter_bossBlackMageSc"  (spawnea al jefe de la fase)
#   onUserEnter      = "enter_bossBlackMageSc"      (este script)
#
# Ninguno de los dos scripts existia, asi que el server los creaba vacios al
# vuelo: la sala se quedaba sin limite de tiempo y sin barra de vida del jefe.

from net.swordie.ms.constants import BossConstants

TIME = 30 * 60

# Las caras del Mago Oscuro (mismos ids que la version normal, ver BossConstants)
AEONIAN_RISE = 8880500
TANADIAN_RUIN = 8880501
THRONE = 8880502        # BLACK_MAGE_PHASE_1
FACE_2 = 8880503        # BLACK_MAGE_FACE_2
FACE_3 = 8880504        # BLACK_MAGE_FACE_3

fieldId = sm.getFieldID()

sm.setInstanceTime(TIME)

if field.getLifeByTemplateId(AEONIAN_RISE) is not None:
    sm.showHP(AEONIAN_RISE)
elif field.getLifeByTemplateId(TANADIAN_RUIN) is not None:
    sm.showHP(TANADIAN_RUIN)
elif field.getLifeByTemplateId(THRONE) is not None:
    sm.showHP(THRONE)
elif field.getLifeByTemplateId(FACE_2) is not None:
    sm.showHP(FACE_2)
elif field.getLifeByTemplateId(FACE_3) is not None:
    sm.showHP(FACE_3)
