import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cindy Liu
 */
public class GraphViz {
    
    private final Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
     
    GraphViz(String[] s){
        createGraph(s);
    }
    
    private void createGraph(String[] s){
        // if empty return
        boolean neighbour= false;
        int i = 0;
        String curr_v = "";
        String curr_n = "";
        
        while(i < s.length){
            if(!neighbour){
                curr_v = s[i].substring(1);
                
                if(!g.containsVertex(curr_v)){
                    g.addVertex(curr_v);
                }
                
                neighbour = !neighbour;
                ++i;
            }
            else{
                // taking the opening paranthesis off
                s[i] = s[i].substring(1);
                curr_n = s[i];
                
                // adding edges
                while(!(curr_n.substring(curr_n.length() - 1).equals(")"))){
                    if(!g.containsVertex(curr_n)){
                        g.addVertex(curr_n);
                    }
                    g.addEdge(curr_v, curr_n);
                    
                    ++i;
                    curr_n = s[i];
                }
                
                if(curr_n.length() > 2){
                    curr_n = curr_n.substring(0,curr_n.length()-2);
                    if(!g.containsVertex(curr_n)){
                        g.addVertex(curr_n);
                    }
                    g.addEdge(curr_v, curr_n);
                }
                
                neighbour = !neighbour;
                ++i;
            }
        }
    }
    
    public void display(){
        JGraphXAdapter<String, DefaultEdge> graphAdapter 
                = new JGraphXAdapter<>(g);
        mxGraphComponent component = new mxGraphComponent(graphAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        
        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(graphAdapter.getDefaultParent());
        
        // making the JFrame
        JFrame frame = new JFrame();
        frame.setPreferredSize(DEFAULT_SIZE);
        frame.getContentPane().add(component);
        frame.pack();
        frame.setVisible(true);
    }
}
