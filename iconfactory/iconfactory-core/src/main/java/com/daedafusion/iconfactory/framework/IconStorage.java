package com.daedafusion.iconfactory.framework;

import com.daedafusion.iconfactory.entities.Icon;
import com.daedafusion.iconfactory.framework.exceptions.ObjectNotFoundException;
import com.daedafusion.iconfactory.framework.exceptions.StorageException;

import java.util.List;

/**
 * Created by mphilpot on 7/22/14.
 */
public interface IconStorage
{
    List<Icon> getIcons(String domain) throws StorageException;

    Icon addIcon(Icon icon) throws StorageException;

    Icon getIcon(Long id) throws ObjectNotFoundException, StorageException;

    void deleteIcon(Long id) throws ObjectNotFoundException, StorageException;
}
