package edu.uestc.msstudio.knowledge_graph.flow_chart.controller;

import edu.uestc.msstudio.knowledge_graph.flow_chart.Repository.GraphNodeRepo;
import edu.uestc.msstudio.knowledge_graph.flow_chart.entity.FollowingNode;
import edu.uestc.msstudio.knowledge_graph.flow_chart.entity.GraphNode;
import edu.uestc.msstudio.knowledge_graph.flow_chart.utils.UMLutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping(path = "/graph")
public class FlowOutputController
{
    private static final String startUML = "@startuml\nstart\n";
    private static final String endUML = "end\n@enduml\n";
    @Autowired
    private GraphNodeRepo nodeRepo;

    @GetMapping(path = "/info")
    public String getStatics()
    {
        String target = "get the count of the table";
        return target;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<GraphNode> getAllNodes()
    {
        // This returns a JSON or XML with the users
        return nodeRepo.findAll();
    }

    @GetMapping(path = "/single")
    public GraphNode getGraphById(@RequestParam long id)
    {
        GraphNode target = new GraphNode();
        target.setNode_id(1);
        target.setText("start painting");
        target.setFollowing("2Y,3N");
        return target;
    }

    @GetMapping(path = "/output")
    public String getTheGraph()
            throws IOException
    {

        StringBuilder sourcebuilder = new StringBuilder();
        sourcebuilder.append("http://yuml.me/diagram/plain/activity/");
        Iterable<GraphNode> result = nodeRepo.findAll();
        Map<Long, GraphNode> target = new HashMap();
        Queue<GraphNode> node_queue = new ConcurrentLinkedQueue<>();

        result.forEach(node -> {
            target.put(node.getNode_id(), node);
        });
        node_queue.add(result.iterator().next());

        GraphNode temp = null;
        while (node_queue.size() != 0) {
            temp = node_queue.poll();
            long proceed = temp.getNode_id();

            switch (temp.getNodeType()) {
                case CONDITION: {
                    sourcebuilder.append(String.format("(%s)-><%d>", temp.getText(), temp.getNode_id()));
                    sourcebuilder.append(",");
                    List<FollowingNode> followingNodeList = UMLutils.processFollowing(temp.getFollowing());
                    for (FollowingNode follow : followingNodeList) {
                        GraphNode followNode = target.get(follow.getNode_id());
                        StringBuilder atom = new StringBuilder();
                        atom.append(String.format("<%d>", temp.getNode_id()));
                        switch (follow.condition) {
                            case 1:
                                atom.append("[Y]");
                                break;
                            case -1:
                                atom.append("[N]");
                                break;
                            case 0:
                                break;
                            default:
                                break;
                        }
                        atom.append("->");
                        atom.append(String.format("(%s)", followNode.getText()));
                        atom.append(",");
                        sourcebuilder.append(atom.toString());
                        node_queue.offer(followNode);
                    }
                    break;
                }
                case NORMAL: {
                    List<FollowingNode> followingNodeList = UMLutils.processFollowing(temp.getFollowing());
                    for (FollowingNode follow : followingNodeList) {
                        GraphNode followNode = target.get(follow.getNode_id());
                        sourcebuilder.append(String.format("(%s)->(%s)", temp.getText(), followNode.getText()));
                        sourcebuilder.append(",");
                        node_queue.offer(followNode);
                        break;
                    }
                }
            }
        }

        sourcebuilder.replace(sourcebuilder.length() - 1, sourcebuilder.length(), ".");
        sourcebuilder.append("svg");

        return sourcebuilder.toString();
    }
}
