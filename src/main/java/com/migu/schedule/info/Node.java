package com.migu.schedule.info;

import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author liming
 * @version [版本号, 六月 20, 2018]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Node
{
    private int nodeId;
    private List<Task> tasks;

    public int getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("nodeId=").append(nodeId);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
    }
}
