package org.example.myArrays;

import org.example.exc.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyIntegerImplTest {
    private final int MAX = MyIntegerListImpl.MAX_SHADOW_SIZE;

    @Test
    void ShouldThrowExcOfIncorrectCreation() {
        assertThrows(MyIncorrectSizeOfArray.class, () -> new MyIntegerListImpl(-1));
        assertThrows(MyIncorrectSizeOfArray.class, () -> new MyIntegerListImpl(MAX + 1));

    }

    @Test
    void shouldThrowExcWhenTryAddAndShadowArrayIsFull() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        assertThrows(MyMemoryOverloadedException.class, () -> {
            for (int i = 0; i < MAX + 10; i++) {
                myIntegerList.add(0);
            }
        });

    }

    @Test
    void shouldThrowExcWhenTryAddToGrowShadowArray() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(9);
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 4; i++) {
                myIntegerList.add(0);
            }
        });

    }


    @Test
    void add() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        assertAll(() -> assertEquals(222, myIntegerList.add(222)), () -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.add(null))

        );


    }

    @Test
    void testAddIndexed() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(0, 4);
        myIntegerList.add(0, 3);
        myIntegerList.add(0, 2);
        myIntegerList.add(0, 1);

        assertAll(() -> assertEquals(222, myIntegerList.add(0, 222)), () -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.add(2, null)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.add(-1, 0)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.add(MAX, 0)), () -> assertEquals("222; 1; 2; 3; 4", myIntegerList.toString()));
    }

    @Test
    void set() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(0);
        assertAll(() -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.set(0, null)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.set(-1, 0)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.set(MAX, 0)), () -> assertEquals(10, myIntegerList.set(0, 10)));
    }

    @Test
    void removeByItem() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(111);
        myIntegerList.add(222);
        myIntegerList.add(333);
        myIntegerList.add(444);
        myIntegerList.add(555);
        assertAll(() -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.removeByItem(null)), () -> assertEquals(111, myIntegerList.removeByItem(111)), () -> assertThrows(MyExcNoSuchElement.class, () -> myIntegerList.removeByItem(999))

        );

    }

    @Test
    void RemoveByIndex() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(111);
        assertAll(() -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.removeByIndex(-1)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.removeByIndex(MAX)), () -> assertEquals(111, myIntegerList.removeByIndex(0)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.removeByIndex(2))

        );

    }

    @Test
    void contains() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(111);
        assertAll(() -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.contains(null)), () -> assertTrue(myIntegerList.contains(111)), () -> assertFalse(myIntegerList.contains(222)));


    }

    @Test
    void indexOf() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(111);
        assertAll(() -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.indexOf(null)), () -> assertEquals(0, myIntegerList.indexOf(111)), () -> assertEquals(-1, myIntegerList.indexOf(222)));

    }

    @Test
    void lastIndexOf() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        myIntegerList.add(111);
        myIntegerList.add(111);
        assertAll(() -> assertThrows(MyExcPutYourNullToYourAnal.class, () -> myIntegerList.lastIndexOf(null)), () -> assertEquals(1, myIntegerList.lastIndexOf(111)), () -> assertEquals(-1, myIntegerList.lastIndexOf(222)));

    }

    @Test
    void get() {
        MyIntegerListImpl myIntegerList = new MyIntegerListImpl(10);
        Integer expected = 333;
        myIntegerList.add(expected);
        assertAll(() -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.get(-1)), () -> assertThrows(MyIndexOutOfBoundsException.class, () -> myIntegerList.get(2)),

                () -> assertEquals(expected, myIntegerList.get(0))

        );


    }

    @Test
    void testEquals() {
        MyIntegerListImpl myIntegerList1 = new MyIntegerListImpl(10);
        myIntegerList1.add(111);
        myIntegerList1.add(222);
        myIntegerList1.add(333);

        MyIntegerListImpl myIntegerList11 = new MyIntegerListImpl(10);
        myIntegerList11.add(111);
        myIntegerList11.add(222);
        myIntegerList11.add(333);

        MyIntegerListImpl myIntegerList2 = new MyIntegerListImpl(10);
        myIntegerList2.add(111);
        myIntegerList2.add(222);
        myIntegerList2.add(444);

        MyIntegerListImpl myIntegerList22 = new MyIntegerListImpl(10);
        myIntegerList22.add(111);
        myIntegerList22.add(222);


        assertAll(() -> assertThrows(MyExcNullArrayInParam.class, () -> myIntegerList1.equals(null)), () -> assertTrue(myIntegerList1.equals(myIntegerList11)), () -> assertTrue(myIntegerList1.equals(myIntegerList1)), () -> assertFalse(myIntegerList1.equals(myIntegerList2)), () -> assertFalse(myIntegerList1.equals(myIntegerList22)));
    }

    @Test
    void size() {
        MyIntegerListImpl myIntegerList1 = new MyIntegerListImpl(10);
        myIntegerList1.add(111);
        myIntegerList1.add(222);
        myIntegerList1.add(333);

        assertEquals(3, myIntegerList1.size());


    }

    @Test
    void isEmpty() {
        MyIntegerListImpl myIntegerList1 = new MyIntegerListImpl(10);
        MyIntegerListImpl myIntegerList2 = new MyIntegerListImpl(10);

        myIntegerList1.add(111);
        myIntegerList1.add(222);
        myIntegerList1.add(333);
        assertAll(() -> assertFalse(myIntegerList1.isEmpty()), () -> assertTrue(myIntegerList2.isEmpty()));
    }

    @Test
    void clear() {
        MyIntegerListImpl myIntegerList1 = new MyIntegerListImpl(10);

        myIntegerList1.add(111);
        myIntegerList1.add(222);
        myIntegerList1.add(333);
        myIntegerList1.clear();
        assertAll(() -> assertTrue(myIntegerList1.isEmpty()));

    }

    @Test
    void toArray() {
        Integer[] S = {111, 222, 333};
        MyIntegerListImpl myIntegerList1 = new MyIntegerListImpl(10);
        for (Integer s : S) {
            myIntegerList1.add(s);
        }

        assertArrayEquals(S, myIntegerList1.toArray());


    }


    @Test
    void shouldSortBubble() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(3);
        iList1.add(2);
        iList1.add(1);
        iList1.add(0);
        iList1.sortBubble();
        assertEquals("0; 1; 2; 3", iList1.toString());
    }

    @Test
    void shouldSortInsertion() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(3);
        iList1.add(2);
        iList1.add(1);
        iList1.add(0);
        iList1.sortInsertion();
        assertEquals("0; 1; 2; 3", iList1.toString());
    }

    @Test
    void shouldSortSelection() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(3);
        iList1.add(2);
        iList1.add(1);
        iList1.add(0);
        iList1.sortSelection();
        assertEquals("0; 1; 2; 3", iList1.toString());
    }


    @Test
    void shouldSortQuickSort() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(3);
        iList1.add(2);
        iList1.add(1);
        iList1.add(0);
        iList1.sortQuickSort();
        assertEquals("0; 1; 2; 3", iList1.toString());
    }

    @Test
    void binarySearchTest() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(3);
        iList1.add(2);
        iList1.add(1);
        iList1.add(0);
        iList1.sortInsertion();
        assertFalse(iList1.contains(-1));
        assertFalse(iList1.contains(100));

    }

    @Test
    void shouldReturnText() {
        MyIntegerListImpl iList1 = new MyIntegerListImpl(10);
        iList1.add(11);
        iList1.add(22);

        assertEquals("11; 22", iList1.toString());
    }
}