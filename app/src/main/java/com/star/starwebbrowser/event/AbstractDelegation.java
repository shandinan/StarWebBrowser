package com.star.starwebbrowser.event;

/**
 * 定义抽象类，可以让不同的委托实现
 */
public abstract class AbstractDelegation {

    private  EventHandler eventHandler = new EventHandler();
    public  EventHandler getEventHandler(){return  eventHandler;}

    /**
     * 增加需要委托的人
     * @param object
     * @param methodName
     * @param args
     */
    public abstract void addListener(Object object,String methodName,Object... args);

    /**
     * 执行委托人交代的事
     */
    public abstract void  invokeEvent();
}
