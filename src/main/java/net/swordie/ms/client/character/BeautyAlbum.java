package net.swordie.ms.client.character;

import java.util.Arrays;

/**
 * A character's Beauty Album (saved hair, face and skin slots) and Angelic Buster's dress-up look.
 * Slot codes are the client's: 20000 + i face, 30000 + i hair, 40000 + i skin. Empty slots are -1.
 */
public class BeautyAlbum {

    public static final int FACE = 2, HAIR = 3, SKIN = 4;
    public static final int START_SLOTS = 9, START_SKIN_SLOTS = 6;
    public static final int MAX_SLOTS = 60, MAX_SKIN_SLOTS = 30;
    public static final int EXTEND_STEP = 3;

    private int charId;
    private int[] hairs = empty(START_SLOTS);
    private int[] faces = empty(START_SLOTS);
    private int[] skins = empty(START_SKIN_SLOTS);
    private int dressFace, dressHair, dressSkin;
    private boolean dressOn;

    public BeautyAlbum(int charId) {
        this.charId = charId;
    }

    private static int[] empty(int n) {
        int[] arr = new int[n];
        Arrays.fill(arr, -1);
        return arr;
    }

    public static int typeOf(int code) {
        return code / 10000;
    }

    public int getCharId() {
        return charId;
    }

    public int[] slots(int type) {
        return switch (type) {
            case HAIR -> hairs;
            case FACE -> faces;
            case SKIN -> skins;
            default -> null;
        };
    }

    public void setSlots(int type, int[] arr) {
        switch (type) {
            case HAIR -> hairs = arr;
            case FACE -> faces = arr;
            case SKIN -> skins = arr;
        }
    }

    public int maxSlots(int type) {
        return type == SKIN ? MAX_SKIN_SLOTS : MAX_SLOTS;
    }

    /** @return the id in that slot, or -1 */
    public int get(int code) {
        int[] arr = slots(typeOf(code));
        int i = code % 10000;
        return arr != null && i >= 0 && i < arr.length ? arr[i] : -1;
    }

    public boolean set(int code, int id) {
        int[] arr = slots(typeOf(code));
        int i = code % 10000;
        if (arr == null || i < 0 || i >= arr.length) {
            return false;
        }
        arr[i] = id;
        return true;
    }

    public boolean isFull(int type) {
        int[] arr = slots(type);
        return arr != null && Arrays.stream(arr).noneMatch(id -> id < 0);
    }

    /** Grows every type to its cap. @return true if any grew */
    public boolean openAll() {
        boolean grew = false;
        for (int type : new int[]{HAIR, FACE, SKIN}) {
            int[] arr = slots(type);
            if (arr.length < maxSlots(type)) {
                int[] grown = Arrays.copyOf(arr, maxSlots(type));
                Arrays.fill(grown, arr.length, grown.length, -1);
                setSlots(type, grown);
                grew = true;
            }
        }
        return grew;
    }

    /** Adds EXTEND_STEP free slots, up to the cap. @return true if it grew */
    public boolean extend(int type) {
        int[] arr = slots(type);
        if (arr == null || arr.length >= maxSlots(type)) {
            return false;
        }
        int n = Math.min(maxSlots(type), arr.length + EXTEND_STEP);
        int[] grown = Arrays.copyOf(arr, n);
        Arrays.fill(grown, arr.length, n, -1);
        setSlots(type, grown);
        return true;
    }

    public int getDressFace() {
        return dressFace;
    }

    public void setDressFace(int dressFace) {
        this.dressFace = dressFace;
    }

    public int getDressHair() {
        return dressHair;
    }

    public void setDressHair(int dressHair) {
        this.dressHair = dressHair;
    }

    public int getDressSkin() {
        return dressSkin;
    }

    public void setDressSkin(int dressSkin) {
        this.dressSkin = dressSkin;
    }

    public boolean isDressOn() {
        return dressOn;
    }

    public void setDressOn(boolean dressOn) {
        this.dressOn = dressOn;
    }
}
