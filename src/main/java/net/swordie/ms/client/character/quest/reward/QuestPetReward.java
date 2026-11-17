package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.PetItem;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.InventoryOperation;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.life.pet.PetSkill;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Act.img pet rewards for the summoned pet: "pettameness" (closeness) and "petskill" (a pet skill flag, in the old
 * numbering: 128 = recall with /petcome, 256 = talking by itself).
 */
public class QuestPetReward implements QuestReward {
    // closeness needed for each pet level (level 1 at 0)
    private static final int[] CLOSENESS = {1, 3, 6, 14, 31, 60, 108, 181, 287, 434, 632, 891, 1224, 1642, 2161,
            2793, 3557, 4467, 5542, 6801, 8263, 9950, 11882, 14084, 16578, 19391, 22547, 26074, 30000};

    private int status = 1;
    private int tameness;
    private int skillFlag;

    public QuestPetReward() {
    }

    public QuestPetReward(int status, int tameness, int skillFlag) {
        this.status = status;
        this.tameness = tameness;
        this.skillFlag = skillFlag;
    }

    @Override
    public int getStatus() {
        return status;
    }

    private static PetSkill skillByOldFlag(int flag) {
        return switch (flag) {
            case 128 -> PetSkill.RECALL;
            case 256 -> PetSkill.AUTO_SPEAKING;
            default -> null;
        };
    }

    @Override
    public void giveReward(Char chr) {
        Pet pet = chr.getPetByIdx(0);
        if (pet == null || pet.getItem() == null) {
            return;
        }
        PetItem pi = pet.getItem();
        if (tameness > 0) {
            int closeness = Math.min(CLOSENESS[CLOSENESS.length - 1], pi.getTameness() + tameness);
            pi.setTameness((short) closeness);
            int level = 1;
            for (int need : CLOSENESS) {
                if (closeness >= need) {
                    level++;
                }
            }
            if (level > pi.getLevel()) {
                pi.setLevel((byte) Math.min(30, level));
            }
        }
        PetSkill skill = skillByOldFlag(skillFlag);
        if (skill != null) {
            pi.addPetSkill(skill);
        }
        chr.write(WvsContext.inventoryOperation(true, false, InventoryOperation.Add, (short) pi.getBagIndex(),
                (short) 0, 0, pi));
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(status);
        dos.writeInt(tameness);
        dos.writeInt(skillFlag);
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestPetReward(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
