package net.swordie.ms.life.mob.boss.will;

import net.swordie.ms.util.Position;

public class StrikeInfo {
    protected int id;
    protected int delayMs;
    protected Position pos;

    public StrikeInfo(int id, int delayMs, Position pos) {
        this.id = id;
        this.delayMs = delayMs;
        this.pos = pos;
    }
}
