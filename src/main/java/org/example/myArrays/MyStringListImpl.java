package org.example.myArrays;

import org.example.exc.*;

public class MyStringListImpl implements StringList {
    private String[] shadowArray;
    private int sizeWork;

    private int sizeShadow = 10;

    /**
     * Коэффициент увеличения теневого массива
     * ДОЛЖЕН быть больше 1
     */
    private final double SHADOW_GROW_COEFFICIENT = 2;
    private final double SHADOW_GROW_COEFFICIENT_INVERTED = 1.0 / SHADOW_GROW_COEFFICIENT;

    /**
     * Соотношение теневого к рабочему массиву
     * ДОЛЖНО быть больше 1
     */
    private final double SHADOW_TO_WORK_COEFFICIENT = 1.5;
    private final double SHADOW_TO_WORK_COEFFICIENT_INVERTED = 1.0 / SHADOW_TO_WORK_COEFFICIENT;
    public static final int MAX_SHADOW_SIZE = Integer.MAX_VALUE / 20 - 1;


    public MyStringListImpl(int size) {
        if (size < 0) throw new MyIncorrectSizeOfArray("размер массива не может быть меньше ноля");
        if (size > MAX_SHADOW_SIZE)
            throw new MyIncorrectSizeOfArray("размер массива не может быть больше " + MAX_SHADOW_SIZE);

        this.sizeShadow = Math.max(size, sizeShadow);
        this.sizeWork = 0;
        shadowArray = new String[sizeShadow + 1];

    }


    @SuppressWarnings("ManualArrayCopy")
    private void growShadowMayBe() {
        // проверяем, нужно ли расширять теневой массив
        if (sizeShadow * SHADOW_TO_WORK_COEFFICIENT_INVERTED < sizeWork && sizeShadow != MAX_SHADOW_SIZE) {
            if (MAX_SHADOW_SIZE * SHADOW_GROW_COEFFICIENT_INVERTED > sizeShadow) {//проверяем - можно ли увеличить теневой массив
                sizeShadow = (int) ((sizeWork) * SHADOW_GROW_COEFFICIENT);
            } else {
                sizeShadow = MAX_SHADOW_SIZE;
            }


            String[] newArray = new String[sizeShadow + 1];
            for (int i = 0; i <= sizeWork; i++) {// у метода заткнут варнинг о ручном копировании
                newArray[i] = shadowArray[i];
            }
            shadowArray = newArray;
        }
        if (sizeWork >= sizeShadow) throw new MyMemoryOverloadedException("Дальше увеличивать массив некуда");
    }


    private void checkIndex(int ind) throws MyIndexOutOfBoundsException {
        if (ind < 0 || ind >= sizeWork)
            throw new MyIndexOutOfBoundsException("индекс за пределами массива");
    }


    private void checkItem(String item) {
        if (item == null)
            throw new MyExcPutYourNullToYourAnal("Нельзя пихать налл");
    }

    private void moveRight(int from) {
        for (int i = sizeWork; i >= from; i--)
            shadowArray[i] = shadowArray[i - 1];

    }

    private void moveLeft(int to) {
        for (int i = to; i < sizeWork - 1; i++) {
            shadowArray[i] = shadowArray[i + 1];
        }
    }

    @Override
    public String add(String item) {
        checkItem(item);
        shadowArray[sizeWork] = item;
        sizeWork++;
        growShadowMayBe();


        return item;
    }

    @Override
    public String add(int index, String item) {
        checkItem(item);
        if (index != 0)
            checkIndex(index);
        sizeWork++;
        moveRight(index + 1);
        shadowArray[index] = item;
        growShadowMayBe();
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkIndex(index);
        checkItem(item);
        shadowArray[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        checkItem(item);
        for (int i = 0; i < sizeWork; i++) {
            if (shadowArray[i].equals(item)) {
                moveLeft(i);
                sizeWork--;
                //decreaseShadowMayBe();
                return item;
            }
        }
        throw new MyExcNoSuchElement("не найдено для удаления");
    }

    @Override
    public String remove(int index) {
        checkIndex(index);
        String res = shadowArray[index];
        moveLeft(index);
        sizeWork--;
        return res;
    }

    @Override
    public boolean contains(String item) {
        checkItem(item);
        for (int i = 0; i < sizeWork; i++) {
            if (shadowArray[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(String item) {
        checkItem(item);
        for (int i = 0; i < sizeWork; i++) {
            if (shadowArray[i].equals(item)) {
                return i;
            }
        }
        return -1;


    }

    @Override
    public int lastIndexOf(String item) {
        checkItem(item);
        for (int i = sizeWork - 1; i >= 0; i--) {
            if (shadowArray[i].equals(item)) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public String get(int index) {
        checkIndex(index);
        return shadowArray[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (otherList == null) throw new MyExcNullArrayInParam("а где массив-то?");
        if (this == otherList) {
            return true;
        }
        if (this.size() != otherList.size()) return false;
        for (int i = 0; i < this.size(); i++) {
            if (!this.shadowArray[i].equals(otherList.get(i))) return false;
        }
        return true;
    }

    @Override
    public int size() {
        return sizeWork;
    }

    @Override
    public boolean isEmpty() {

        return sizeWork == 0;
    }

    @Override
    public void clear() {
        sizeWork = 0;
        sizeShadow = 10;
    }

    @Override
    public String[] toArray() {
        String[] res = new String[sizeWork];

        System.arraycopy(shadowArray, 0, res, 0, sizeWork);

        return res;
    }

    @Override
    public String toString() {
        String res = "";
        int i;
        for (i = 0; i < sizeWork - 1; i++) {
            res = res + shadowArray[i] + "; ";
        }

        res = res + shadowArray[i];

        return res;
    }
}
