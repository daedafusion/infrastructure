package com.daedafusion.iconfactory.framework.providers.daos;

import com.daedafusion.hibernate.dao.GenericDAO;
import com.daedafusion.iconfactory.entities.Icon;

import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public interface IconDAO extends GenericDAO<Icon, Long>
{

    List<Icon> listByDomain(String domain);

}
