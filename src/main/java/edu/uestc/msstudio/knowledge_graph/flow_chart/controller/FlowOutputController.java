package edu.uestc.msstudio.knowledge_graph.flow_chart.controller;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import edu.uestc.msstudio.knowledge_graph.flow_chart.entity.GraphNode;
import edu.uestc.msstudio.knowledge_graph.flow_chart.utils.UMLutils;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping(path = "/graph")
public class FlowOutputController
{
    private static final String startUML = "@startuml\n";
    private static final String endUML = "@enduml\n";

    @GetMapping(path = "/info")
    public String getStatics()
    {
        String target = "get the count of the table";
        return target;
    }

    @GetMapping(path = "/single")
    public GraphNode getGraphById(@RequestParam long id)
    {
        GraphNode target = new GraphNode();
        target.setNode_id(1);
        target.setText("start painting");
        target.setProceed("1,2,3");
        target.setFollowing("4,5");

        return target;
    }

    @GetMapping(path = "/output")
    public String getTheGraph()
            throws IOException
    {
        StringBuilder sourcebuilder = new StringBuilder();
        sourcebuilder.append(startUML);
        sourcebuilder.append("Bob -> Alice : hello\n");
        sourcebuilder.append(endUML);

        String source = sourcebuilder.toString();
        return UMLutils.drawSVG(source);
    }
}
