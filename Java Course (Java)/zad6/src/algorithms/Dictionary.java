package algorithms;
public interface Dictionary<T extends Comparable<T>> {
    void insert(T x);
    void remove(T x);
    boolean search(T x);
    T min();
    T max();
    int size();
    void clear();
}
