package bank.tools;

import java.io.Serializable;

/**
 * This class represents the pair class, same as C++ pair
 *
 * @param <T> first
 * @param <R> second
 * @author Ilya Novitskiy, @SaintPher, Ilya.Novitskiy.04@mail.ru
 */
public class Pair<T extends Number, R extends Number> implements Serializable {
    private T first;
    private R second;

    public Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }
}
