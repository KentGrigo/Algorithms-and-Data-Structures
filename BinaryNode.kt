import java.util.*

// Note intended for concurrency
data class BinarySearchTree(
    var root: BinaryNode?
) {
    fun insert(newElement: BinaryNode) {
        var previousTree: BinaryNode? = null
        var currentTree: BinaryNode? = root
        while (currentTree != null) {
            previousTree = currentTree
            if (newElement.key < currentTree.key) {
                currentTree = currentTree.left
            } else {
                currentTree = currentTree.right
            }
        }
        newElement.parent = previousTree
        if (previousTree == null) {
            root = newElement
        } else if (newElement.key < previousTree.key) {
            previousTree.left = newElement
        } else {
            previousTree.right = newElement
        }
    }

    private fun transplant(oldElement: BinaryNode, newElement: BinaryNode?) {
        if (oldElement.parent == null) {
            root = newElement
        } else if (oldElement == oldElement.parent!!.left) {
            oldElement.parent!!.left = newElement
        } else {
            oldElement.parent!!.right = newElement
        }

        if (newElement != null) {
            newElement.parent = oldElement.parent
        }
    }

    fun delete(oldElement: BinaryNode) {
        if (oldElement.left == null) {
            transplant(oldElement, oldElement.right)
        } else if (oldElement.right == null) {
            transplant(oldElement, oldElement.left)
        } else {
            val substitute = oldElement.right!!.minimum()
            if (substitute.parent != oldElement) {
                transplant(substitute, substitute.right)
                substitute.right = oldElement.right
                substitute.right!!.parent = substitute
            }
            transplant(oldElement, substitute)
            substitute.left = oldElement.left
            substitute.left!!.parent = substitute
        }
    }

    fun print() {
        fun helper(x: BinaryNode?, indentation: String) {
            if (x == null) {
                println("${indentation}x")
            } else {
                helper(x.left, "$indentation  ")
                println(indentation + x.key)
                helper(x.right, "$indentation  ")
            }
        }
        helper(root, "")
    }
}

data class BinaryNode(
    var parent: BinaryNode?,
    val key: Int,
    var left: BinaryNode?,
    var right: BinaryNode?
) : Iterable<BinaryNode> {
    fun preorderTraversal(function: (Int) -> Unit) {
        function(key)
        left?.preorderTraversal(function)
        right?.preorderTraversal(function)
    }

    fun inorderTraversal(function: (Int) -> Unit) {
        left?.inorderTraversal(function)
        function(key)
        right?.inorderTraversal(function)
    }

    fun postorderTraversal(function: (Int) -> Unit) {
        left?.postorderTraversal(function)
        right?.postorderTraversal(function)
        function(key)
    }

    fun breadthFirstTraversal(function: (Int) -> Unit) {
        val forest = LinkedList<BinaryNode>()
        forest.add(this)
        while (forest.isNotEmpty()) {
            val tree = forest.pop()
            function(tree.key)
            tree.left?.let { forest.add(it) }
            tree.right?.let { forest.add(it) }
        }
    }

    fun recursiveSearch(key: Int): BinaryNode? {
        if (this.key == key) {
            return this
        } else if (key < this.key && left != null) {
            return left!!.recursiveSearch(key)
        } else if (this.key < key && right != null) {
            return right!!.recursiveSearch(key)
        } else {
            return null
        }
    }

    fun iterativeSearch(key: Int): BinaryNode? {
        var currentTree: BinaryNode? = this
        while (currentTree != null && currentTree.key != key) {
            if (key < this.key) {
                currentTree = currentTree.left
            } else {
                currentTree = currentTree.right
            }
        }
        return currentTree
    }

    fun minimum(): BinaryNode {
        var currentTree: BinaryNode? = this
        while (currentTree?.left != null) {
            currentTree = currentTree.left
        }
        return currentTree!!
    }

    fun maximum(): BinaryNode {
        var currentTree: BinaryNode? = this
        while (currentTree?.right != null) {
            currentTree = currentTree.right
        }
        return currentTree!!
    }

    fun predecessor(): BinaryNode? {
        if (left != null) {
            return left!!.maximum()
        }
        var previousTree: BinaryNode = this
        var currentTree: BinaryNode? = this.parent
        while (currentTree != null && previousTree == currentTree.left) {
            previousTree = currentTree
            currentTree = currentTree.parent
        }
        return currentTree
    }

    fun successor(): BinaryNode? {
        if (right != null) {
            return right!!.minimum()
        }
        var previousTree: BinaryNode = this
        var currentTree: BinaryNode? = this.parent
        while (currentTree != null && previousTree == currentTree.right) {
            previousTree = currentTree
            currentTree = currentTree.parent
        }
        return currentTree
    }

    override fun iterator(): Iterator<BinaryNode> {
        val first = minimum()
        return object : Iterator<BinaryNode> {
            var currentTree: BinaryNode? = first

            override fun hasNext(): Boolean {
                return currentTree != null
            }

            override fun next(): BinaryNode {
                val result = currentTree
                currentTree = currentTree?.successor()
                return result!!
            }
        }
    }
}


fun main() {
    fun i2n(key: Int): BinaryNode = BinaryNode(null, key, null, null)

    val five = i2n(5)
    val three = i2n(3)
    val two = i2n(2)
    val one = i2n(1)
    val four = i2n(4)
    val eight = i2n(8)

    val tree = BinarySearchTree(null)
    tree.insert(five)
    tree.insert(three)
    tree.insert(two)
    tree.insert(one)
    tree.insert(four)
    tree.insert(eight)
    tree.delete(five)
    tree.delete(two)
    tree.print()
}