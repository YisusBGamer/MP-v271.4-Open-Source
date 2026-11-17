# 450013810 / 450013830 / 450013850 / 450013870 - primera entrada de la variante
# "Sc" del Mago Oscuro (spawnea al jefe de la fase).
#
# El WZ (Map.wz/Map4) define onFirstUserEnter = "firstenter_bossBlackMageSc" y el
# script no existia. Sin el, el jefe no aparecia en la sala.

# Las caras del Mago Oscuro (ver BossConstants)
AEONIAN_RISE = 8880500
TANADIAN_RUIN = 8880501
THRONE = 8880502        # BLACK_MAGE_PHASE_1
FACE_2 = 8880503        # BLACK_MAGE_FACE_2
FACE_3 = 8880504        # BLACK_MAGE_FACE_3

fieldId = field.getId()

if fieldId == 450013810:
    # Fase 1: dos ruinas (como la version normal)
    sm.spawnMob(AEONIAN_RISE, -942, 85, False, 32500000000000) # 32.5t
    sm.spawnMob(TANADIAN_RUIN, 942, 85, False, 32500000000000) # 32.5t
elif fieldId == 450013830:
    sm.spawnMob(THRONE, 0, 88, False, 135000000000000) # 135t
elif fieldId == 450013850:
    sm.spawnMob(FACE_2, 375, 88, False, 200000000000000) # 200t
elif fieldId == 450013870:
    sm.spawnMob(FACE_3, 375, 88, False, 100000000000000) # 100t
