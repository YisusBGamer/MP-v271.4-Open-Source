package net.swordie.ms.client.character;

import net.swordie.ms.loaders.DataTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/** The classes the character creation screen offers, from resources/data/creatable-classes.tsv. */
public final class CreatableClasses {
    private record Data(Set<Integer> finalJobs, Set<String> loginJobs, Map<Integer, Integer> tiers) {
    }

    private static final Supplier<Data> DATA = DataTable.cached("creatable-classes", t -> {
        Set<Integer> finalJobs = new HashSet<>();
        Set<String> loginJobs = new HashSet<>();
        Map<Integer, Integer> tiers = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            tiers.put(r.i("final_job"), r.i("tiers", 4));
            if (!"parked".equals(r.str("status").trim())) {
                finalJobs.add(r.i("final_job"));
                loginJobs.add(r.str("login_job").trim());
            }
        }
        return new Data(finalJobs, loginJobs, tiers);
    });

    private CreatableClasses() {
    }

    /** @return whether the class that ends as this job is offered (not parked) */
    public static boolean isFinalJob(int finalJob) {
        return DATA.get().finalJobs().contains(finalJob);
    }

    /** @return whether the class made from this LoginJob name is offered (not parked) */
    public static boolean isLoginJob(String name) {
        return DATA.get().loginJobs().contains(name);
    }

    /** @return the job advancements of the class that ends as this job, or 0 if it isn't listed */
    public static int tiers(int finalJob) {
        return DATA.get().tiers().getOrDefault(finalJob, 0);
    }
}
