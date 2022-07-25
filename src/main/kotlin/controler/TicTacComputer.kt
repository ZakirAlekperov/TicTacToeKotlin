/**
 * Основной контроллер игры
 */
package controler

import view.TicTacGUI
import java.awt.Color

class TicTacComputer constructor(private var ticTacGUI: TicTacGUI) {

    private val weight = IntArray(9)

    /**
     * Этот метод вызывается после каждого хода, чтобы узнать,
     * есть ли победитель. Он проверяет каждый ряд, колонку и диагональ, чтобы найти
     * три клетки с одинаковыми надписями
     *
     * @return "X", "0", "T" - ничья, "" - еще нет победителя
     */
    fun lookForWinner(): String {
        ticTacGUI.emptySquaresLeft--
        if (ticTacGUI.emptySquaresLeft == 0) {
            return "T" //Это ничья, Т от tie
        }

        //Проверяем ряд 1
        if (lineCheck(0, 1, 2) != "") {
            return lineCheck(0, 1, 2)
        }
        //Проверяем ряд 2
        if (lineCheck(3, 4, 5) != "") {
            return lineCheck(3, 4, 5)
        }
        //Проверяем ряд 3
        if (lineCheck(6, 7, 8) != "") {
            return lineCheck(6, 7, 8)
        }
        //Проверяем колонку 1
        if (lineCheck(0, 3, 6) != "") {
            return lineCheck(0, 3, 6)
        }
        //Проверяем колонку 2
        if (lineCheck(1, 4, 7) != "") {
            return lineCheck(1, 4, 7)
        }
        //Проверяем колонку 3
        if (lineCheck(2, 5, 8) != "") {
            return lineCheck(2, 5, 8)
        }
        //Проверяем первую диагональ
        if (lineCheck(0, 4, 8) != "") {
            return lineCheck(0, 4, 8)
        }
        //Проверяем вторую диагональ
        if (lineCheck(2, 4, 6) != "") {
            return lineCheck(2, 4, 6)
        }
        return ""
    }

    /**
     * Этот метод принимает набор правил, чтобы найти лучший компьютерный ход.
     * Если хороший ход не найден, выбирается случайная клетка
     */
    fun computerMove() {
        var selectedSquare: Int

        //Сначала компьютер пытается найти пустую клетку
        //рядом с двумя клетками с ноликами, чтобы выиграть
        selectedSquare = findEmptySquare("0")
        //Если он не может найти два нолика, то хотя бы
        //попытается не дать выиграть оппоненту
        if (selectedSquare == -1) {
            selectedSquare = findEmptySquare("X")
        }
        //Если selectedSquare по-прежнему равен -1, то попытается занять центр
        if (selectedSquare == -1 && ticTacGUI.squares[4]!!.text == "") {
            selectedSquare = 4
        }
        //Иначе - занимаем случайною клетку
        if (selectedSquare == -1) {
            selectedSquare = randomSquare
        }
        ticTacGUI.squares[selectedSquare]!!.text = "0"
    }

