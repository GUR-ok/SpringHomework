package homeworkSpringApp.model.MadBrains;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

public class MyList<T> implements AuthorHolder, AdvancedList<T>, SimpleList<T> {

    private final int INIT_SIZE = 10; // Начальный размер внутреннего массива при инициализации списка
    private Object[] array = new Object[INIT_SIZE];
    private int counter;

    // Имя разработчика
    @Override
    public String author() {
        return "Gorelov Yuriy";
    }

    //Добавление элемента в список
    @Override
    public void add(T item) {
        if (counter == array.length)
            resize(array.length*2);
        array[counter++] = item;
    }

    /*
    Вставка элемента в список по индексу. Нумерация элементов списка начинается с 0.
    Добавляемый элемент становится на место index, остальные элементы сдвигаются вправо.
    */
    @Override
    public void insert(int index, T item) throws Exception {
        if (index > counter) throw new Exception();
        Object[] newArray = new Object[++counter];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = item;
        System.arraycopy(array, index, newArray, index+1, counter-index-1);
        array = newArray;
    }

    //Удаление элемента из списка по индексу. Нумерация элементов списка начинается с 0.
    @Override
    public void remove(int index) throws Exception {
        if (index > counter) throw new Exception();
        Object[] newArray = new Object[--counter];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index+1, newArray, index, counter-index);
        array = newArray;
    }

    //Получение элемента списка как объекта Optional
    @Override
    public Optional<T> get(int index) {
        T item = (T) array[index];
        return Optional.ofNullable(item);
    }

    // Возвращает размер списка по количеству элементов
    @Override
    public int size() {
        return counter;
    }

    // Увеличение размера внутреннего массива при нехватке места для добавления новых элементов
    private void resize(int newSize) {
        Object[] newArray = new Object[newSize];
        System.arraycopy(this.array, 0, newArray, 0, counter);
        array = newArray;
    }

    //Добавления списка целиком
    @Override
    public void addAll(SimpleList<T> list) {
        for (int i=0; i < list.size(); i++) {
            this.add(list.get(i).get());
        }
    }

    /*
    Возвращает индекс первого встретившегося элемента, который equals принятому в параметрах.
    Если такого элемента нет, то возвращает -1.
     */
    @Override
    public int first(T item) {
        for (int i = 0; i < counter; i++) {
            if (array[i].equals(item)) return i;
        }
        return -1;
    }

    /*
    Возвращает индекс последнего встретившегося элемента, который equals принятому в параметрах.
    Если такого элемента нет, то возвращает -1.
    */
    @Override
    public int last(T item) {
        for (int i = counter-1; i >= 0; i--) {
            if (array[i].equals(item)) return i;
        }
        return -1;
    }

    //Возвращает наличие элемента в списке в виде булевого значения
    @Override
    public boolean contains(T item) {
        for (int i = 0; i < counter; i++) {
            if (array[i].equals(item)) return true;
        }
        return false;
    }

    //Возвращает булево значение, пуст ли список
    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    //Возвращает перемешанный список.
    @Override
    public AdvancedList<T> shuffle() {
        Object[] arrayCopy = Arrays.copyOf(array,counter);
        int index;
        Random random = new Random();
        for (int i = arrayCopy.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            T temp = (T) arrayCopy[index];
            arrayCopy[index] = arrayCopy[i];
            arrayCopy[i] = temp;
        }
        AdvancedList<T> newList = new MyList<>();
        for (int i = 0; i < arrayCopy.length; i++) {
            newList.add((T) arrayCopy[i]);
        }
        return newList;
    }

    //Возвращает отсортированный список.
    @Override
    public AdvancedList<T> sort(Comparator<T> comparator) { //Сортировка методом quicksort
        Object[] arrayCopy = Arrays.copyOf(array,counter);
        quickSort((T[]) arrayCopy,0,arrayCopy.length-1,comparator);
        //bubblesort((T[]) arrayCopy,comparator); //Пузырьковый метод сортировки для тестов и сравнения скорости

        AdvancedList<T> newList = new MyList<>();
        for (int i = 0; i < arrayCopy.length; i++) {
            newList.add((T) arrayCopy[i]);
        }
        return newList;
    }

    //Приватный метод для сортировки, реализующий алгоритм быстрой сортировки.
    private void quickSort(T[] array, int left, int right, Comparator<T> comparator) {
        if (array.length == 0)
            return;
        if (left >= right)
            return;

        // Выбор опорного элемента
        int mid = left + (right - left) / 2;
        T pivot = (T) array[mid];

        // Разделение на 2 части
        int i = left, j = right;
        while (i <= j) {
            while (comparator.compare(array[i],pivot) < 0) {
                i++;
            }
            while (comparator.compare(array[j],pivot) > 0) {
                j--;
            }

            //Swap
            if (i <= j) {
                T temp = (T) array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (left < j)
            quickSort(array, left, j,comparator); // Рекурсия с выходом по нулевому размеру массива или когда его нельзя разделить
        if (right > i)
            quickSort(array, i, right,comparator);
    }

    /*
    Приватный метод для сортировки (для тестов и сравнения скорости сортировок),
    реализующий алгоритм пузырькового метода сортировки (медленнее чем qsort).
     */
    private void bubblesort(T[] array, Comparator<T> comparator) {
        for (int i = 0; i < array.length-1; i++) {
            for (int j = i; j < array.length; j++) {
                if (comparator.compare((T)array[i], (T)array[j]) > 0) {
                    T temp = (T) array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

}
