package net.swordie.ms.client.character.skills.info;

import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 21-8-2019.
 *
 * @author Asura
 */
public class SkillUseInfo {
    // General
    public int skillID, skillID2;
    public int slv;
    public Position startingPosition;
    public Position position;
    public Position endingPosition;
    public Position targetPosition;
    public Position castPosition; // v271 skill-use info type 26 (e.g. Lara: where she casts)
    public boolean isLeft, byUnreliableMemory;
    public int rushX, rushY;
    public int attackAction;
    public int attackSpeed;
    public int attackActionType;
    public List<Integer> mobIds = new ArrayList<>();
    public List<Position> positions = new ArrayList<>();
    public int angle;
    public int unk;
    public Rect rect;
    public Map<Integer, Integer> targetMap; // Used by Photon Ray
    public int multiAttackInfoSequenceCounter;
    public int phantomRiftBreaksRemaining; // Used by Rift Break
    public int lifeObjectId;
    public int fieldId;
    // Dark Omen: the client's own bat animation handles (CUserLocal +0xb59c/+0xb5a0)
    public int[] effectIds = new int[2];

    // Projectile
    public int projectileItemId;

    // Rock 'N Shock (Mechanic)
    public byte rockNshockSize;
    public int[] rockNshockLifes = new int[2]; // used on last rockNshock, client encodes 2 previous rockNshocks planted in the Field.
}
