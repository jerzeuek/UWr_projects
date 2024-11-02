namespace Zadanie2
{
    public class MyDictionary<K, V>
    {
        K[] keys;
        V[] values;
        int size;
        int count_elems;

        public MyDictionary()
        {
            size = 10;
            keys = new K[size];
            values = new V[size];
            count_elems = 0;
        }

        public void Add(K key, V value)
        {
            for (int i = 0; i < count_elems; i++)
            {
                if (keys[i].Equals(key))
                    return;
            }

            if (count_elems + 1 > size)
            {
                size += 10;
                Array.Resize(ref keys, size);
                Array.Resize(ref values, size);
            }

            keys[count_elems] = key;
            values[count_elems] = value;
            count_elems++;
        }

        public V? Search(K key)
        {
            for (int i = 0; i < count_elems; i++)
            {
                if (keys[i].Equals(key))
                    return values[i];
            }
            return default(V);
        }

        public void Delete(K key)
        {
            int numIndex = Array.IndexOf(keys, key);
            if (numIndex == -1)
                return;

            for (int i = numIndex + 1; i <= count_elems; i++)
            {
                values[i - 1] = values[i];
                keys[i - 1] = keys[i];
            }

            count_elems--;
        }
    }
}