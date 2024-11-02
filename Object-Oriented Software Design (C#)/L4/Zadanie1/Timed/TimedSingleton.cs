namespace Timed
{
    public sealed class FiveSecondsSingleton
    {
        private static readonly object lockObject = new object();
        private static FiveSecondsSingleton? instance;
        private static DateTime lastAccessTime;

        private FiveSecondsSingleton() { }

        public static FiveSecondsSingleton Instance
        {
            get
            {
                lock (lockObject)
                {
                    if (instance == null || (DateTime.Now - lastAccessTime).TotalSeconds >= 5)
                    {
                        instance = new FiveSecondsSingleton();
                        lastAccessTime = DateTime.Now;
                    }
                    return instance;
                }
            }
        }
    }
}
