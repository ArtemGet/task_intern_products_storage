import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ProductsStorageTest {
    private ProductsStorage actualProducts;
    private ProductsStorage actualProductsNullAttribute;
    private Map<String, Set<Integer>> expectedProducts;

    //    метод для обратной совместимости с JDK 8(вместо List.of)
    private static Set<Integer> fill(int... ids) {
        Set<Integer> filler = new HashSet<>();
        for (int id : ids) {
            filler.add(id);
        }
        return filler;
    }

    @Before
    public void putActualProducts() {
        actualProducts = new ProductsStorage();
        actualProducts.put(1, new String[]{"atr1", "atr2", "atr10", "atr16"});
        actualProducts.put(2, new String[]{"atr1", "atr2", "atr13", "anotherAtr"});
        actualProducts.put(3, new String[]{"atr1", "atr3", "atr7", "atr16", "atr1602"});
        actualProducts.put(4, new String[]{"atr1", "atr4", "atr10", "atr19"});
        actualProducts.put(5, new String[]{"atr1", "atr2", "atr10", "atr22"});
        actualProducts.put(6, new String[]{"atr2", "atr10", "yetAnotherAtr"});
    }

    @Before
    public void putActualProductsNullAttribute() {
        actualProductsNullAttribute = new ProductsStorage();
        actualProductsNullAttribute.put(1, new String[]{"atr1", "atr2", "atr10", "atr16"});
        actualProductsNullAttribute.put(2, new String[]{"atr1", "atr2", "atr13", "anotherAtr"});
        actualProductsNullAttribute.put(3, new String[]{"atr1", "atr3", "atr7", "atr16", "atr1602"});
        actualProductsNullAttribute.put(4, new String[]{"atr1", "atr4", "atr10", "atr19"});
        actualProductsNullAttribute.put(5, new String[]{"atr1", "atr2", "atr10", "atr22"});
        actualProductsNullAttribute.put(6, new String[]{"atr2", "atr10", "yetAnotherAtr"});
        actualProductsNullAttribute.put(7, null);
        actualProductsNullAttribute.put(8, new String[]{null});
    }

    @Before
    public void setExpectedProducts() {
        expectedProducts = new HashMap<>();

        expectedProducts.put("atr1", new HashSet<>(fill(1, 2, 3, 4, 5)));
        expectedProducts.put("atr2", new HashSet<>(fill(1, 2, 5, 6)));
        expectedProducts.put("atr3", new HashSet<>(fill(3)));
        expectedProducts.put("atr10", new HashSet<>(fill(1, 4, 5, 6)));
        expectedProducts.put("atr16", new HashSet<>(fill(1, 3)));
        expectedProducts.put("atr13", new HashSet<>(fill(2)));
        expectedProducts.put("anotherAtr", new HashSet<>(fill(2)));
        expectedProducts.put("atr7", new HashSet<>(fill(3)));
        expectedProducts.put("atr1602", new HashSet<>(fill(3)));
        expectedProducts.put("atr4", new HashSet<>(fill(4)));
        expectedProducts.put("atr19", new HashSet<>(fill(4)));
        expectedProducts.put("atr22", new HashSet<>(fill(5)));
        expectedProducts.put("yetAnotherAtr", new HashSet<>(fill(6)));
    }

    @Test
    public void put() {
        Map<String, Set<Integer>> actual = actualProducts.getStruct();
        Map<String, Set<Integer>> expected = expectedProducts;

        for (Map.Entry<String, Set<Integer>> entry : expected.entrySet()) {
            assertEquals(actual.get(entry.getKey()), expected.get(entry.getKey()));
        }
    }

    @Test
    public void putNullAttribute() {
        Map<String, Set<Integer>> actual = actualProductsNullAttribute.getStruct();
        Map<String, Set<Integer>> expected = expectedProducts;

        for (Map.Entry<String, Set<Integer>> entry : expected.entrySet()) {
            assertEquals(actual.get(entry.getKey()), expected.get(entry.getKey()));
        }
    }

    @Test
    public void searchByAttributes() {
        assertArrayEquals(new int[]{1, 2, 5}, actualProducts.searchByAttributes(new String[]{"atr1", "atr2"}));
        assertArrayEquals(new int[]{2}, actualProducts.searchByAttributes(new String[]{"atr1", "atr13"}));
        assertArrayEquals(new int[]{1, 4, 5}, actualProducts.searchByAttributes(new String[]{"atr1", "atr10"}));
        assertArrayEquals(new int[]{1, 5, 6}, actualProducts.searchByAttributes(new String[]{"atr2", "atr10"}));

    }

    @Test
    public void searchByAttributesNull() {
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(null));
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(new String[]{null}));
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(new String[]{null, "atr1", "atr2"}));
        assertArrayEquals(new int[]{1, 2, 5}, actualProducts.searchByAttributes(new String[]{"atr1", "atr2"}));
    }

    @Test
    public void searchByAttributesNotExists() {
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(new String[]{"", "atr1", "atr2"}));
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(new String[]{"atr1", "", "atr2"}));
        assertArrayEquals(new int[0], actualProducts.searchByAttributes(new String[]{""}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, actualProducts.searchByAttributes(new String[]{"atr1"}));
    }
}