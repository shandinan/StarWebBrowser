package com.star.starwebbrowser.event;

import java.lang.reflect.Method;

/**
 * 执行事件
 */
public class sdnEvent {
    /**
     * 要执行的方法对象
     */
    private  Object object;
    /**
     * 要执行的方法名称
     */
    private  String  methodName;
    /**
     * 要执行方法
     */
    private  Object[] params;
    /**
     * 要执行方法的参数类型
     */
    private Class[] paramTypes;

    public  sdnEvent(Object object,String methodName,Object... args){
        this.object=object;
        this.methodName=methodName;
        this.params = args;
        this.createParamTypes(args);
    }

    /**
     * 根据参数数组生成参数类型数组
     * @param params
     */
    private  void  createParamTypes(Object[] params){
        this.paramTypes = new Class[params.length];
        for (int i=0;i<params.length;i++){
            this.paramTypes[i] = params[i].getClass();
        }
    }
    public Object getObject() {
        return object;
    }
    public  String getMethodName(){
        return  methodName;
    }

    public  Class[] getParamTypes(){
        return  paramTypes;
    }
    public  Object[] getParams(){
        return  params;
    }

    /**
     * 执行该对象的该方法
     * @throws
     */
    public  void  invoke() throws  Exception{
        Method method = object.getClass().getMethod(this.getMethodName(),this.getParamTypes());
        if(null==method){
            return;
        }
        method.invoke(this.getObject(),this.getParams());
    }
}
