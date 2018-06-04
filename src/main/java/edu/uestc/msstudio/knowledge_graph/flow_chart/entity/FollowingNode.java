package edu.uestc.msstudio.knowledge_graph.flow_chart.entity;

public class FollowingNode
{
    public int condition;
    public long node_id;

    public FollowingNode(int condition, long node_id)
    {
        this.condition = condition;
        this.node_id = node_id;
    }

    public int getCondition()
    {
        return condition;
    }

    public void setCondition(int condition)
    {
        this.condition = condition;
    }

    public long getNode_id()
    {
        return node_id;
    }

    public void setNode_id(long node_id)
    {
        this.node_id = node_id;
    }
}
