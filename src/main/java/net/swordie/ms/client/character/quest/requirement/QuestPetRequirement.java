package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

/**
 * Pet requirement from Quest.wz/Check.img/[id]/[0|1]: one of the pets in <code>pet/[n]/id</code> must be summoned
 * (any pet when the list is empty or <code>allPet</code> is set), with at least <code>pettamenessmin</code>
 * closeness.
 */
public class QuestPetRequirement implements QuestStartRequirement {

    private Set<Integer> petIds = new HashSet<>();
    private int minTameness;

    public QuestPetRequirement() {
    }

    public QuestPetRequirement(Set<Integer> petIds, int minTameness) {
        this.petIds = petIds;
        this.minTameness = minTameness;
    }

    public Set<Integer> getPetIds() {
        return petIds;
    }

    public int getMinTameness() {
        return minTameness;
    }

    @Override
    public boolean hasRequirements(Char chr) {
        if (chr.getPets() == null) {
            return false;
        }
        for (Pet pet : chr.getPets()) {
            if (pet == null) {
                continue;
            }
            if (!getPetIds().isEmpty() && !getPetIds().contains(pet.getTemplateId())) {
                continue;
            }
            int tameness = pet.getItem() == null ? 0 : pet.getItem().getTameness();
            if (tameness >= getMinTameness()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeShort(getPetIds().size());
        for (int id : getPetIds()) {
            dos.writeInt(id);
        }
        dos.writeInt(getMinTameness());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        Set<Integer> ids = new HashSet<>();
        short size = dis.readShort();
        for (int i = 0; i < size; i++) {
            ids.add(dis.readInt());
        }
        return new QuestPetRequirement(ids, dis.readInt());
    }
}
