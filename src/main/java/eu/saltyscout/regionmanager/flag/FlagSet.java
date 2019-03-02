package eu.saltyscout.regionmanager.flag;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Peter on 06-Nov-16.
 */
public final class FlagSet {

    private FlagSet() {

    }

    private static HashMap<String, Flag> types = new HashMap<>();
    private static HashMap<String, String> descriptions = new HashMap<>();
    private static HashMap<String, List<String>> dependencies = new HashMap<>();
    private static List<String> readonlyFlags = new ArrayList<>();

    public synchronized static <T> void register(String flag, String description, Class<? extends Flag<T>> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        checkNotNull(flag);
        checkNotNull(type);
        description = description == null ? "" : description;
        types.put(flag.toLowerCase(), (Flag) type.getConstructors()[0].newInstance(new Object[]{null}));
        descriptions.put(flag.toLowerCase(), description);
    }

    public static boolean isReadonly(String flag) {
        checkNotNull(flag);
        flag = flag.toLowerCase();
        return readonlyFlags.contains(flag);
    }

    public static void setReadOnly(String flag, boolean readonly) {
        checkNotNull(flag);
        flag = flag.toLowerCase();
        if(!readonly) {
            readonlyFlags.remove(flag);
        } else if(!readonlyFlags.contains(flag)) {
            readonlyFlags.add(flag);
        }
    }

    public synchronized static boolean setDependencies(String flag, String... depend) {
        checkNotNull(flag);
        flag = flag.toLowerCase();
        boolean valid = false;
        if(exists(flag)) {
            valid = true;
            for(int i = 0; valid && i < depend.length; i++) {
                String dependency = depend[i];
                if(!exists(dependency)) {
                    valid = false;
                } else if(dependsDeep(dependency, flag)) {
                    valid = false;
                }
            }
            dependencies.put(flag, Arrays.stream(depend).map(String::toLowerCase).collect(Collectors.toList()));
        }
        return valid;
    }

    public synchronized static List<String> getDependencies(String flag) {
        checkNotNull(flag);
        flag = flag.toLowerCase();
        if( dependencies.containsKey(flag) ) {
            return new ArrayList<>(dependencies.get(flag));
        } else {
            return new ArrayList<>();
        }
    }

    public synchronized static List<String> getDeepDependencies(String flag) {
        List<String> depend = getDependencies(flag);
        for(int i = 0; i < depend.size(); i++) {
            List<String> subDepend = getDeepDependencies(depend.get(i));
            depend.addAll(subDepend);
        }
        return depend;
    }

    public synchronized static boolean dependsShallow(String flag, String dependency) {
        checkNotNull(flag);
        checkNotNull(dependency);
        flag = flag.toLowerCase();
        dependency = dependency.toLowerCase();
        return dependencies.containsKey(flag) && dependencies.get(flag).contains(dependency);
    }

    public synchronized static boolean dependsDeep(String flag, String dependency) {
        checkNotNull(flag);
        checkNotNull(dependency);
        flag = flag.toLowerCase();
        dependency = dependency.toLowerCase();
        return getDeepDependencies(flag).contains(dependency);
    }

    public synchronized static boolean exists(String flag) {
        checkNotNull(flag);
        return types.containsKey(flag.toLowerCase());
    }
    
    public synchronized static Flag get(String flag) {
        return exists(flag) ? types.get(flag.toLowerCase()).clone() : null;
    }
    public synchronized static <T> Flag<T> get(String flag, Class<? extends Flag<T>> type) {
        return exists(flag) ? types.get(flag.toLowerCase()).clone() : null;
    }

    public synchronized static String getDescription(String flag) {
        checkNotNull(flag);
        flag = flag.toLowerCase();
        String description = null;
        if(exists(flag)) {
            description = descriptions.get(flag.toLowerCase());
            if(description == null) {
                description = "...";
            }
        }
        return description;
    }

    public synchronized static Set<String> getFlags() {
        return types.keySet().stream().collect(Collectors.toSet());
    }

    public synchronized static void flush() {
        types = new HashMap<>();
        descriptions = new HashMap<>();
    }

    public static int count() {
        return types.size();
    }
}
