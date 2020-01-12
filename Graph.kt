import java.util.*

data class Graph(
    val nodes: List<Node>,
    val nodeToAdjacentNodes: Map<Int, List<Node>>
) {
    fun BST(startingNode: Node) {
        for (node in nodes) {
            if (node == startingNode) continue
            node.color = Color.WHITE
            node.discoveringTime = Int.MAX_VALUE
            node.predecessor = null
        }
        startingNode.color = Color.GRAY
        startingNode.discoveringTime = 0
        startingNode.predecessor = null
        val queue = LinkedList<Node>()
        queue.add(startingNode)
        while (queue.isNotEmpty()) {
            val currentNode = queue.remove()
            val entry = nodes.indexOf(currentNode)
            val adjacentNodes = nodeToAdjacentNodes.getValue(entry)
            for (adjacentNode in adjacentNodes) {
                if (adjacentNode.color == Color.WHITE) {
                    adjacentNode.color = Color.GRAY
                    adjacentNode.discoveringTime = currentNode.discoveringTime + 1
                    adjacentNode.predecessor = currentNode
                    queue.add(adjacentNode)
                }
            }
            currentNode.color = Color.BLACK
        }
    }

    fun DFS() {
        var time = 0

        fun visit(currentNode: Node) {
            time += 1
            currentNode.discoveringTime = time
            currentNode.color = Color.GRAY
            val adjacentNodes = nodeToAdjacentNodes.getValue(nodes.indexOf(currentNode))
            for (adjacentNode in adjacentNodes) {
                if (adjacentNode.color == Color.WHITE) {
                    adjacentNode.predecessor = currentNode
                    visit(adjacentNode)
                }
            }
            currentNode.color = Color.BLACK
            time += 1
            currentNode.finishingTime = time
        }

        for (node in nodes) {
            node.color = Color.WHITE
            node.predecessor = null
        }

        for (node in nodes) {
            if (node.color == Color.WHITE) {
                visit(node)
            }
        }
    }

    fun printPath(startingNode: Node, endNode: Node) {
        if (startingNode == endNode) {
            println(startingNode)
        } else if (endNode.predecessor == null) {
            println("No path from $startingNode to $endNode exists")
        } else {
            printPath(startingNode, endNode.predecessor!!)
            println(endNode)
        }
    }
}

data class Node(
    val index: Int,
    var color: Color,
    var discoveringTime: Int,
    var finishingTime: Int,
    var predecessor: Node?
)

enum class Color {
    WHITE, GRAY, BLACK
}

fun main() {
    val node0 = Node(0, Color.WHITE, 0, 0, null)
    val node1 = Node(1, Color.WHITE, 0, 0, null)
    val node2 = Node(2, Color.WHITE, 0, 0, null)
    val node3 = Node(3, Color.WHITE, 0, 0, null)
    val node4 = Node(4, Color.WHITE, 0, 0, null)
    val node5 = Node(5, Color.WHITE, 0, 0, null)
    val node6 = Node(6, Color.WHITE, 0, 0, null)
    val node7 = Node(7, Color.WHITE, 0, 0, null)

    val nodes = listOf(node0, node1, node2, node3, node4, node5, node6, node7)

    val nodeToAdjacentNodes = HashMap<Int, List<Node>>()
    nodeToAdjacentNodes[nodes.indexOf(node0)] = listOf(node1, node4)
    nodeToAdjacentNodes[nodes.indexOf(node1)] = listOf(node0, node5)
    nodeToAdjacentNodes[nodes.indexOf(node2)] = listOf(node3, node5, node6)
    nodeToAdjacentNodes[nodes.indexOf(node3)] = listOf(node2, node6, node7)
    nodeToAdjacentNodes[nodes.indexOf(node4)] = listOf(node0)
    nodeToAdjacentNodes[nodes.indexOf(node5)] = listOf(node1, node2, node6)
    nodeToAdjacentNodes[nodes.indexOf(node6)] = listOf(node2, node3, node5, node7)
    nodeToAdjacentNodes[nodes.indexOf(node7)] = listOf(node3, node6)

    val graph = Graph(
        nodes,
        nodeToAdjacentNodes
    )
    graph.BST(node1)
    graph.printPath(node1, node7)

    println()

    graph.DFS()
    graph.printPath(node1, node7)
}