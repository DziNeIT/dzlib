package pw.ollie.sprint.test.reflect;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pw.ollie.sprint.reflect.sun.SilentInstantiator;

@RunWith(JUnit4.class)
public class SilentInstantiatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void runTest() {
        // if constructor was called, we'd get UnsupportedOperationException
        CantInstantiate cannot = SilentInstantiator.create(
                CantInstantiate.class);

        Assert.assertNotNull("cannot null - creation failed", cannot);
        Assert.assertEquals("u wot m8 how u do dis",
                cannot.coolMethodWhichMakesYourLifeOneHundredTimesBetterButYouCanNeverCallItBecauseYouCannotGetAnInstanceOfCantInstantiateLolLolLol());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exception.expect(NullPointerException.class);
        System.out.println(SilentInstantiator.create(String.class));
    }

    private final class CantInstantiate {
        private boolean instantiated;

        private CantInstantiate() {
            instantiated = true;
            // haha! you can't instantiate this class!
            throw new UnsupportedOperationException();
        }

        public String coolMethodWhichMakesYourLifeOneHundredTimesBetterButYouCanNeverCallItBecauseYouCannotGetAnInstanceOfCantInstantiateLolLolLol() {
            if (!instantiated) {
                return "u wot m8 how u do dis";
            } else {
                return "haha u cant fool me with your method of bringing back the object after an exception in constructor!";
            }
        }
    }
}
