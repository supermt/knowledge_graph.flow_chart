package edu.uestc.msstudio.knowledge_graph.flow_chart.utils;

import edu.uestc.msstudio.knowledge_graph.flow_chart.entity.FollowingNode;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UMLutils
{
    public static String drawSVG(String source)
            throws IOException
    {
        SourceStringReader reader = new SourceStringReader(source);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
        os.close();

        return new String(os.toByteArray(), Charset.forName("UTF-8"));
    }

    public static List<FollowingNode> processFollowing(String source)
    {
        List<FollowingNode> result = new ArrayList<>();
        String[] following = source.split(",");
        for (String node : following) {
            node = node.toUpperCase();
            if (node.contains("Y")) {
                // yes condition
                result.add(new FollowingNode(1, Long.valueOf(node.substring(0, node.length() - 1))));
            }
            else if (node.contains("N")) {
                // no condition
                result.add(new FollowingNode(-1, Long.valueOf(node.substring(0, node.length() - 1))));
            }
            else {
                // normal condition
                if (!"".equals(node)) { result.add(new FollowingNode(0, Long.valueOf(node))); }
            }
        }
        return result;
    }
}
