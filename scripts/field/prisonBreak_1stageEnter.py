# Escape party quest, Hidden Tower: the stage's instructions for everyone entering
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.enums import WeatherEffNoticeType
chr.write(WvsContext.weatherEffectNotice(WeatherEffNoticeType.EscapePQ,
          "Sshh! Climb the tower quietly and avoid the spears. One of you reaching the top is enough.", 7000))
sm.dispose()
