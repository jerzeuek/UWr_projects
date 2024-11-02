namespace Process
{
    public sealed class ProcessSingleton
    {
        private static readonly ProcessSingleton instance = new ProcessSingleton();

        private ProcessSingleton() { }

        public static ProcessSingleton Instance
        {
            get { return instance; }
        }
    }
}
