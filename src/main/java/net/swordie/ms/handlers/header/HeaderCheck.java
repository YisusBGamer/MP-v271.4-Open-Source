package net.swordie.ms.handlers.header;

import net.swordie.ms.ServerSettings;
import net.swordie.ms.connection.netty.NettyClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import static net.swordie.ms.ServerConstants.UNVERIFIED_OP;

/**
 * Startup sanity check of the opcode enums: two In or two Out entries on the same number (the later one is
 * unreachable by number lookup), and field-gate allow-list names that aren't enum entries or are UNVERIFIED_OP.
 * Warns only.
 */
public class HeaderCheck {

    private static final Logger log = LogManager.getLogger();

    public static void run() {
        try {
            List<String> problems = new ArrayList<>();
            clashes("In", InHeader.values(), InHeader::getValue, problems);
            clashes("Out", OutHeader.values(), OutHeader::getValue, problems);
            gate("test.fieldInAllow", InHeader.values(), InHeader::getValue, problems);
            gate("test.fieldAllow", OutHeader.values(), OutHeader::getValue, problems);
            if (problems.isEmpty()) {
                log.info("[Headers] no duplicate opcodes; allow lists clean");
                return;
            }
            StringBuilder sb = new StringBuilder("[Headers] " + problems.size() + " opcode problem(s):");
            for (String p : problems) {
                sb.append("\n  ").append(p);
            }
            log.warn(sb.toString());
        } catch (Throwable t) {
            log.warn("[Headers] check failed: " + t);
        }
    }

    private static <E extends Enum<E>> void clashes(String dir, E[] values, Function<E, Short> op,
                                                    List<String> problems) {
        Map<Integer, List<String>> byOp = new TreeMap<>();
        for (E e : values) {
            int v = op.apply(e);
            if (v != UNVERIFIED_OP) {
                byOp.computeIfAbsent(v, k -> new ArrayList<>()).add(e.name());
            }
        }
        byOp.forEach((v, names) -> {
            if (names.size() > 1) {
                problems.add(String.format("%s %d shared by %s (lookup finds %s)", dir, v, String.join(", ", names),
                        names.get(names.size() - 1)));
            }
        });
    }

    private static <E extends Enum<E>> void gate(String prefix, E[] values, Function<E, Short> op,
                                                 List<String> problems) {
        Map<String, E> byName = new LinkedHashMap<>();
        for (E e : values) {
            byName.put(e.name(), e);
        }
        for (String suffix : NettyClient.GATE_SUFFIXES) {
            String key = prefix + suffix;
            String list = ServerSettings.getMergedList(key);
            for (String raw : list.split(",")) {
                String name = raw.trim();
                int colon = name.indexOf(':'); // NAME:n allows one sub-type
                if (colon > 0) {
                    name = name.substring(0, colon);
                }
                if (name.isEmpty() || name.chars().allMatch(Character::isDigit)) {
                    continue;
                }
                E e = byName.get(name);
                if (e == null) {
                    problems.add(key + ": " + name + " is not a header name");
                } else if (op.apply(e) == UNVERIFIED_OP) {
                    problems.add(key + ": " + name + " is UNVERIFIED_OP");
                }
            }
        }
    }
}
