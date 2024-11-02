using Thread;

namespace ThreadTest
{
    [TestFixture]
    public class ThreadSingletonTests
    {
        [Test]
        public void TestSingletonInstance()
        {
            ThreadSingleton? instance1 = null;
            ThreadSingleton? instance2 = null;

            Parallel.Invoke(
                () => { instance1 = ThreadSingleton.Instance; },
                () => { instance2 = ThreadSingleton.Instance; }
            );

            Assert.That(instance2, Is.Not.SameAs(instance1));
        }
    }
}