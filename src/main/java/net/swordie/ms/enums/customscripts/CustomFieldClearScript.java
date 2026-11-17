package net.swordie.ms.enums.customscripts;

import net.swordie.ms.util.Util;

import java.util.Arrays;
import java.util.Set;

public enum CustomFieldClearScript {

    ShowClear("clear_showClear", 940205100, 401070000, 401100400, 401070700, 401070800),
    Clear450001380("clear_450001380", 450001380),
    Clear_first_towertop_enter("first_towertop_enter", 211060201), // Lionheart Questline
    Clear_second_towertop_enter("second_towertop_enter", 211060401), // Lionheart Questline
    Clear_third_towertop_enter("third_towertop_enter", 211060601), // Lionheart Questline
    // Dimensional Crack dark rooms 922010401-405: DimensionalCrackInstanceHelper counts cleared rooms
    Clear922030400("clear_922030400", 922030400), // Cygnus 3rd job adv
    ;

    private final String scriptName;
    private final Set<Integer> fieldIds;

    CustomFieldClearScript(String scriptName, int... fieldIds) {
        this.scriptName = scriptName;
        this.fieldIds = Util.makeSet(fieldIds);
    }

    public String getScriptName() {
        return scriptName;
    }

    public Set<Integer> getFieldIds() {
        return fieldIds;
    }

    public static String byFieldId(int id) {
        var script = Arrays.stream(values())
                .filter(cfcs -> cfcs.getFieldIds().contains(id))
                .findFirst()
                .orElse(null);

        return script == null ? null : script.getScriptName();
    }
}
