import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Для хранения продуктов используется словарь: ключ - атрибут, значение - множество id, в которых есть данный атрибут.
public class ProductsStorage {

    private Map<String, Set<Integer>> struct = new HashMap<>();

    public Map<String, Set<Integer>> getStruct() {
        return this.struct;
    }

    public void put(int productId, String[] attributes) {
        if (attributes != null) {
            for (String attr : attributes) {
                struct.computeIfAbsent(attr, k -> new HashSet<>()).add(productId);
            }
        }
    }

    int[] searchByAttributes(String[] attributes) {
        if (attributes == null || struct.get(attributes[0]) == null) {
            return new int[0];
        }
        Set<Integer> Ids = new HashSet<>(struct.get(attributes[0]));
        for (int i = 1; i < attributes.length; i++) {
            if (struct.get(attributes[i]) == null) {
                return new int[0];
            }
            Ids.retainAll(struct.get(attributes[i]));
        }
        return Ids.stream().mapToInt(i -> i).toArray();
    }
}