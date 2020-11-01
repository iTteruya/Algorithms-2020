@file:Suppress("UNUSED_PARAMETER")

package lesson7

import java.lang.StringBuilder

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
//Трудоемкость - O(first.length * second.length)
//Ресурсоемкость - O(first.length * second.length)
fun longestCommonSubSequence(first: String, second: String): String {
    val table = Array(first.length + 1) { IntArray(second.length + 1) }
    for (i in first.length - 1 downTo 0) {
        for (j in second.length - 1 downTo 0) {
            if (first[i] == second[j]) {
                table[i][j] = table[i + 1][j + 1] + 1
            } else table[i][j] = table[i + 1][j].coerceAtLeast(table[i][j + 1])
        }
    }
    val sb = StringBuilder()
    var i = 0
    var j = 0
    while (i < first.length && j < second.length) {
        when {
            first[i] == second[j] -> {
                sb.append(first[i])
                i++
                j++
            }
            else -> {
                if (table[i + 1][j] >= table[i][j + 1]) i++ else j++
            }
        }
    }
    return sb.toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
//Трудоемкость - O(N^2)
//Ресурсоемкость - O(N)
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val size = list.size
    if (size <= 1) return list
    val prev = MutableList(size) { -1 }
    val d = MutableList(size) { 1 }
    for (i in 0 until size) {
        for (j in 0 until i) {
            if (list[j] < list[i] && d[j] + 1 > d[i]) {
                d[i] = d[j] + 1
                prev[i] = j
            }
        }
    }

    val length = d.max()
    var position = d.indexOf(length)

    val answer = mutableListOf<Int>()
    while (position != -1) {
        answer.add(0, list[position])
        position = prev[position]
    }
    return answer
}


/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5