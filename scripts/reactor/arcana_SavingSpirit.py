# Freed Rock Spirit (3600001) in Spirit Savior (921172300): the harvest key takes it along
instance = chr.getInstance()
helper = instance.getInstanceHelper() if instance is not None else None
if helper is not None and hasattr(helper, "rescue"):
    helper.rescue(chr, reactor)
sm.dispose()
