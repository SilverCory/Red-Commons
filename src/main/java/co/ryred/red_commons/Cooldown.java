package co.ryred.red_commons;

import co.ryred.red_commons.util.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class Cooldown {

    private static ConcurrentHashMap<Class, Cooldown> instances = new ConcurrentHashMap<>();
    private static long lastPurge = 0;

    private final long cooldownTime;
    private final ConcurrentHashMap<Object, Long> cooldownMap;

    /**
     * Internal cooldown constuctor.
     * @param cooldownTime The time for the cooldown.
     * @param cooldownTimeFormat The unit of the time for the cooldown.
     */
    Cooldown(long cooldownTime, TimeUnit cooldownTimeFormat) {
        this.cooldownTime = cooldownTimeFormat.toMillis(cooldownTime);
        this.cooldownMap = new ConcurrentHashMap<>();
    }

    /**
     * Removes the cooldown owned by the owner.
     * @param owner The owner.
     */
    public static void remove( Class owner ) {
        instances.remove( owner );
    }

    /**
     * Removes the specified cooldown.
     * @param cooldown
     */
    public static void remove( Cooldown cooldown ) {

        Iterator<Map.Entry<Class, Cooldown>> cooldownIterator = instances.entrySet().iterator();

        while (cooldownIterator.hasNext()) {
            if( cooldownIterator.next().getValue().equals( cooldown ) ) cooldownIterator.remove();
        }

    }

    /**
     * Get or create a cooldown instance.
     *
     * @param owner The unique owner of the cooldown.
     * @param cooldownTime The time for the cooldown.
     * @param cooldownTimeFormat The unit of the time for the cooldown.
     * @return The unique cooldown.
     */
    public static Cooldown get(Class owner, long cooldownTime, TimeUnit cooldownTimeFormat) {

        if (instances.containsKey(owner))
            return instances.get(owner);

        Cooldown cd = new Cooldown( cooldownTime, cooldownTimeFormat );
        instances.put( owner, cd );
        return cd;

    }

    /**
     * Get or create a cooldown instance.
     *
     * If it doesn't exist it will be created with 2 minuites as the cooldown.
     * @param owner THe unique owner of the cooldown.
     * @return The unique cooldown.
     */
    public static Cooldown get(Class owner) {
        return get( owner, 2, TimeUnit.MINUTES );
    }

    /**
     * Should be called async in it's own repeating task.
     * Purges all unnecessary entries in the cooldown.
     */
    public static synchronized void purge() {
        if( (lastPurge + 30000) - System.currentTimeMillis() < 0 ) return;
        for( Map.Entry<Class, Cooldown> entry : instances.entrySet() ) {
            entry.getValue().purge1();
        }
        lastPurge = System.currentTimeMillis();
    }

    /**
     * Add an object to cooldown.
     * @param obj The hot object.
     */
    public void addChilling( Object obj )
    {
        cooldownMap.put( obj, System.currentTimeMillis() + cooldownTime );
    }

    /**
     * Remove an object from cooling down.
     * @param obj The now cold object.
     */
    public void removeChilling( Object obj )
    {
        this.cooldownMap.remove( obj );
    }

    /**
     * Is the object still too hot? ;)
     * @param obj The object to be rectal probed.
     * @return Is the object hot?
     */
    public boolean isChilling( Object obj )
    {
        return getCooldownTime( TimeUnit.MILLISECONDS, obj ) > 0;
    }

    /**
     * Get the cooldown time in the supplied TimeUnit.
     * @param tu The TimeUnit of the time to return.
     * @param obj The object to detect.
     * @return The time in the supplied TimeUnit.
     */
    public long getCooldownTime( TimeUnit tu, Object obj )
    {
        long time = System.currentTimeMillis();
        long userTime = cooldownMap.get( obj );
        userTime = userTime == 0 ? time - 50 : userTime;

        return tu.convert( userTime - time, TimeUnit.MILLISECONDS );
    }

    /**
     * Get a human readable time format.
     * @see co.ryred.red_commons.util.StringUtils#getTimeString(long)
     * @param obj The object to detect.
     * @return The formatted string.
     */
    public String getCooldownTimeFormatted( Object obj )
    {
        return StringUtils.getTimeString(getCooldownTime(TimeUnit.MILLISECONDS, obj));
    }

    /**
     * Internal purging.
     * @see Cooldown#purge()
     */
    private synchronized void purge1()
    {
        Iterator<Map.Entry<Object, Long>> iterator = cooldownMap.entrySet().iterator();
        while ( iterator.hasNext() ) { if ( !isChilling( iterator.next().getKey() ) ) iterator.remove(); }
    }

    /**
     * Clears all the cooldowns..
     */
    public void clear()
    {
        cooldownMap.clear();
    }

}
