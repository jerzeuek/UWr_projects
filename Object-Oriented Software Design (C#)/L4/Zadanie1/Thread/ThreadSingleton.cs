namespace Thread
{
    public sealed class ThreadSingleton
    {
        [ThreadStatic]
        private static ThreadSingleton? instance;

        private ThreadSingleton() { }

        public static ThreadSingleton Instance
        {
            get
            {
                if (instance == null)
                    instance = new ThreadSingleton();
                return instance;
            }
        }
    }
 }
