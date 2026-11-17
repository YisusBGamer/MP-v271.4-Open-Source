# Pink Bean (P2) - el script estaba VACIO (0 bytes, la plantilla autogenerada):
# la sala se quedaba sin limite de tiempo ni barra de vida del jefe.

from net.swordie.ms.constants import BossConstants

chaos = field.getId() == BossConstants.CHAOS_PINK_BEAN_FIELD

sm.setInstanceTime(BossConstants.PINK_BEAN_TIME)  # 30 min
if chaos:
    sm.showHP(BossConstants.CHAOS_PINK_BEAN_P2)
else:
    sm.showHP(BossConstants.PINK_BEAN_P2)
