/**
 * Игра крестики-нолики на доске 3Х3
 */
package View

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class TicBase : ActionListener {
    var squares = arrayOfNulls<JButton>(9)
    var frame: JFrame
    var newGameButton: JButton
    var windowsContent = JPanel()
    var playing_field = JPanel()
    var score: JLabel
    var emptySquaresLeft = 9

    init {
        val borderLayout = BorderLayout()
        windowsContent.layout = borderLayout
        windowsContent.background = Color.CYAN

        //Создаем шрифты для кнопок и метка счета
        val labelFont = Font(Font.MONOSPACED, Font.BOLD, 30)
        val buttonFont = Font(Font.MONOSPACED, Font.BOLD, 40)
        newGameButton = JButton("Новая игра")
        newGameButton.font = buttonFont

        //Создаем метку для счета и задаем ее шрифт
        score = JLabel()
        score.text = "Ваш ход"
        score.font = labelFont
        windowsContent.add("North", newGameButton)
        windowsContent.add("South", score)
        for (i in 0..8) {
            squares[i] = JButton("")
        }

        //Размещаем кнопки на игровом поле
        val gridLayout = GridLayout(3, 3)
        playing_field.layout = gridLayout
        for (i in squares.indices) {
            playing_field.add(squares[i])
        }
        windowsContent.add("Center", playing_field)
        frame = JFrame("Крестики-нолики")
        frame.contentPane = windowsContent
        frame.setSize(300, 400)
        frame.isVisible = true
        for (i in squares.indices) {
            squares[i]!!.addActionListener(this)
            squares[i]!!.background = Color.ORANGE
        }
        newGameButton.addActionListener(this)
    }

    /**
     * Этот метод будет обрабатывать все события
     *
     * @param e объект
     */
    override fun actionPerformed(e: ActionEvent) {
        val theButton = e.source as JButton

        //Это кнопка Новая игра?
        if (theButton === newGameButton) {
            for (i in squares.indices) {
                squares[i]!!.isEnabled = true
                squares[i]!!.text = ""
                squares[i]!!.background = Color.ORANGE
            }
            emptySquaresLeft = 9
            score.text = "Ваш ход"
            newGameButton.isEnabled = true
            return
        }
        var winner = ""

        //Это одна из клеток?
        for (square in squares) {
            if (theButton === square) {
                square.text = "X"
                winner = lookForWinner()
                if ("" != winner) {
                    endTheGame()
                } else {
                    computerMove()
                    winner = lookForWinner()
                    if ("" != winner) {
                        endTheGame()
                    }
                }
                break
            }
        } //Конец цикла for
        if (winner == "X") {
            score.text = "Вы выиграли!"
        } else if (winner == "0") {
            score.text = "Вы проиграли!"
        } else if (winner == "T") {
            score.text = "Ничья!"
        }
    } //Конец метода actionPerformed

    /**
     * Этот метод вызывается после каждого хода, чтобы узнать,
     * есть ли победитель. Он проверяет каждый ряд, колонку и диагональ, чтобы найти
     * три клетки с одинаковыми надписями
     *
     * @return "X", "0", "T" - ничья, "" - еще нет победителя
     */
    fun lookForWinner(): String {
        var theWinner = ""
        emptySquaresLeft--
        if (emptySquaresLeft == 0) {
            return "T" //Это ничья, Т от tie
        }

        //Проверяем ряд 1
        if (squares[0]!!.text != "" && squares[0]!!.text == squares[1]!!
                .text && squares[0]!!.text == squares[2]!!.text
        ) {
            theWinner = squares[0]!!.text
            hightlightWinner(0, 1, 2)
        }
        //Проверяем ряд 2
        if (squares[3]!!.text != "" && squares[3]!!.text == squares[4]!!
                .text && squares[3]!!.text == squares[5]!!.text
        ) {
            theWinner = squares[3]!!.text
            hightlightWinner(3, 4, 5)
        }
        //Проверяем ряд 3
        if (squares[6]!!.text != "" && squares[6]!!.text == squares[7]!!
                .text && squares[6]!!.text == squares[8]!!.text
        ) {
            theWinner = squares[6]!!.text
            hightlightWinner(6, 7, 8)
        }
        //Проверяем колонку 1
        if (squares[0]!!.text != "" && squares[0]!!.text == squares[3]!!
                .text && squares[0]!!.text == squares[6]!!.text
        ) {
            theWinner = squares[0]!!.text
            hightlightWinner(0, 3, 6)
        }
        //Проверяем колонку 2
        if (squares[1]!!.text != "" && squares[1]!!.text == squares[4]!!
                .text && squares[1]!!.text == squares[7]!!.text
        ) {
            theWinner = squares[1]!!.text
            hightlightWinner(1, 4, 7)
        }
        //Проверяем колонку 3
        if (squares[2]!!.text != "" && squares[2]!!.text == squares[5]!!
                .text && squares[2]!!.text == squares[8]!!.text
        ) {
            theWinner = squares[2]!!.text
            hightlightWinner(2, 5, 8)
        }
        //Проверяем первую диагональ
        if (squares[0]!!.text != "" && squares[0]!!.text == squares[4]!!
                .text && squares[0]!!.text == squares[8]!!.text
        ) {
            theWinner = squares[0]!!.text
            hightlightWinner(0, 4, 8)
        }
        //Проверяем вторую диагональ
        if (squares[2]!!.text != "" && squares[2]!!.text == squares[4]!!
                .text && squares[2]!!.text == squares[6]!!.text
        ) {
            theWinner = squares[2]!!.text
            hightlightWinner(2, 4, 6)
        }
        return theWinner
    }

    /**
     * Этот метод принимает набор правил, чтобы найти лучший компьютерный ход.
     * Если хороштй ход не найден, выбирается случайная клетка
     */
    fun computerMove() {
        var selectedSquare: Int

        //Сначала компьютер пытается найти пустую клетку
        //рядом с двумя клетками с ноликами, чтобы выиграть
        selectedSquare = findEmptySquare("0")
        //Если он не может найти два нолика, то хотя бы
        //Попытается не дать выиграть опоненту
        if (selectedSquare == -1) {
            selectedSquare = findEmptySquare("X")
        }
        //Если selectedSquare по прежнему равен -1, то попытается занять центр
        if (selectedSquare == -1 && squares[4]!!.text == "") {
            selectedSquare = 4
        }
        //Иначе - занимаем случайню клетку
        if (selectedSquare == -1) {
            selectedSquare = randomSquare
        }
        squares[selectedSquare]!!.text = "0"
    }

    /**
     * Этот метод проверяет каждый ряд, колонку и диагональ чтобы узнать, есть ли в ней две клетки
     * с одинаковыми надписями и пустой клеткой.
     *
     * @param args передается Х - для пользователя и 0 для компьютера
     * @return количество свободных клеток
     * или -1 если не найдено две клетки с одинаковым надписями
     */
    fun findEmptySquare(player: String): Int {
        val weight = IntArray(9)
        for (i in weight.indices) {
            if (squares[i]!!.text == "0") {
                weight[i] = -1
            } else if (squares[i]!!.text == "X") {
                weight[i] = 1
            } else {
                weight[i] = 0
            }
        }
        val twoWeights = if (player == "0") -2 else 2

        //Проверяем есть ли в ряду 1 две одинаковые клетки и одна пустая
        if (weight[0] + weight[1] + weight[2] == twoWeights) {
            return if (weight[0] == 0) {
                0
            } else if (weight[1] == 0) {
                1
            } else {
                2
            }
        }
        //Проверяем есть ли в ряду 2 две одинаковые клетки и одна пустая
        if (weight[3] + weight[4] + weight[5] == twoWeights) {
            return if (weight[3] == 0) {
                3
            } else if (weight[4] == 0) {
                4
            } else {
                5
            }
        }
        //Проверяем есть ли в ряду 3 две одинаковые клетки и одна пустая
        if (weight[6] + weight[7] + weight[8] == twoWeights) {
            return if (weight[6] == 0) {
                6
            } else if (weight[7] == 0) {
                7
            } else {
                8
            }
        }
        //Проверяем есть ли в колонке 1 две одинаковые клетки и одна пустая
        if (weight[0] + weight[3] + weight[6] == twoWeights) {
            return if (weight[0] == 0) {
                0
            } else if (weight[3] == 0) {
                3
            } else {
                6
            }
        }

        //Проверяем есть ли в колонке 2 две одинаковые клетки и одна пустая
        if (weight[1] + weight[4] + weight[7] == twoWeights) {
            return if (weight[1] == 0) {
                1
            } else if (weight[4] == 0) {
                4
            } else {
                7
            }
        }

        //Проверяем есть ли в колонке 3 две одинаковые клетки и одна пустая
        if (weight[2] + weight[5] + weight[8] == twoWeights) {
            return if (weight[2] == 0) {
                2
            } else if (weight[5] == 0) {
                5
            } else {
                8
            }
        }

        //Проверяем есть ли в диагонали 1 две одинаковые клетки и одна пустая
        if (weight[0] + weight[4] + weight[8] == twoWeights) {
            return if (weight[0] == 0) {
                0
            } else if (weight[4] == 0) {
                4
            } else {
                8
            }
        }

        //Проверяем есть ли в диагонали 2 две одинаковые клетки и одна пустая
        return if (weight[2] + weight[4] + weight[6] == twoWeights) {
            if (weight[2] == 0) {
                2
            } else if (weight[4] == 0) {
                4
            } else {
                6
            }
        } else -1
        //Не еайдено двух соседних одинаковых клеток
    }// чтобы выйти из цикла
    //конец метода getRandomSquare()
    /**
     * Этот метод выбирает любую пустую клетку
     * @return случайно выбранный номер клетки
     */
    val randomSquare: Int
        get() {
            var gotEmptySquare = false
            var selectedSquare = -1
            do {
                selectedSquare = (Math.random() * 9).toInt()
                if (squares[selectedSquare]!!.text == "") {
                    gotEmptySquare = true // чтобы выйти из цикла
                }
            } while (!gotEmptySquare)
            return selectedSquare
        }

    /**
     * Этот метод выделяет выигрывшую линию
     * @param args клетки которые нужны выделить
     */
    fun hightlightWinner(win1: Int, win2: Int, win3: Int) {
        squares[win1]!!.background = Color.CYAN
        squares[win2]!!.background = Color.CYAN
        squares[win3]!!.background = Color.CYAN
    }

    //делаем недоступными клеткии доступной кнопку "Новая игра"
    fun endTheGame() {
        newGameButton.isEnabled = true
        for (i in squares.indices) {
            squares[i]!!.isEnabled = false
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ticBase = TicBase()
        }
    }
}