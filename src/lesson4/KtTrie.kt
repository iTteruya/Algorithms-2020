package lesson4

import java.lang.IllegalStateException
import java.util.*

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    class Node {
        val children: MutableMap<Char, Node> = linkedMapOf()
    }

    private var root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()


    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val letters = element.withZero().toCharArray().toMutableList()
        return delete(letters, root)
    }

    //Трудоемкость - O(N)
    private fun delete(letters: MutableList<Char>, node: Node): Boolean {
        var currentNode = node
        if (letters.isEmpty()) {
            if (currentNode.children.isNotEmpty()) {
                return false
            }
            size--
            return true
        }
        if (currentNode.children[(letters[0])] == null) {
            return false
        } else currentNode = node.children[letters[0]]!!
        val char = letters[0]
        letters.removeAt(0)

        val wordCanBeDeleted = delete(letters, currentNode)

        if (wordCanBeDeleted) {
            node.children.remove(char)
            return node.children.keys.isEmpty()
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = KtTrieIterator()

    inner class KtTrieIterator : MutableIterator<String> {
        private var queue = LinkedList<String>()
        private var removeWasAlreadyCalled = false
        private var nextWasAlreadyCalled = false
        private var lastWord: String? = null
        private val letters = LinkedList<Char>()
        private val nodes = Stack<Node>()
        private var lastLetter: Char? = null


        init {
            if (root.children.keys.isNotEmpty()) {
                nodes.add(root)
                getWord(root, root.children.keys.first())
            }
        }

        //Трудоемкость - O(N)
        //N - количество букв в слове (может быть меньше, если часть текущего слова входит в следующее)
        private fun getWord(parentNode: Node, child: Char?) {
            lastLetter = child
            if (child == null) {
                val letter = letters.pollLast()
                nodes.pop()
                if (nodes.isNotEmpty()) {
                    getWord(
                        nodes.peek(),
                        nodes.peek().children.keys.toList()
                            .getOrNull(nodes.peek().children.keys.indexOf(letter) + 1)
                    )
                }
            } else {
                val currentNode = parentNode.children[child]!!
                if (currentNode.children.isEmpty() && child.toInt() == 0) {
                    queue.add(letters.joinToString(""))
                } else {
                    nodes.add(currentNode)
                    letters.add(child)
                    getWord(currentNode, currentNode.children.keys.first())
                }
            }
        }

        //Трудоемкость - O(1)
        override fun hasNext(): Boolean {
            return (queue.isNotEmpty())
        }

        override fun next(): String {
            if (!hasNext()) throw IllegalStateException()
            nextWasAlreadyCalled = true
            removeWasAlreadyCalled = false
            lastWord = queue.peek()
            getWord(
                nodes.peek(),
                nodes.peek().children.keys.toList()
                    .getOrNull(nodes.peek().children.keys.indexOf(lastLetter) + 1)
            )
            return queue.poll()
        }

        //Трудоемкость - O(N)
        override fun remove() {
            if ((!nextWasAlreadyCalled) || (removeWasAlreadyCalled)) throw IllegalStateException()
            remove(lastWord)
            removeWasAlreadyCalled = true
            nextWasAlreadyCalled = false
        }
    }

}