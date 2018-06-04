package edu.uestc.msstudio.knowledge_graph.flow_chart.Repository;

import edu.uestc.msstudio.knowledge_graph.flow_chart.entity.GraphNode;
import org.springframework.data.repository.CrudRepository;

public interface GraphNodeRepo
        extends CrudRepository<GraphNode, Long>
{

}
