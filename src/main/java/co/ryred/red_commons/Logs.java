package co.ryred.red_commons;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class Logs {

    private static ConcurrentHashMap<Class, Logs> instances = new ConcurrentHashMap<>();

    @Getter
    private final Logger logger;

    public static Logs get( Class owner ) {
        return get( owner, Logger.getLogger(owner.getName()), false );
    }

    public static Logs get( Class owner, Logger logger ) {
        return get( owner, logger, false );
    }

    public static Logs get( Class owner, Logger logger, boolean debug ) {

        if( instances.containsKey( owner ) ) return instances.get( owner );

        Logs logs = new Logs( logger, debug );
        instances.put( owner, logs );
        return logs;

    }

    Logs(Logger logger, boolean debug) {
        setDebug( debug );
        this.logger = logger;
    }

    @Getter
    @Setter
    private boolean debug = true;

    @Getter
    @Setter
    private static boolean globalDebug = false;

    public boolean _D()
    {
        return isDebug() || isGlobalDebug();
    }

    public void _D( Object... objects )
    {
        if( !_D() ) return;

        StringBuilder sb = new StringBuilder( "[D]" );

        for ( Object obj : objects )
            sb.append( " | " ).append( obj );

        log( Level.INFO, sb );
    }

    public static void globalDebug( Object... objects )
    {
        if( !isGlobalDebug() ) return;

        StringBuilder sb = new StringBuilder( "[D]" );

        for ( Object obj : objects )
            sb.append( " | " ).append( obj );

        Logs.get(Logs.class).log(Level.INFO, sb);
    }

    public void info( String string )
    {
        getLogger().info(string);
    }

    public void fine( String string )
    {
        getLogger().fine( string );
    }

    public void finer( String string )
    {
        getLogger().finer( string );
    }

    public void finest( String string )
    {
        getLogger().finest( string );
    }

    public void severe( String string )
    {
        getLogger().severe(string);
    }

    public void warning( String string )
    {
        getLogger().warning( string );
    }

    public void log( Level level, Object object )
    {
        getLogger().log( level, object.toString() );
    }

}
