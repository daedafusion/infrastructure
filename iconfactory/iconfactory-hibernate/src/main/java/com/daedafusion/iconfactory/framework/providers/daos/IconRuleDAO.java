package com.daedafusion.iconfactory.framework.providers.daos;

import com.daedafusion.hibernate.dao.GenericDAO;
import com.daedafusion.iconfactory.entities.IconRule;

import java.util.List;

/**
 * Created by mphilpot on 7/23/14.
 */
public interface IconRuleDAO extends GenericDAO<IconRule, Long>
{
    List<IconRule> findByDomain(String domain);

}
