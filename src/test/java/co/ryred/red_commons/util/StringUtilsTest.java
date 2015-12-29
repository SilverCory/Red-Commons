package co.ryred.red_commons.util;

import co.ryred.red_commons.Cooldown;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class StringUtilsTest {

    @Test( timeout = 5000 )
    public void doGetTimeStringTest() {

        Assert.assertEquals("50 seconds.", StringUtils.getTimeString(50000) );
        Assert.assertEquals("20 minutes, 34 seconds.", StringUtils.getTimeString(1234234) );
        Assert.assertEquals("8 seconds.", StringUtils.getTimeString( 8573 ) );
        Assert.assertEquals("6 days, 12 hours, 30 minutes, 58 seconds.", StringUtils.getTimeString( 563458702 ) );
        Assert.assertEquals("not very long!", StringUtils.getTimeString(-6) );

    }

}
