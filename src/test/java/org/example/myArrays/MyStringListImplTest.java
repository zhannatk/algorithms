package org.example.myArrays;


import org.example.exc.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStringListImplTest {
    private final int MAX = MyStringListImpl.MAX_SHADOW_SIZE;

    @Test
    void ShouldThrowExcOfIncorrectCreation() {
        assertThrows(MyIncorrectSizeOfArray.class, () -> new MyStringListImpl(-1));
        assertThrows(MyIncorrectSizeOfArray.class, () -> new MyStringListImpl(MAX + 1));

    }

    @Test
    void shouldThrowExcWhenTryAddAndShadowArrayIsFull() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        assertThrows(MyMemoryOverloadedException.class, () -> {
            for (int i = 0; i < MAX + 10; i++) {
                stringList.add("");
            }
        });

    }

    @Test
    void shouldThrowExcWhenTryAddToGrowShadowArray() {
        MyStringListImpl stringList = new MyStringListImpl(9);
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 40; i++) {
                stringList.add("hi");
            }
        });

    }


    @Test
    void add() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.add(null)),
                () -> assertEquals("dog", stringList.add("dog"))
        );


    }

    @Test
    void testAddIndexed() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add(0, "4");
        stringList.add(0, "3");
        stringList.add(0, "2");
        stringList.add(0, "1");
        assertAll(
                () -> assertEquals("dog", stringList.add(0, "dog")),
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.add(0, null)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.add(-1, "")),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.add(MAX, "")),
                () -> assertEquals("dog; 1; 2; 3; 4", stringList.toString())


        );
    }

    @Test
    void set() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("");
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.set(0, null)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.set(-1, "")),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.set(MAX, "")),
                () -> assertEquals("aa", stringList.set(0, "aa"))
        );
    }

    @Test
    void removeByItem() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("to remove");
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.add("4");

        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.remove(null)),
                () -> assertEquals("to remove", stringList.remove("to remove")),
                () -> assertThrows(MyExcNoSuchElement.class, () ->
                        stringList.remove("not_there")),
                () -> assertEquals("1; 2; 3; 4", stringList.toString())

        );

    }

    @Test
    void RemoveByIndex() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("removed");
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.remove(null)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.remove(-1)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.remove(MAX)),
                () -> assertEquals("removed", stringList.remove(0)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.remove(2))

        );

    }

    @Test
    void contains() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("presents");
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.contains(null)),
                () -> assertTrue(stringList.contains("presents")),
                () -> assertFalse(stringList.contains("not pres"))
        );


    }

    @Test
    void indexOf() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("contains");
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.indexOf(null)),
                () -> assertEquals(0, stringList.indexOf("contains")),
                () -> assertEquals(-1, stringList.indexOf("not pres"))
        );

    }

    @Test
    void lastIndexOf() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        stringList.add("contains");
        stringList.add("contains");
        assertAll(
                () -> assertThrows(MyExcPutYourNullToYourAnal.class, () ->
                        stringList.lastIndexOf(null)),
                () -> assertEquals(1, stringList.lastIndexOf("contains")),
                () -> assertEquals(-1, stringList.lastIndexOf("not pres"))
        );

    }

    @Test
    void get() {
        MyStringListImpl stringList = new MyStringListImpl(10);
        String expected = "get some";
        stringList.add(expected);
        assertAll(
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.get(-1)),
                () -> assertThrows(MyIndexOutOfBoundsException.class, () ->
                        stringList.get(2)),

                () -> assertEquals(expected, stringList.get(0))

        );


    }

    @Test
    void testEquals() {
        MyStringListImpl stringList1 = new MyStringListImpl(10);
        stringList1.add("cat");
        stringList1.add("dog");
        stringList1.add("frog");

        MyStringListImpl stringList11 = new MyStringListImpl(10);
        stringList11.add("cat");
        stringList11.add("dog");
        stringList11.add("frog");

        MyStringListImpl stringList2 = new MyStringListImpl(10);
        stringList2.add("cat");
        stringList2.add("dog");
        stringList2.add("bird");

        MyStringListImpl stringList22 = new MyStringListImpl(10);
        stringList22.add("cat");
        stringList22.add("dog");


        assertAll(
                () -> assertThrows(MyExcNullArrayInParam.class, () ->
                        stringList1.equals(null)),
                () -> assertTrue(stringList1.equals(stringList11)),
                () -> assertTrue(stringList1.equals(stringList1)),
                () -> assertFalse(stringList1.equals(stringList2)),
                () -> assertFalse(stringList1.equals(stringList22))
        );
    }

    @Test
    void size() {
        MyStringListImpl stringList1 = new MyStringListImpl(10);
        stringList1.add("cat");
        stringList1.add("dog");
        stringList1.add("frog");

        assertEquals(3, stringList1.size());


    }

    @Test
    void isEmpty() {
        MyStringListImpl stringList1 = new MyStringListImpl(10);
        MyStringListImpl stringList2 = new MyStringListImpl(10);

        stringList1.add("cat");
        stringList1.add("dog");
        stringList1.add("frog");
        assertAll(
                () -> assertFalse(stringList1.isEmpty()),
                () -> assertTrue(stringList2.isEmpty())
        );
    }

    @Test
    void clear() {
        MyStringListImpl stringList1 = new MyStringListImpl(10);

        stringList1.add("cat");
        stringList1.add("dog");
        stringList1.add("frog");
        stringList1.clear();
        assertAll(
                () -> assertTrue(stringList1.isEmpty())
        );

    }

    @Test
    void toArray() {
        String[] S = {"cat", "dog", "frog"};
        MyStringListImpl stringList1 = new MyStringListImpl(10);
        for (String s : S) {
            stringList1.add(s);
        }

        assertArrayEquals(S, stringList1.toArray());


    }

    @Test
    void shouldReturnText() {
        MyStringListImpl stringList1 = new MyStringListImpl(10);
        stringList1.add("cat");
        stringList1.add("dog");
        assertEquals("cat; dog", stringList1.toString());
    }
}