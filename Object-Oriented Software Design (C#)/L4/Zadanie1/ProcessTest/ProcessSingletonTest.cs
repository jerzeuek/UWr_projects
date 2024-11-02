using Process;

namespace ProcessTest
{
    public class Tests
    {
        [TestFixture]
        public class ProcessSingletonTests
        {
            [Test]
            public void TestSingletonInstance()
            {
                var instance1 = ProcessSingleton.Instance;
                var instance2 = ProcessSingleton.Instance;

                Assert.That(instance2, Is.SameAs(instance1));
            }
        }
    }
}