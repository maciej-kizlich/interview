package com.epam.library.user.db;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithDbUserSecurityContextFactory.class)
public @interface WithDbUser {

    String username() default "";

    String value();
}
