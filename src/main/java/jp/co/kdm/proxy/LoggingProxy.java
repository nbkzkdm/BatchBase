package jp.co.kdm.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * メソッド呼び出し時にログを出力するプロキシ
 */
public class LoggingProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass, T target) {
        Logger logger = LoggerFactory.getLogger(target.getClass());

        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        logger.info("Called {}.{} with args: {}",
                                target.getClass().getSimpleName(),
                                method.getName(),
                                args == null ? "[]" : java.util.Arrays.toString(args));

                        return method.invoke(target, args);
                    }
                }
        );
    }
}

