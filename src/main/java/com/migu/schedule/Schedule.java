package com.migu.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.Node;
import com.migu.schedule.info.Task;
import com.migu.schedule.info.TaskInfo;
import com.migu.schedule.util.NodeCompartor;
import com.migu.schedule.util.TaskCompartor;
import com.migu.schedule.util.TaskCompartorCom;
import com.migu.schedule.util.TaskCompartorTaskId;

/*
*类名和方法不能修改
 */
public class Schedule {

    private List<Node> nodes;
    private Queue<Task> queue;
    private Map<Task, Node> taskNodeMap;

    public int init() {
        // TODO 方法未实现
        nodes = new ArrayList<Node>();
        queue = new LinkedBlockingQueue();
        taskNodeMap = new HashMap<>();

        return ReturnCodeKeys.E001;
    }

    private boolean containNode(int node)
    {
        for(Node node1 : nodes){
            if(node1.getNodeId() == node)
            {
                return true;
            }
        }
        return false;
    }

    public int registerNode(int nodeId) {
        // TODO 方法未实现
        if(nodeId <= 0)
        {
            return ReturnCodeKeys.E004;
        }

        if(containNode(nodeId))
        {
            return ReturnCodeKeys.E005;
        }
        Node node = new Node();
        node.setNodeId(nodeId);
        node.setTasks(new ArrayList<>());
        nodes.add(node);
        return ReturnCodeKeys.E003;
    }


    public int unregisterNode(int nodeId) {
        // TODO 方法未实现
        for(Node node1 : nodes){
            if(node1.getNodeId() == nodeId)
            {
                List<Task>  ts =  node1.getTasks();
                if(ts != null)
                {
                    queue.addAll(ts);
                    System.out.println(queue.size());
                }
                nodes.remove(node1);
                return ReturnCodeKeys.E006;
            }
        }
        {
            return ReturnCodeKeys.E007;
        }
    }


