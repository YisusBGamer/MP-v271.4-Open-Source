package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.life.pet.PetSkill;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.PetInfo;
import net.swordie.ms.loaders.containerclasses.SetItemInfo;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 4/14/2018.
 */
public class PetItem extends Item {
    private static final Logger log = LogManager.getLogger();

    private String name;
    private byte level;
    private short tameness;
    private byte repleteness; // hungry thing
    private short petAttribute;
    private int petSkill;
    private FileTime dateDead;
    private int remainLife;
    private short attribute;
    private byte activeState;
    private int autoBuffSkill;
    private int petHue;
    private short giantRate;
    private int autoBuffSkill2;

    @Override
    public int getTransactionalHash() {
        return Objects.hash(super.getTransactionalHash(), name, level, tameness, repleteness, petAttribute, petSkill, dateDead, remainLife, attribute, activeState, autoBuffSkill, autoBuffSkill2, petHue, giantRate);
    }

    @Override
    public Type getType() {
        return Type.PET;
    }

    public void encode(OutPacket outPacket) {
        super.encode(outPacket);

        outPacket.encodeString(getName(), 21); // v271: 21 bytes (v265 13)
        outPacket.encodeByte(getLevel());
        outPacket.encodeShort(getTameness() + 1);
        outPacket.encodeByte(getRepleteness());
        outPacket.encodeFT((getDateDead() == null || getDateDead().toLong() == 0)
                ? FileTime.fromDate(LocalDateTime.now().plusYears(50))
                : getDateDead());
        outPacket.encodeShort(getPetAttribute());
        outPacket.encodeShort(getPetSkill());
        outPacket.encodeInt(getRemainLife());
        outPacket.encodeShort(getAttribute());
        outPacket.encodeByte(getActiveState());
        // v265 (reference tree): no auto-buff skill ints here, one int more at the end
        outPacket.encodeInt(getPetHue());
        outPacket.encodeShort(getGiantRate());
        outPacket.encodeShort(0); // 202
        outPacket.encodeInt(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public short getTameness() {
        return tameness;
    }

    public void setTameness(short tameness) {
        this.tameness = tameness;
    }

    public byte getRepleteness() {
        return repleteness;
    }

    public void setRepleteness(byte repleteness) {
        this.repleteness = repleteness;
    }

    public short getPetAttribute() {
        return petAttribute;
    }

    public void setPetAttribute(short petAttribute) {
        this.petAttribute = petAttribute;
    }

    public int getPetSkill() {
        return petSkill;
    }

    public void setPetSkill(int petSkill) {
        this.petSkill = petSkill;
    }

    public FileTime getDateDead() {
        return dateDead;
    }

    public void setDateDead(FileTime dateDead) {
        this.dateDead = dateDead;
    }

    public int getRemainLife() {
        return remainLife;
    }

    public void setRemainLife(int remainLife) {
        this.remainLife = remainLife;
    }

    public short getAttribute() {
        return attribute;
    }

    public void setAttribute(short attribute) {
        this.attribute = attribute;
    }

    public byte getActiveState() {
        return activeState;
    }

    public void setActiveState(byte activeState) {
        this.activeState = activeState;
    }

    public int getAutoBuffSkill() {
        return autoBuffSkill;
    }

    public void setAutoBuffSkill(int autoBuffSkill) {
        this.autoBuffSkill = autoBuffSkill;
    }

    public int getPetHue() {
        return petHue;
    }

    public void setPetHue(int petHue) {
        this.petHue = petHue;
    }

    public short getGiantRate() {
        return giantRate;
    }

    public void setGiantRate(short giantRate) {
        this.giantRate = giantRate;
    }

    public Pet createPet(Char chr) {
        Pet pet = new Pet(getItemId(), chr.getId());
        pet.setFh(chr.getFoothold());
        pet.setPosition(chr.getPosition());
        int chosenIdx = chr.getFirstPetIdx();
        if (chosenIdx == -1) {
            log.warn("Tried to create a pet while 3 pets already exist.");
        }
        pet.setIdx(chosenIdx);
        pet.setName(getName());
        pet.setPetLockerSN(getId());
        pet.setHue(getPetHue() == 0 ? -1 : getPetHue());
        pet.setGiantRate(getGiantRate() == 0 ? 100 : getGiantRate());
        pet.setItem(this);
        return pet;
    }

    /**
     * Pet eggs (e.g. the Dragon egg, 5000028) are pets that evolve on their own: no evolution item, no pet level
     * needed. The game client only has their hatching animation and waits for the server to turn the egg into the
     * hatched pet (Baby Dragon, 5000029), which never happened, so the egg stayed stuck hatching. Hatches it.
     *
     * @return whether the pet hatched (its item id changed)
     */
    public boolean hatchIfEgg() {
        PetInfo info = ItemData.getPetInfoByID(getItemId());
        if (info == null || !info.isEvol() || info.getEvolReqItemID() != 0 || info.getEvolReqPetLvl() != 0) {
            return false;
        }
        int target = pickEvolution(info);
        if (target == 0) {
            return false;
        }
        evolveTo(target);
        return true;
    }

    /**
     * @return whether this pet can evolve with an item (e.g. Baby Dragon -> a coloured Dragon with the Rock of
     * Evolution, at pet level 15)
     */
    public boolean canEvolveWithItem() {
        PetInfo info = ItemData.getPetInfoByID(getItemId());
        return info != null && info.isEvol() && info.getEvolReqItemID() != 0 && pickEvolution(info) != 0;
    }

    public int getEvolutionItemId() {
        PetInfo info = ItemData.getPetInfoByID(getItemId());
        return info == null ? 0 : info.getEvolReqItemID();
    }

    public int getEvolutionLevel() {
        PetInfo info = ItemData.getPetInfoByID(getItemId());
        return info == null ? 0 : info.getEvolReqPetLvl();
    }

    /**
     * Evolves the pet into one of its evolutions (by their chances; equal chances when none are given). Checking
     * the requirements (item, pet level) is up to the caller.
     *
     * @return the new pet item id, or 0 if this pet has no evolution
     */
    public int evolve() {
        PetInfo info = ItemData.getPetInfoByID(getItemId());
        int target = info == null || !info.isEvol() ? 0 : pickEvolution(info);
        if (target != 0) {
            evolveTo(target);
        }
        return target;
    }

    private static int pickEvolution(PetInfo info) {
        int[] targets = {info.getEvol1(), info.getEvol2(), info.getEvol3(), info.getEvol4(), info.getEvol5()};
        int[] probs = {info.getProbEvol1(), info.getProbEvol2(), info.getProbEvol3(), info.getProbEvol4(),
                info.getProbEvol5()};
        int count = Math.max(1, Math.min(5, info.getEvolNo()));
        List<Integer> options = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (targets[i] > 0 && ItemData.getPetInfoByID(targets[i]) != null) {
                options.add(targets[i]);
                weights.add(Math.max(0, probs[i]));
            }
        }
        if (options.isEmpty()) {
            return 0;
        }
        int total = weights.stream().mapToInt(Integer::intValue).sum();
        if (total <= 0) {
            return options.get(new Random().nextInt(options.size()));
        }
        int roll = new Random().nextInt(total);
        for (int i = 0; i < options.size(); i++) {
            roll -= weights.get(i);
            if (roll < 0) {
                return options.get(i);
            }
        }
        return options.get(0);
    }

    private void evolveTo(int target) {
        String oldDefaultName = net.swordie.ms.loaders.StringData.getItemStringById(getItemId());
        if (getName() == null || getName().equals(oldDefaultName)) {
            String newName = net.swordie.ms.loaders.StringData.getItemStringById(target);
            if (newName != null) {
                setName(newName);
            }
        }
        setItemId(target);
    }

    public void activatePet(Char chr, Pet pet) {
        setActiveState((byte) (pet.getIdx() + 1));
        syncSetSkills(chr, getItemId(), null);
    }

    public void deactivatePet(Char chr, Pet pet) {
        setActiveState((byte) 0);
        syncSetSkills(chr, getItemId(), pet);
    }

    /**
     * Gives the skill of the pet set's current level (pets of the set that are out, without the one leaving) and
     * takes the set's other level skills away, so a set never keeps a lower level next to the one it has.
     */
    public static void syncSetSkills(Char chr, int petItemId, Pet leaving) {
        PetInfo petInfo = ItemData.getPetInfoByID(petItemId);
        if (petInfo == null || petInfo.getSetItemID() <= 0) {
            return;
        }
        SetItemInfo sii = EtcData.getSetItemInfoById(petInfo.getSetItemID());
        if (sii == null) {
            return;
        }
        Set<Integer> itemIDs = sii.getItemIds();
        int count = (int) chr.getPets().stream()
                .filter(p -> p != leaving && itemIDs.contains(p.getItem().getItemId())).count();
        List<Skill> gone = new ArrayList<>();
        for (var level : sii.getEffectActiveSkills().entrySet()) {
            for (var entry : level.getValue().entrySet()) {
                int skillId = entry.getKey();
                if (level.getKey() == count) {
                    Skill owned = chr.getSkill(skillId);
                    if (owned == null || owned.getCurrentLevel() != entry.getValue()) {
                        chr.addSkill(skillId, entry.getValue(), entry.getValue());
                    }
                } else if (chr.hasSkill(skillId)) {
                    gone.add(chr.getSkill(skillId));
                }
            }
        }
        chr.removeSkills(gone);
    }

    public PetItem deepCopy() {
        PetItem petItem = new PetItem();

        // Item fields
        petItem.setItemId(getItemId());
        petItem.setBagIndex(getBagIndex());
        petItem.setCashItemSerialNumber(getCashItemSerialNumber());
        petItem.setDateExpire(getDateExpire().deepCopy());
        petItem.setInvType(getInvType());
        petItem.setCash(isCash());
        petItem.setType(getType());
        petItem.setOwner(getOwner());
        petItem.setQuantity(getQuantity());

        // PetItem fields
        petItem.setName(getName());
        petItem.setLevel(getLevel());
        petItem.setTameness(getTameness());
        petItem.setRepleteness(getRepleteness());
        petItem.setPetAttribute(getPetAttribute());
        petItem.setPetSkill(getPetSkill());
        petItem.setDateDead(getDateDead() == null ? null : getDateDead().deepCopy());
        petItem.setRemainLife(getRemainLife());
        petItem.setAttribute(getAttribute());
        petItem.setActiveState(getActiveState());
        petItem.setAutoBuffSkill(getAutoBuffSkill());
        petItem.setPetHue(getPetHue());
        petItem.setGiantRate(getGiantRate());

        return petItem;
    }

    public void addPetSkill(PetSkill petSkill) {
        setPetSkill(getPetSkill() | petSkill.getVal());
    }

    public void removePetSkill(PetSkill petSkill) {
        if (hasPetSkill(petSkill)) {
            setPetSkill(getPetSkill() ^ petSkill.getVal());
        }
    }

    public boolean hasPetSkill(PetSkill petSkill) {
        return (getPetSkill() & petSkill.getVal()) != 0;
    }

    public int getAutoBuffSkill2() {
        return autoBuffSkill2;
    }

    public void setAutoBuffSkill2(int autoBuffSkill2) {
        this.autoBuffSkill2 = autoBuffSkill2;
    }
}
