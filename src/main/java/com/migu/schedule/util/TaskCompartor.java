package com.migu.schedule.util;

import java.util.Comparator;

import com.migu.schedule.info.Task;

/**
 * 比较器
 *
 * @author liming
 * @version [版本号, 六月 20, 2018]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TaskCompartor implements Comparator<Task>
{

    @Override
    public int compare(Task task, Task t2)
    {
        if(task.getConsumption() - t2.getConsumption() != 0)
        return task.getConsumption() - t2.getConsumption();
        else
        {
            return task.getTaskId() - t2.getTaskId();
        }
    }
}
