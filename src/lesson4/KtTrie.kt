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


        init {
            if (root.children.keys.isNotEmpty()) {
                val word = LinkedList<Char>()
                root.children.keys.forEach { getWords(root, it, word) }
            }
        }

        private fun getWords(parentNode: Node, parent: Char, letters: LinkedList<Char>) {
            val currentNode = parentNode.children[parent]!!
            val word = LinkedList<Char>()
            word.addAll(letters)
            if (currentNode.children.isEmpty() && parent.toInt() == 0) {
                queue.add(word.joinToString(""))
            } else {
                word.add(parent)
                currentNode.children.keys.forEach { getWords(currentNode, it, word) }
            }
        }

        override fun hasNext(): Boolean {
            return (queue.isNotEmpty())
        }

        override fun next(): String {
            if (!hasNext()) throw IllegalStateException()
            nextWasAlreadyCalled = true
            removeWasAlreadyCalled = false
            lastWord = queue.peek()
            return queue.poll()
        }

        override fun remove() {
            if ((!nextWasAlreadyCalled) || (removeWasAlreadyCalled)) throw IllegalStateException()
            remove(lastWord)
            removeWasAlreadyCalled = true
            nextWasAlreadyCalled = false
        }
    }

}