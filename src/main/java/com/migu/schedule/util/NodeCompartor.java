package com.migu.schedule.util;

import java.util.Comparator;

import com.migu.schedule.info.Node;

/**
 * 比较器
 *
 * @author liming
 * @version [版本号, 六月 20, 2018]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NodeCompartor implements Comparator<Node>
{

    @Override
    public int compare(Node node, Node t2)
    {
        return node.getNodeId() - t2.getNodeId();
    }
}
