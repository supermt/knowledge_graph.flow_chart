package edu.uestc.msstudio.knowledge_graph.flow_chart.entity;

import edu.uestc.msstudio.knowledge_graph.flow_chart.utils.UMLutils;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class GraphNode
{
    @Id
    @GeneratedValue
    private long node_id;
    private String text;
    private String proceed;
    private String following;

    @Enumerated
    private NodeType nodeType = NodeType.NORMAL; // condition?

    public String getFollowing()
    {
        return following;
    }

    public void setFollowing(String following)
    {
        this.following = following;
    }

    public long getNode_id()
    {
        return node_id;
    }

    public void setNode_id(long node_id)
    {
        this.node_id = node_id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getProceed()
    {
        return proceed;
    }

    public void setProceed(String proceed)
    {
        this.proceed = proceed;
    }

    public NodeType getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType)
    {
        this.nodeType = nodeType;
    }

    public List<GraphNode> getSources(Map<Long, GraphNode> totalMap)
    {
        List<FollowingNode> nodeList = UMLutils.processFollowing(getFollowing());
        List<GraphNode> nodes = new ArrayList<>();
        for (FollowingNode node : nodeList) {
            nodes.add(totalMap.get(node.getNode_id()));
        }
        return nodes;
    }

    public enum NodeType
    {
        NORMAL,
        CONDITION
    }
}