    public int addTask(int taskId, int consumption) {
        if(taskId == 0 )
        {
            return ReturnCodeKeys.E009;
        }
        Task task = new Task();
        task.setTaskId(taskId);
        task.setConsumption(consumption);
        if(taskNodeMap.keySet().contains(task))
        {
            return ReturnCodeKeys.E010;
        }
        else
        {
            queue.add(task);
            taskNodeMap.put(task, null);
        }
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        // TODO 方法未实现
        System.out.println(queue.size());
        for(Task task : taskNodeMap.keySet())
        {
            if(task.getTaskId() == taskId)
            {
                Node node = taskNodeMap.get(task);
                if(node == null)
                {
                    queue.remove(task);
                    System.out.println(queue.size());
                }
                else
                {
                    node.getTasks().remove(task);
                }
                taskNodeMap.remove(task);
                return ReturnCodeKeys.E011;

            }
        }
        return ReturnCodeKeys.E012;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
        //获取所有损耗值
        int sumCons = 0;
        for(Task task : taskNodeMap.keySet())
        {
            sumCons += task.getConsumption();
        }
        int minAvg = sumCons / nodes.size();
        int maxAvg = minAvg;
        if(sumCons % nodes.size() > 0)
        {
            maxAvg += 1;
        }
        //计算每个节点的平均任务数
        int avgT = taskNodeMap.keySet().size() / nodes.size();

        //对所有的节点排序
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(taskNodeMap.keySet());
        Collections.sort(taskList, new TaskCompartor());
        Collections.sort(nodes, new NodeCompartor());
        System.out.println(taskList);
        //判断最大值是否大于损耗+阈值
        //直接返回
        if(taskList.get(taskList.size() - 1).getConsumption() > maxAvg)
        {
            return ReturnCodeKeys.E014;
        }
        //任务值一样
        if(taskList.get(0).getConsumption() == taskList.get(taskList.size() - 1).getConsumption())
        {
            int flag = 0;
            for(int i = 0; i< nodes.size(); i++)
            {
                nodes.get(i).getTasks().clear();
                for (int j = 0; j < avgT; j++)
                {
                    Task t = taskList.get(flag++);
                    nodes.get(i).getTasks().add(t);
                    taskNodeMap.put(t,nodes.get(i));
                }
            }
            return ReturnCodeKeys.E013;
        }
        if(nodes.size() == 3)
        {
            List<Integer> one = new ArrayList<>();
            List<Integer> two = new ArrayList<>();
            List<Integer> three = new ArrayList<>();
            boolean threeIsOk = false;
            boolean twoIsOk = false;
            boolean oneIsOk = false;

            int sumOne = 0;
            int sumTwo = 0;
            int sumThree = 0;

            int end = taskList.size() - 1;
            int start = 0;

            while(!threeIsOk || !twoIsOk)
            {
                if(!threeIsOk)
                {
                    Task tk = taskList.get(end);
                    if(tk.getConsumption() == minAvg || tk.getConsumption() == maxAvg)
                    {
                        three.add(end);
                        threeIsOk = true;
                        end--;
                    }
                    else if(tk.getConsumption() > maxAvg)
                    {
                        return ReturnCodeKeys.E014;
                    }
                }
                else if(!twoIsOk)
                {
                    Task tk = taskList.get(end);
                    if(tk.getConsumption() == minAvg || tk.getConsumption() == maxAvg)
                    {
                        three.add(end);
                        twoIsOk = true;
                        end--;
                    }
                    if(tk.getConsumption() < minAvg)
                    {
                        two.add(end);
                        int v = maxAvg -  tk.getConsumption();
                        Task as = new Task();
                        as.setConsumption(v);
                        int idx = Collections.binarySearch(taskList, as, new TaskCompartorCom());
                        if(idx >= 0)
                        {
                            two.add(idx);
                            twoIsOk = true;
                            break;
                        }
                        if(minAvg != maxAvg)
                        {
                            as.setConsumption(minAvg - tk.getConsumption());
                            idx = Collections.binarySearch(taskList, as, new TaskCompartorCom());
                            if(idx >= 0)
                            {
                                two.add(idx);
                                twoIsOk = true;
                                break;
                            }
                        }

                    }
                }
            }
            for(int i= 0; i< taskList.size(); i++)
            {
                if(two.contains(i) || three.contains(i))
                {
                    continue;
                }
                one.add(i);
            }
//            Collections.sort(one);
//            Collections.sort(two);
//            Collections.sort(three);
            for(int ii : one)
            {
                Task ks = taskList.get(ii);
                nodes.get(2).getTasks().add(ks);
                taskNodeMap.put(ks,nodes.get(2));
            }
            for(int ii : two)
            {
                Task ks = taskList.get(ii);
                nodes.get(1).getTasks().add(ks);
                taskNodeMap.put(ks,nodes.get(1));
            }
            for(int ii : three)
            {
                Task ks = taskList.get(ii);
                nodes.get(0).getTasks().add(ks);
                taskNodeMap.put(ks,nodes.get(0));
            }


        }
        /*if(queue != null)
        {
            queue= new LinkedBlockingQueue();
        }*/
       /* //按照两个node节点调度
        if(nodes.size()%2 == 0)
        {
            //任务值一样
            if(taskList.get(0).getConsumption() == taskList.get(taskList.size() - 0).getConsumption())
            {

            }

            int one = 0;


            int avgTask = taskList.size() / nodes.size();
            List<List<Task>> ones = new ArrayList<>();
            List<List<Task>> twos = new ArrayList<>();
            for(int i = 0; i < taskList.size(); i++)
            {
                List<Task> numOne = new ArrayList<>();
                List<Task> numTwo = new ArrayList<>();
                if((numOne.size() + numTwo.size()) < (i+1))
                {
                    if(numOne.size() == numTwo.size())
                    {

                    }
                }
            }
        }
*/
        //获取到

        return ReturnCodeKeys.E013;
    }




    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        //对所有的节点排序
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(taskNodeMap.keySet());
        Collections.sort(taskList, new TaskCompartorTaskId());
        for(Task t : taskList)
        {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(t.getTaskId());
            Node node = taskNodeMap.get(t);
            taskInfo.setNodeId(node.getNodeId());
            tasks.add(taskInfo);
        }

        return ReturnCodeKeys.E015;
    }

}
