import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

class CoolGraph {
    private val graph: Graph

    init {
        System.setProperty("org.graphstream.ui", "swing")
        graph = SingleGraph("Sup!")
        graph.setAttribute("ui.quality")
        graph.setAttribute("ui.antialias")
        graph.setAttribute("ui.stylesheet", stylesheet)
    }

    fun display() {
        graph.display()
    }

    fun containsNode(node: Any) = graph.getNode(node.toString()) != null

    fun addNode(node: Any) {
        val nodeS = node.toString()
        graph.addNode(nodeS).setAttribute("ui.label", nodeS)
    }

    fun addEdge(from: Any, to: Any, label: Any) {
        val fromS = from.toString()
        val toS = to.toString()
        graph.addEdge(fromS + toS, fromS, toS).setAttribute("ui.label", label.toString())
    }

    companion object {
        val stylesheet = """
        node {
        	shape: circle;
        	size: 10px;
            text-size: 15;
            text-alignment: above;
        	fill-mode: plain;  
        	fill-color: red;  
        	stroke-mode: plain; 
        	stroke-color: blue; 
        }
        
         edge {
            text-style: bold;
            text-size: 15;
        }
    """.trimIndent()
    }
}
