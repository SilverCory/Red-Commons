package co.ryred.red_commons.util;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class WebUtils {

    /**
     * Get the contents of a site.
     *
     * @param url The url of the site to get contents from.
     * @return The contents of the site.
     * @throws IOException
     */
    public static String getSiteContents( URL url ) throws IOException {
        return new Scanner( url.openStream(), "UTF-8" ).useDelimiter( "\\A" ).next();
    }

    /**
     * @see WebUtils#getSiteContents(URL)
     *
     * @param url The url of the site to get contents from.
     * @return The contents of the site.
     * @throws IOException May be a MalformedURLException.
     */
    public static String getSiteContents( String url ) throws IOException {
        return getSiteContents( new URL( url ) );
    }

}
