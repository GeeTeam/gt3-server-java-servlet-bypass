package com.geetest.demo.listener;

import com.geetest.demo.task.CheckGeetestStatusTask;
import com.geetest.sdk.utils.PropertiesUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.Timer;

public class CheckGeetestStatusListener extends HttpServlet implements
        ServletContextListener {


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Timer timer = new Timer();
        CheckGeetestStatusTask task = new CheckGeetestStatusTask();
        timer.schedule(task, 0, Integer.parseInt(PropertiesUtils.get("task.schedule.check.geetest.status.interval", "10000")));  // 立即执行，每隔10秒执行一次task
        System.out.println("gtlog: 定时器开始执行: 监控极验云状态, 每隔10秒执行一次任务. ");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
