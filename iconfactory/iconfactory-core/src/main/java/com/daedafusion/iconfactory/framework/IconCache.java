package com.daedafusion.iconfactory.framework;

/**
 * Created by mphilpot on 7/22/14.
 */
public interface IconCache
{
    /**
     *
     * @param key
     * @return null if not found
     */
    byte[] getIcon(String key);

    void putIcon(String key, byte[] png);
}
