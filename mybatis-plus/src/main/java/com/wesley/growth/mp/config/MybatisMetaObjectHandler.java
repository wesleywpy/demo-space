package com.wesley.growth.mp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/09/07
 */
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println(" ----- insertFill -----");
        boolean setter = metaObject.hasSetter("createBy");
        // 有 setter方法 才自动填充
        if (setter) {
            strictInsertFill(metaObject, "createBy", Integer.class, 666);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println(" ----- updateFill -----");
        Object val = getFieldValByName("updateBy", metaObject);
        // 没有值才进行自动填充
        if (Objects.isNull(val)) {
            strictUpdateFill(metaObject, "updateBy", Integer.class, 777);
        }
    }
}
