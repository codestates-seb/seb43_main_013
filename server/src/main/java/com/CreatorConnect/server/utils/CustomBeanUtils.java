package com.CreatorConnect.server.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
public class CustomBeanUtils {

    // 소스 객체에서 대상 객체로 속성을 복사
    // 소스 객체와 대상 객체가 null 이거나 클래스가 다를 경우, 복사를 수행하지 않고 null 을 반환
    public <T> T copyNonNullProperties(T source, T destination) {
        if (source == null || destination == null || source.getClass() != destination.getClass()) {
            return null;
        }

        // BeanWrapper 를 사용하여 소스 객체와 대상 객체를 감싸고, 속성에 접근
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper dest = new BeanWrapperImpl(destination);

        for (final Field property : source.getClass().getDeclaredFields()) {
            Object sourceProperty = src.getPropertyValue(property.getName());

            // 소스 객체의 속성 값이 null 이 아니고 Collection 이 아닌 경우에만, 대상 객체의 동일한 속성에 값을 설정
            if (sourceProperty != null && !(sourceProperty instanceof Collection<?>)) {
                dest.setPropertyValue(property.getName(), sourceProperty);
            }
        }

        return destination;
    }
}
