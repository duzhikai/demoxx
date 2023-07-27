//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
public class ServerAopAspect {
    private static final Logger log = LoggerFactory.getLogger(ServerAopAspect.class);
    private CurrentUserService currentUserService;

    public ServerAopAspect(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public ServerAopAspect() {
    }

    @Around("@within(com.bytedance.thrift.server.ThriftService)")
    public Object thriftServerAround(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Method method = null;

        Object var10;
        try {
            Class returnType;
            BeanWrapperImpl beanWrapper;
            try {
                method = signature.getMethod();
                Optional.ofNullable(AnnotationUtils.findAnnotation(method, Identity.class)).ifPresent((annotation) -> {
                    this.injectUser(args);
                });
                Object result = joinPoint.proceed();
                log.info("Thrift 服务调用成功 {}.{}, params: {}, result: {}", new Object[]{className, methodName, args, result});
                Object var18 = result;
                return var18;
            } catch (BusinessException var15) {
                log.warn("Thrift 服务端业务 {}.{} 异常， params: {}", new Object[]{className, methodName, args, var15});
                returnType = method.getReturnType();
                beanWrapper = new BeanWrapperImpl(returnType);
                beanWrapper.setAutoGrowNestedPaths(true);
                beanWrapper.setPropertyValue("baseResp.statusMessage", var15.getMsg());
                beanWrapper.setPropertyValue("baseResp.statusCode", var15.getCode());
                var10 = beanWrapper.getWrappedInstance();
                return var10;
            } catch (Throwable var16) {
                log.error("Thrift 服务端逻辑 {}.{} 异常， params: {}", new Object[]{className, methodName, args, var16});
                returnType = method.getReturnType();
                beanWrapper = new BeanWrapperImpl(returnType);
                beanWrapper.setAutoGrowNestedPaths(true);
                beanWrapper.setPropertyValue("baseResp.statusMessage", "系统异常");
                beanWrapper.setPropertyValue("baseResp.statusCode", Constants.ERROR_INTERNAL_CODE);
                var10 = beanWrapper.getWrappedInstance();
            }
        } finally {
            UserContext.removeCurrentUser();
        }

        return var10;
    }

    private void injectUser(Object[] args) {
        try {
            if (this.currentUserService != null && args.length > 0) {
                Object[] var2 = args;
                int var3 = args.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Object arg = var2[var4];
                    Method method = ReflectionUtils.findMethod(arg.getClass(), "getCommonRequest");
                    if (method != null) {
                        Object commonRequest = method.invoke(arg);
                        if (commonRequest != null) {
                            Method getUserIdMethod = ReflectionUtils.findMethod(commonRequest.getClass(), "getUserID");
                            Method getUnionIdMethod = ReflectionUtils.findMethod(commonRequest.getClass(), "getUnion_id");
                            Method getSideMethod = ReflectionUtils.findMethod(commonRequest.getClass(), "getSide");
                            if (getUserIdMethod != null && getUnionIdMethod != null && getUnionIdMethod != null) {
                                Object userId = getUserIdMethod.invoke(commonRequest);
                                Object unionId = getUnionIdMethod.invoke(commonRequest);
                                Object side = getSideMethod.invoke(commonRequest);
                                CurrentUser cuer = new CurrentUser();
                                if (userId != null && userId instanceof Long) {
                                    cuer.setUserId((Long)userId);
                                }

                                if (unionId instanceof String && StringUtils.isNotBlank((String)unionId)) {
                                    cuer.setUnionId((String)unionId);
                                }

                                if (side != null) {
                                    Method getValueMethod = ReflectionUtils.findMethod(side.getClass(), "getValue");
                                    if (getValueMethod != null) {
                                        Object sideVal = getValueMethod.invoke(side);
                                        if (sideVal != null && sideVal instanceof Integer) {
                                            cuer.setSide((Integer)sideVal);
                                        }
                                    }
                                }

                                CurrentUser currentUser = this.currentUserService.getCurrentUser(cuer);
                                if (currentUser != null) {
                                    UserContext.putCurrentUser(currentUser);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var17) {
            log.error("injectUser errror", var17);
        }

    }
}
