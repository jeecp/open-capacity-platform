package com.open.capacity.handler;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author pm 1280415703@qq.com
 * @date 2019/10/27 18:08
 */
public class StartTaskListener implements TaskListener {



    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("调用了任务监听器");
    }
}
