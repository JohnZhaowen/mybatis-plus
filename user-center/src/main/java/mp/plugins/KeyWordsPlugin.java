package mp.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;


@Intercepts({
    @Signature(
        type= StatementHandler.class,method = "prepare" ,args = {Connection.class, Integer.class}
    )
})
@Component("KeyWordsPlugin")
public class KeyWordsPlugin implements Interceptor {

    /**
     * 拦截目标对象的目标方法的执行
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

        String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");


        /** 执行目标方法，即执行本来就要执行的执行sql语句。如果没有这一句，就相当于直接返回了，不再操作原本应该执行的逻辑了，会破坏mybatis的内部结构  */
        Object proceed = invocation.proceed();
        return proceed;
    }

    /**
     * 包装目标对象，为目标创建一个代理对象
     *      即：将传入的四大对象包装一下，返回Proxy的代理对象。
     */
    @Override
    public Object plugin(Object target) {
        /**  Mybatis提供的包装对象Plugin */
        Object wrap = Plugin.wrap(target, this);

        /** 方法解析。getAllinterfaces(type, signatureMap) 这个方法在当前插件拦截器的注解中是否要拦截这个对象；
         *  比如说拦截器顶部的注解中要拦截Executor，interfaces.length才会大于0。否则就会跳过对Executor的包装
         *  包装代理对象也是利用Proxy代理对象处理，因此拦截之后会执行这个Plugin的invoke()的方法
         * public static Object wrap(Object target, Interceptor interceptor) {
         Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
         Class<?> type = target.getClass();
         Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
         return interfaces.length > 0 ? Proxy.newProxyInstance(type.getClassLoader(), interfaces, new Plugin(target, interceptor, signatureMap)) : target;
         }
         */

        return wrap;
    }

    /**
     * 获取插件注册传递的参数包装成Propereties并自动获取到
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }
}
