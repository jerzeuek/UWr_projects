using Timed;

namespace TimedTest
{
    [TestFixture]
    public class FiveSecondsSingletonTests
    {
        [Test]
        public void TestSingletonInstance()
        {
            var instance1 = FiveSecondsSingleton.Instance;
            Thread.Sleep(2000);
            var instance2 = FiveSecondsSingleton.Instance;

            Assert.That(instance2, Is.SameAs(instance1));
        }

        [Test]
        public void TestSingletonInstance2()
        {
            var instance1 = FiveSecondsSingleton.Instance;
            Thread.Sleep(6000);
            var instance2 = FiveSecondsSingleton.Instance;

            Assert.That(instance2, Is.Not.SameAs(instance1));
        }
    }
}