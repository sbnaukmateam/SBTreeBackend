package org.sbteam.sbtree.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class IgnoreNullBeanUtilsBean extends BeanUtilsBean {

    private static final ContextClassLoaderLocal<IgnoreNullBeanUtilsBean> BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<IgnoreNullBeanUtilsBean>() {
        // Creates the default instance used when the context classloader is unavailable
        @Override
        protected IgnoreNullBeanUtilsBean initialValue() {
            return new IgnoreNullBeanUtilsBean();
        }
    };

    public static IgnoreNullBeanUtilsBean getInstance() {
        return BEANS_BY_CLASSLOADER.get();
    }

    public IgnoreNullBeanUtilsBean() {
        this(new ConvertUtilsBean(), new PropertyUtilsBean());
    }

    public IgnoreNullBeanUtilsBean(final ConvertUtilsBean convertUtilsBean) {
        this(convertUtilsBean, new PropertyUtilsBean());
    }

    public IgnoreNullBeanUtilsBean(final ConvertUtilsBean convertUtilsBean, final PropertyUtilsBean propertyUtilsBean) {
        super(convertUtilsBean, propertyUtilsBean);
        getConvertUtils().register(false, true, 0);
    }

    @Override
    public void copyProperty(final Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value != null) {
            super.copyProperty(bean, name, value);
        }
    }
}