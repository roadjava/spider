package com.threepeople.spider.util;

import com.threepeople.spider.bean.req.CrawlRequest;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 */
public class ValidationUtil {

    private static Validator validator;
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 检验参数值
     * @param t : 被校验的对象
     * @return
     */
    public static <T> boolean valid(T t){
        return CollectionUtils.isEmpty(validator.validate(t));
    }



}
