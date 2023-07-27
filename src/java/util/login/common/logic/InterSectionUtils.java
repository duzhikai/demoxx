package util.login.common.logic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xuliangliang.1995
 */
public class InterSectionUtils {

    public static <T> Set<T> interSection(Set<T> set1, Set<T> set2) {
        Set<T> interSection = new HashSet<>();
        for (T element : set1) {
            if (set2.contains(element)) {
                interSection.add(element);
            }
        }
        return interSection;
    }

    public static <T> Set<T> interSection(Collection<T> collection1, Collection<T> collection2) {
        return interSection(new HashSet<>(collection1), new HashSet<>(collection2));
    }
}
