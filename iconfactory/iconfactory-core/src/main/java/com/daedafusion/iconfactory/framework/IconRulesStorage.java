package com.daedafusion.iconfactory.framework;

import com.daedafusion.iconfactory.entities.IconRule;
import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;

import java.util.List;

/**
 * Created by mphilpot on 7/22/14.
 */
public interface IconRulesStorage
{
    List<IconRule> getRules(String domain) throws StorageException;

    void setRule(IconRule rule) throws StorageException;

    IconRule getRule(Long id) throws ObjectNotFoundException, StorageException;

    void deleteRule(Long id) throws ObjectNotFoundException, StorageException;
}
