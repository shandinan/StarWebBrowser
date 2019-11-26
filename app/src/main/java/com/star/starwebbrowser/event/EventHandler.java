package com.star.starwebbrowser.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 委托执行器
 */
public class EventHandler {
    /**
     * 委托的事件
     */
    private List<sdnEvent> eventList = new ArrayList<>();

    public void  addEvent(Object object,String methodName,Object... args){
        eventList.add(new sdnEvent(object,methodName,args));
    }

    /**
     * 执行通知事件
     * @throws
     */
    public  void  notifyX() throws  Exception{
        for (sdnEvent event :eventList){
            event.invoke();
        }
    }

}
