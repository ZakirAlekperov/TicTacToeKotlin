package controler

import view.TicTacGUI
import java.awt.Color

class TicTacComputer constructor(var ticTacGUI: TicTacGUI) {


    /**
     * Этот метод вызывается после каждого хода, чтобы узнать,
     * есть ли победитель. Он проверяет каждый ряд, колонку и диагональ, чтобы найти
     * три клетки с одинаковыми надписями
     *
     * @return "X", "0", "T" - ничья, "" - еще нет победителя
     */
    fun lookForWinner(): String {


        var theWinner = ""
        ticTacGUI.emptySquaresLeft--
        if (ticTacGUI.emptySquaresLeft == 0) {
            return "T" //Это ничья, Т от tie
        }

        //Проверяем ряд 1
        if (ticTacGUI.squares[0]!!.text != "" && ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[1]!!
                .text && ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[2]!!.text
        ) {
            theWinner = ticTacGUI.squares[0]!!.text
            hightlightWinner(0, 1, 2)
        }
        //Проверяем ряд 2
        if (ticTacGUI.squares[3]!!.text != "" &&ticTacGUI.squares[3]!!.text ==ticTacGUI.squares[4]!!
                .text && ticTacGUI.squares[3]!!.text ==ticTacGUI.squares[5]!!.text
        ) {
            theWinner =ticTacGUI.squares[3]!!.text
            hightlightWinner(3, 4, 5)
        }
        //Проверяем ряд 3
        if (ticTacGUI.squares[6]!!.text != "" &&ticTacGUI.squares[6]!!.text ==ticTacGUI.squares[7]!!
                .text &&ticTacGUI.squares[6]!!.text ==ticTacGUI.squares[8]!!.text
        ) {
            theWinner =ticTacGUI.squares[6]!!.text
            hightlightWinner(6, 7, 8)
        }
        //Проверяем колонку 1
        if (ticTacGUI.squares[0]!!.text != "" &&ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[3]!!
                .text &&ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[6]!!.text
        ) {
            theWinner =ticTacGUI.squares[0]!!.text
            hightlightWinner(0, 3, 6)
        }
        //Проверяем колонку 2
        if (ticTacGUI.squares[1]!!.text != "" &&ticTacGUI.squares[1]!!.text ==ticTacGUI.squares[4]!!
                .text &&ticTacGUI.squares[1]!!.text ==ticTacGUI.squares[7]!!.text
        ) {
            theWinner =ticTacGUI.squares[1]!!.text
            hightlightWinner(1, 4, 7)
        }
        //Проверяем колонку 3
        if (ticTacGUI.squares[2]!!.text != "" &&ticTacGUI.squares[2]!!.text ==ticTacGUI.squares[5]!!
                .text &&ticTacGUI.squares[2]!!.text ==ticTacGUI.squares[8]!!.text
        ) {
            theWinner =ticTacGUI.squares[2]!!.text
            hightlightWinner(2, 5, 8)
        }
        //Проверяем первую диагональ
        if (ticTacGUI.squares[0]!!.text != "" && ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[4]!!
                .text &&ticTacGUI.squares[0]!!.text ==ticTacGUI.squares[8]!!.text
        ) {
            theWinner =ticTacGUI.squares[0]!!.text
            hightlightWinner(0, 4, 8)
        }
        //Проверяем вторую диагональ
        if (ticTacGUI.squares[2]!!.text != "" &&ticTacGUI.squares[2]!!.text ==ticTacGUI.squares[4]!!
                .text &&ticTacGUI.squares[2]!!.text ==ticTacGUI.squares[6]!!.text
        ) {
            theWinner =ticTacGUI.squares[2]!!.text
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
        if (selectedSquare == -1 &&ticTacGUI.squares[4]!!.text == "") {
            selectedSquare = 4
        }
        //Иначе - занимаем случайню клетку
        if (selectedSquare == -1) {
            selectedSquare = randomSquare
        }
        ticTacGUI.squares[selectedSquare]!!.text = "0"
    }

    /**
     * Этот метод проверяет каждый ряд, колонку и диагональ, чтобы узнать, есть ли в ней две клетки
     * с одинаковыми надписями и пустой клеткой.
     * @param  передается Х - для пользователя и 0 для компьютера
     * @return количество свободных клеток
     * или -1 если не найдено две клетки с одинаковым надписями
     */
    fun findEmptySquare(player: String): Int {
        val weight = IntArray(9)
        for (i in weight.indices) {
            if (ticTacGUI.squares[i]!!.text == "0") {
                weight[i] = -1
            } else if (ticTacGUI.squares[i]!!.text == "X") {
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
                if (ticTacGUI.squares[selectedSquare]!!.text == "") {
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
       ticTacGUI.squares[win1]!!.background = Color.CYAN
        ticTacGUI.squares[win2]!!.background = Color.CYAN
       ticTacGUI.squares[win3]!!.background = Color.CYAN
    }

    //делаем недоступными клеткии доступной кнопку "Новая игра"
    fun endTheGame() {
       ticTacGUI.newGameButton.isEnabled = true
        for (i in ticTacGUI.squares.indices) {
           ticTacGUI.squares[i]!!.isEnabled = false
        }
    }
}