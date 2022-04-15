package com.snail.aop.operation;

import com.google.common.base.CaseFormat;
import com.snail.aop.annotaion.OpLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Aspect
@Component
public class OpLogAspect {

    private Logger log = LoggerFactory.getLogger(OpLogAspect.class);

    @Around("@annotation(com.snail.aop.annotaion.OpLog)")
    public Object log(ProceedingJoinPoint  pjp) throws Exception{
        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        OpLog opLog = method.getAnnotation(OpLog.class);
        Object response = null;

        try {
            //目标方法执行
            response = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        if(!StringUtils.isEmpty(opLog.OpItemIdExpression())){
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(opLog.OpItemIdExpression());
            EvaluationContext context = new StandardEvaluationContext();

            //获取参数值
            Object[] args = pjp.getArgs();

            //获取运行时参数的名称
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);

            //将参数绑定到context中
            if(parameterNames != null){
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i],args[i]);
                }
            }

            //将方法的resp当作变量放到context中，变量名称为该类名转换为小写字母开头的驼峰格式
            if(response != null){
                context.setVariable(
                        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, response.getClass().getSimpleName()),response);
            }

            // 解析表达式，获取结果
            String itemId = String.valueOf(expression.getValue(context));

            //执行日志记录操作
            log.info("opType is {},opItem is {},itemId is {}",opLog.opType().getOpKind() , opLog.opItem() ,itemId);
        }

        return response;
    }
}
