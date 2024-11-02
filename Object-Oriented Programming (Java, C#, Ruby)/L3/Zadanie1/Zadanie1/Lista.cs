namespace Zadanie1
{
    public class Lista<T>
    {
        class Element
        {
            public T value;
            public Element? next;
            public Element? prev;

            public Element(T value)
            {
                prev = null;
                next = null;
                this.value = value;
            }
        }

        Element? first;

        public Lista()
        {
            first = null;
        }
        public void push_front(T elem)
        {
            Element newElement = new Element(elem);

            if (first == null)
            {
                first = newElement;
                return;
            }
            else if (first.next == null)
            {
                newElement.prev = first;
                newElement.next = first;
                first.prev = newElement;
                first = newElement;
                return;
            }
            else
            {
                newElement.next = first;
                newElement.prev = first.prev;
                first.prev = newElement;
                first = newElement;
                return;
            }
        }

        public void push_back(T val)
        {
            Element? newElement = new Element(val);

            if (first == null)
            {
                first = newElement;
                return;
            }
            else if (first.next == null)
            {
                first.prev = newElement;
                first.next = newElement;
                newElement.prev = first;
                return;
            }
            else
            {
                first.prev.next = newElement;
                newElement.prev = first.prev;
                first.prev = newElement;

                return;
            }
        }

        public T pop_front()
        {
            if (first == null)
                throw new NullException("Lista jest pusta!");

            else
            {
                if (first.next == null)
                {
                    T val_return = first.value;
                    first = null;
                    return val_return;
                }
                else
                {
                    T val_return = first.value;
                    first.next.prev = first.prev;

                    first = first.next;
                    return val_return;
                }
            }
        }

        public T pop_back()
        {
            if (first == null)
                throw new NullException("Lista jest pusta!");

            else
            {
                if (first.next == null)
                {
                    T val_return = first.value;
                    first = null;
                    return val_return;
                }
                else
                {
                    T val_return = first.prev.value;
                    first.prev.prev.next = null;
                    first.prev = first.prev.prev;

                    return val_return;
                }
            }
        }

        public bool is_empty()
        {
            return first == null;
        }

        public void printList()
        {
            Element? temp = first;
            while (temp != null)
            {
                Console.WriteLine(temp.value);
                temp = temp.next;
            }
        }
    }

    public class NullException : System.Exception
    {
        public NullException(string warning) : base(warning)
        {
            Source = "Unknown exception :(";
        }
    }
}
