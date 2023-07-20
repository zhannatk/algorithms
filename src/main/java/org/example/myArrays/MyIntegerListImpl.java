package org.example.myArrays;

import org.example.exc.*;


public class MyIntegerListImpl implements MyIntegerList {
    private Integer[] shadowArray;
    private int sizeWork;

    /**
     * Признак сортированности массива для возможности использования бинарного поиска
     */
    private boolean isSorted = false;

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


    public MyIntegerListImpl(int size) {
        if (size < 0) throw new MyIncorrectSizeOfArray("размер массива не может быть меньше ноля");
        if (size > MAX_SHADOW_SIZE)
            throw new MyIncorrectSizeOfArray("размер массива не может быть больше " + MAX_SHADOW_SIZE);


        this.sizeShadow = Math.max(size, sizeShadow);
        this.sizeWork = 0;
        shadowArray = new Integer[sizeShadow + 1];
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


            Integer[] newArray = new Integer[sizeShadow + 1];
            for (int i = 0; i <= sizeWork; i++) {// у метода заткнут варнинг о ручном копировании
                newArray[i] = shadowArray[i];
            }
            shadowArray = newArray;
        }
        if (sizeWork >= sizeShadow) throw new MyMemoryOverloadedException("Дальше увеличивать массив некуда");
    }


    private void checkIndex(int ind) throws MyIndexOutOfBoundsException {
        if (ind < 0 || ind >= sizeWork) throw new MyIndexOutOfBoundsException("индекс за пределами массива");
    }

    private void checkItem(Integer item) {
        if (item == null) throw new MyExcPutYourNullToYourAnal("Нельзя пихать налл");
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
    public Integer add(Integer item) {
        checkItem(item);
        shadowArray[sizeWork] = item;
        sizeWork++;
        growShadowMayBe();

        isSorted = false;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkItem(item);
        if (index != 0) checkIndex(index);
        sizeWork++;
        moveRight(index + 1);
        shadowArray[index] = item;
        growShadowMayBe();
        isSorted = false;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkIndex(index);
        checkItem(item);
        shadowArray[index] = item;
        isSorted = false;
        return item;
    }

    @Override
    public Integer removeByItem(Integer item) {
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
    public Integer removeByIndex(int index) {
        checkIndex(index);
        Integer res = shadowArray[index];
        moveLeft(index);
        sizeWork--;
        return res;
    }

    @Override
    public boolean contains(Integer item) {
        int steps = 0;
        checkItem(item);
        //если массив в данный момент отсортирован - ускоряем поиск
        if (isSorted)
            return binarySearch(item) != -1;

        for (int i = 0; i < sizeWork; i++) {
            steps++;
            if (shadowArray[i].equals(item)) {
                return true;
            }
        }
        System.out.println("================================Шагов обычного поиска:" + steps);

        return false;
    }

    @Override
    public int indexOf(Integer item) {
        checkItem(item);
        for (int i = 0; i < sizeWork; i++) {
            if (shadowArray[i].equals(item)) {
                return i;
            }
        }
        return -1;


    }

    @Override
    public int lastIndexOf(Integer item) {
        checkItem(item);
        for (int i = sizeWork - 1; i >= 0; i--) {
            if (shadowArray[i].equals(item)) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return shadowArray[index];
    }

    @Override
    public boolean equals(MyIntegerList otherList) {
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
        isSorted = false;
        sizeWork = 0;
        sizeShadow = 10;
    }

    @Override
    public Integer[] toArray() {
        Integer[] res = new Integer[sizeWork];

        System.arraycopy(shadowArray, 0, res, 0, sizeWork);

        return res;
    }


    @Override
    public void sortInsertion() {
        for (int i = 1; i < sizeWork; i++) {
            int temp = shadowArray[i];
            int j = i;
            while (j > 0 && shadowArray[j - 1] >= temp) {
                shadowArray[j] = shadowArray[j - 1];
                j--;
            }
            shadowArray[j] = temp;
        }
        isSorted = true;
    }

    @Override
    public void sortBubble() {
        for (int i = 0; i < sizeWork - 1; i++) {
            for (int j = 0; j < sizeWork - 1 - i; j++) {
                if (shadowArray[j] > shadowArray[j + 1]) {
                    swapElements(shadowArray, j, j + 1);
                }
            }
        }
        isSorted = true;
    }

    /**
     * Данный метод я не могу сделать приватным, так как
     * это ломает логику работы класса.
     * Пользователь должен быть извещён о факте сортировки
     * массива, поэтому я оставляю право сортировки на его выбор,
     * а выбор метода поиска вхождения элемента методом
     * contains происходит автоматически на основе флага
     * isSorted, который отслеживает факт сортировки массива
     */
    @Override
    public void sortSelection() {
        for (int i = 0; i < sizeWork - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < sizeWork; j++) {
                if (shadowArray[j] < shadowArray[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(shadowArray, i, minElementIndex);
        }
        isSorted = true;
    }


    private int binarySearch(Integer item) {
        int steps = 0;


        int left = 0;
        int right = sizeWork - 1;
        while (left <= right) {
            steps++;
            int mid = (right + left) / 2;
            if (item.equals(shadowArray[mid])) return mid;
            if (item < shadowArray[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println("================================Шагов бинарного поиска:" + steps);
        return -1;


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

    @Override

    public void sortQuickSort() {
        quickSort(0, sizeWork - 1);
        isSorted = true;

    }


    private void quickSort(int begin, int end) {

        if (begin < end) {
            int partitionIndex = partition(begin, end);

            quickSort(begin, partitionIndex - 1);
            quickSort(partitionIndex + 1, end);
        }
    }

    private int partition(int begin, int end) {
        int pivot = shadowArray[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (shadowArray[j] <= pivot) {
                i++;

                swapElements(shadowArray, i, j);
            }
        }

        swapElements(shadowArray, i + 1, end);
        return i + 1;
    }

    private void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

}
