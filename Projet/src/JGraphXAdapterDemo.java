import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class JGraphXAdapterDemo extends JApplet
{
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultWeightedEdge> jgxAdapter;

    /**
     * An alternative starting point for this demo, to also allow running this applet as an
     * application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        JGraphXAdapterDemo applet = new JGraphXAdapterDemo();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void init()
    {
        BaseDeTweets bd = new BaseDeTweets();
        try {
            bd.ouvrir("foot.txt");
        }
        catch(IOException e){
            System.out.println("Problèem de lecture du fichier");
        }
        // create a JGraphT graph
        ListenableGraph<String, DefaultWeightedEdge> g =
                new DefaultListenableGraph<>(bd.getSubGraph());

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

       /*String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v1);
        g.addEdge(v4, v3);*/

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 10;
        layout.setX0(0);
        layout.setY0(0);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}