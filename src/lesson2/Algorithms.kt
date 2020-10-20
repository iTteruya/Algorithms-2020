@file:Suppress("UNUSED_PARAMETER")

package lesson2

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
//Трудоемкость - O(N)
//Ресурсоемкость - O(1)
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    var res = 0
    for (i in 1..menNumber) {
        res = (res + choiceInterval) % i;
    }
    return res + 1
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
//Трудоемкость - O(first.length * second.length)
//Ресурсоемкость - O(first.length * second.length)
fun longestCommonSubstring(first: String, second: String): String {
    if (first == "" || second == "") return ""
    var maxLength = 0
    var maxIndex = 0
    val matrix = Array(first.length) { IntArray(second.length) }
    for (i in first.indices) {
        for (j in second.indices) {
            if (first[i] == second[j]) {
                if (i == 0 || j == 0) matrix[i][j] = 1
                else matrix[i][j] = matrix[i - 1][j - 1] + 1
                if (matrix[i][j] > maxLength) {
                    maxLength = matrix[i][j]
                    maxIndex = i
                }
            }
        }
    }
    return first.substring(maxIndex - maxLength + 1..maxIndex)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
//Трудоемкость - O(N*log(log(N)))
//Ресурсоемкость - O(N)
fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) return 0
    if (limit == 2) return 1
    val grid = IntArray(limit + 1)
    grid[1] = 0
    var i = 2
    while (i <= limit) {
        grid[i] = 1
        i++
    }
    i = 2
    while (i * i <= limit) {
        if (grid[i] == 1) {
            var j = i * i
            while (j <= limit) {
                grid[j] = 0
                j += i
            }
        }
        i++
    }
    return grid.sum()
}
