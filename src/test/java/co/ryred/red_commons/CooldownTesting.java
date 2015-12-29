package co.ryred.red_commons;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class CooldownTesting {

    @Test
    public void testGet() throws Exception {

        Cooldown cd = Cooldown.get(CooldownTesting.class, 2, TimeUnit.SECONDS);
        Cooldown cd1 = Cooldown.get(CooldownTesting.class);
        Cooldown cd2 = Cooldown.get(Cooldown.class);

        Assert.assertEquals(cd, cd1);

        Assert.assertNotNull( cd2 );
        if( cd2.equals(cd1) )
            Assert.fail();

    }

    @Test
    public void testBasic() {

        Cooldown.remove( Cooldown.class );

        Cooldown cd = Cooldown.get(CooldownTesting.class, 2, TimeUnit.SECONDS);
        String string = "ekkekekekeke";
        cd.addChilling( string );

        Assert.assertEquals( true, cd.isChilling( string ) );

        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
        }
        Assert.assertEquals( true, cd.isChilling( string ) );

        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
        }
        Assert.assertEquals( false, cd.isChilling( string ) );

    }

}
