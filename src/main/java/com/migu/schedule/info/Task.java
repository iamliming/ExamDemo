package com.migu.schedule.info;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author liming
 * @version [版本号, 六月 20, 2018]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Task
{
    private int taskId;
    private int consumption;

    public int getTaskId()
    {
        return taskId;
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    public int getConsumption()
    {
        return consumption;
    }

    public void setConsumption(int consumption)
    {
        this.consumption = consumption;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("taskId=").append(taskId);
        sb.append(", consumption=").append(consumption);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Task task = (Task)o;

        if (taskId != task.taskId)
            return false;
        return consumption == task.consumption;
    }

    @Override
    public int hashCode()
    {
        int result = taskId;
        result = 31 * result + consumption;
        return result;
    }
}