    /**
     * Этот метод проверяет каждый ряд, колонку и диагональ, чтобы узнать, есть ли в ней две клетки
     * с одинаковыми надписями и пустой клеткой.
     * @param  player X - для пользователя и 0 для компьютера
     * @return номер свободной клетки
     * или -1 если не найдено две клетки с одинаковым надписями
     */
    private fun findEmptySquare(player: String): Int {
        weightInitialisation()
        //Константа равна -2 если в проверяемой строке два 0 или 2 если два Х
        val twoWeights = if (player == "0") -2 else 2

        //Проверяем есть ли в ряду 1 две одинаковые клетки и одна пустая
        if (weightCheck(0, 1, 2, twoWeights) != null) {
            return weightCheck(0, 1, 2, twoWeights)!!
        }
        //Проверяем есть ли в ряду 2 две одинаковые клетки и одна пустая
        if (weightCheck(3, 4, 5, twoWeights) != null) {
            return weightCheck(3, 4, 5, twoWeights)!!
        }
        //Проверяем есть ли в ряду 3 две одинаковые клетки и одна пустая
        if (weightCheck(6, 7, 8, twoWeights) != null) {
            return weightCheck(6, 7, 8, twoWeights)!!
        }
        //Проверяем есть ли в колонке 1 две одинаковые клетки и одна пустая
        if (weightCheck(0, 3, 6, twoWeights) != null) {
            return weightCheck(0, 3, 6, twoWeights)!!
        }
        //Проверяем есть ли в колонке 2 две одинаковые клетки и одна пустая
        if (weightCheck(1, 4, 7, twoWeights) != null) {
            return weightCheck(1, 4, 7, twoWeights)!!
        }
        //Проверяем есть ли в колонке 3 две одинаковые клетки и одна пустая
        if (weightCheck(2, 5, 8, twoWeights) != null) {
            return weightCheck(2, 5, 8, twoWeights)!!
        }
        //Проверяем есть ли в диагонали 1 две одинаковые клетки и одна пустая
        if (weightCheck(0, 4, 8, twoWeights) != null) {
            return weightCheck(0, 4, 8, twoWeights)!!
        }
        //Проверяем есть ли в диагонали 2 две одинаковые клетки и одна пустая
        if (weightCheck(2, 4, 6, twoWeights) != null) {
            return weightCheck(2, 4, 6, twoWeights)!!
        }
        //Не найдено двух соседних одинаковых клеток
        return -1

    }

    /**
     * Этот метод выбирает любую пустую клетку
     * @return случайно выбранный номер клетки
     */
    private val randomSquare: Int
        get() {
            var gotEmptySquare = false
            var selectedSquare: Int
            do {
                selectedSquare = (Math.random() * 9).toInt()
                if (ticTacGUI.squares[selectedSquare]!!.text == "") {
                    gotEmptySquare = true // чтобы выйти из цикла
                }

            } while (!gotEmptySquare)
            return selectedSquare
        }

    /**
     * Этот метод выделяет выигравшую линию.
     * Принимает на вход клетки которые нужны выделить
     * @param win1
     * @param win2
     * @param win3
     */
    private fun highlightWinner(win1: Int, win2: Int, win3: Int) {
        ticTacGUI.squares[win1]!!.background = Color.CYAN
        ticTacGUI.squares[win2]!!.background = Color.CYAN
        ticTacGUI.squares[win3]!!.background = Color.CYAN
    }

    /**
     * Метод делает недоступными клетки и доступной кнопку "Новая игра"
     */
    fun endTheGame() {
        ticTacGUI.newGameButton.isEnabled = true
        for (i in ticTacGUI.squares.indices) {
            ticTacGUI.squares[i]!!.isEnabled = false
        }
    }

    /**
     * Метод проверяет есть в ряду три одинаковых символа
     */
    private fun lineCheck(button1: Int, button2: Int, button3: Int): String {
        return if (ticTacGUI.squares[button1]!!.text != "" && ticTacGUI.squares[button2]!!.text == ticTacGUI.squares[button3]!!
                .text && ticTacGUI.squares[button1]!!.text == ticTacGUI.squares[button3]!!.text
        ) {
            highlightWinner(button1, button2, button3)
            ticTacGUI.squares[button1]!!.text

        } else {
            ""
        }
    }

    /**
     * Метод инициализирует массив весов для определения оптимального хода компьютера
     */
    private fun weightInitialisation() {
        for (i in weight.indices) {
            when (ticTacGUI.squares[i]!!.text) {
                "0" -> {
                    weight[i] = -1
                }
                "X" -> {
                    weight[i] = 1
                }
                else -> {
                    weight[i] = 0
                }
            }
        }
    }

    /**
     * Метод возвращает номер пустой клетки в ряду с двумя одинаковыми или null если подходящей клетки нет
     */
    private fun weightCheck(button1: Int, button2: Int, button3: Int, twoWeights: Int): Int? {
        if (weight[button1] + weight[button2] + weight[button3] == twoWeights) {
            return if (weight[button1] == 0) {
                button1
            } else if (weight[button2] == 0) {
                button2
            } else if (weight[button3] == 0) {
                button3
            } else {
                null
            }
        }
        return null
    }
}