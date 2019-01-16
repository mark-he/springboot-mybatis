package com.eagletsoft.boot.framework.data.handler;

import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.framework.common.utils.BeanUtils;
import com.eagletsoft.boot.framework.data.entity.Auditable;
import com.eagletsoft.boot.framework.data.entity.Entity;
import com.eagletsoft.boot.framework.data.entity.meta.CreatedBy;
import com.eagletsoft.boot.framework.data.entity.meta.CreatedTime;
import com.eagletsoft.boot.framework.data.entity.meta.UpdatedBy;
import com.eagletsoft.boot.framework.data.entity.meta.UpdatedTime;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class })})
public class AuditMetaInterceptor implements Interceptor {

    private Object findEntity(Invocation invocation) {
        Object parameter = invocation.getArgs()[1];
        if (parameter instanceof Entity) {
            return parameter;
        } else if (parameter instanceof MapperMethod.ParamMap) {
            Object entity = ((MapperMethod.ParamMap) parameter).get("et");
            if (entity instanceof Entity) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if(SqlCommandType.UPDATE == sqlCommandType || SqlCommandType.INSERT==sqlCommandType) {
            Object entity = this.findEntity(invocation);

            if (null != entity && entity instanceof Auditable) {
                Date now = new Date();
                Object by = UserSession.getUserInterface().getId();
                List<Field> fields = BeanUtils.findAllDeclaredFields(entity.getClass());

                for (Field field : fields) {
                    if (null != AnnotationUtils.findAnnotation(field, UpdatedBy.class)) {
                        field.setAccessible(true);
                        field.set(entity, by);
                        field.setAccessible(false);
                    }
                    else if (null != AnnotationUtils.findAnnotation(field, UpdatedTime.class)) {
                        field.setAccessible(true);
                        field.set(entity, now);
                        field.setAccessible(false);
                    }
                    if (SqlCommandType.INSERT == sqlCommandType) {
                        if (null != AnnotationUtils.findAnnotation(field, CreatedBy.class)) {
                            field.setAccessible(true);
                            field.set(entity, by);
                            field.setAccessible(false);
                        }
                        else if (null != AnnotationUtils.findAnnotation(field, CreatedTime.class)) {
                            field.setAccessible(true);
                            field.set(entity, now);
                            field.setAccessible(false);
                        }
                    }
                }

            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}