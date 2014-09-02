package com.epam.library.user.mock.annotation;

import com.epam.library.TestData;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@WithMockCustomUser(value= TestData.MockUserDefaults.DEFAULT_MOCK_SIMPLE_USER_USERNAME, roles={"ROLE_USER"})
public @interface WithMockSimpleUser {
}
