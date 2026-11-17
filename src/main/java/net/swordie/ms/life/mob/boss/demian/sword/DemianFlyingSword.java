package net.swordie.ms.life.mob.boss.demian.sword;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.DemianFieldPacket;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;

/**
 * Created on 17-8-2019.
 *
 * @author Asura
 */
public class DemianFlyingSword extends Life {

    private int targetChrId;
    private DemianFlyingSwordPath demianFlyingSwordPath;
    private DemianFlyingSwordType swordType;
    private Mob owner; // MobOwner (Demian)
    private volatile int pendingEnterCharId; // asked another client where the sword is, for this newcomer

    public DemianFlyingSword(int templateId) {
        super(templateId);
        this.demianFlyingSwordPath = DemianFlyingSwordPath.flyingSwordCreationPath();
        this.swordType = DemianFlyingSwordType.MainSword;
        setPosition(DemianFlyingSwordPath.creationPosition);
    }

    public static DemianFlyingSword createDemianFlyingSword(Char target, Mob owner) {
        DemianFlyingSword sword = new DemianFlyingSword(-1);

        sword.setTargetChrId(target.getId());
        sword.setField(target.getField());
        sword.setOwner(owner);

        return sword;
    }

    public int getPendingEnterCharId() {
        return pendingEnterCharId;
    }

    public int getTargetChrId() {
        return targetChrId;
    }

    public void setTargetChrId(int targetChrId) {
        this.targetChrId = targetChrId;
    }

    public DemianFlyingSwordPath getDemianFlyingSwordPath() {
        return demianFlyingSwordPath;
    }

    public void setDemianFlyingSwordPath(DemianFlyingSwordPath demianFlyingSwordPath) {
        this.demianFlyingSwordPath = demianFlyingSwordPath;
    }

    public Mob getOwner() {
        return owner;
    }

    public void setOwner(Mob owner) {
        this.owner = owner;
    }

    public DemianFlyingSwordType getSwordType() {
        return swordType;
    }

    public void setSwordType(DemianFlyingSwordType swordType) {
        this.swordType = swordType;
    }

    public void startPath() {
        getField().broadcastPacket(DemianFieldPacket.flyingSwordNode(this));
    }

    public void target() {
        getField().broadcastPacket(DemianFieldPacket.flyingSwordTarget(this));
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar, EnterType enterType) {
        Field field = getField();
        if (onlyChar == null) {
            field.broadcastPacket(DemianFieldPacket.flyingSwordCreate(this));
        } else {
            // someone who comes in mid-fight: ask a player already here where the sword is (DEMIAN_OBJECT_MAKE_ENTER_ACK
            // answers, and the newcomer gets it at that point of its flight); alone, it is just created
            Char other = field.getChars().stream()
                    .filter(c -> c != onlyChar && !net.swordie.ms.ext.ServerHooks.isAutomated(c))
                    .findFirst().orElse(null);
            if (other != null) {
                pendingEnterCharId = onlyChar.getId();
                other.write(DemianFieldPacket.flyingSwordMakeEnterRequest(this, onlyChar.getId()));
            } else {
                onlyChar.write(DemianFieldPacket.flyingSwordCreate(this));
            }
        }
    }
}
