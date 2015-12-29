package co.ryred.red_commons.bukkit;

import org.bukkit.Location;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 08/02/2015.
 */
public class LocationHashMap<V> extends ConcurrentHashMap<org.bukkit.Location, V>
{

    @Override
    public boolean containsKey( Object key )
    {

        if ( !( key instanceof Location ) ) return false;
        Location loc = ( (Location) key );
        Location nLoc = new Location( loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() );

        return super.containsKey( nLoc );

    }

    @Override
    public V get( Object key )
    {

        if ( !( key instanceof Location ) ) return null;
        Location loc = ( (Location) key );
        Location nLoc = new Location( loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() );

        return super.get( nLoc );
    }

    @Override
    public V put( Location loc, V value )
    {
        return super.put( new Location( loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() ), value );
    }

}
