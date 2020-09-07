package com.wesley.growth.mp.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/09/07
 */
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> list = super.getMethodList(mapperClass);
        list.add(new MybatisRemoveAllMethod());
        return list;
    }

}
