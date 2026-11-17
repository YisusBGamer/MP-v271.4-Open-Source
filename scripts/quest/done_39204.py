# After Thanks for the Save (39204): One-Eye's weekly commissions open (39165 Haven Weekly Mission, 39160 Request
# Reward need start=1 on their records).
if sm.getQRValue(39165, "start") != "1":
    sm.setQRValue(39165, "start", "1")
if sm.getQRValue(39160, "start") != "1":
    sm.setQRValue(39160, "start", "1")
sm.dispose()
