/**
 * Игра Крестики-нолики на поле 3х3
 */

package Controler

import view.TicTacGUI
import java.awt.Color
import java.awt.event.*;
import javax.swing.JButton

class TicTacEngine internal constructor(var ticTacGUI: TicTacGUI, var ticTacComputer: TicTacComputer): ActionListener {

    /**
     * Этот метод будет обрабатывать все события
     * @param e нажатая кнопка
     */
    override fun actionPerformed(e: ActionEvent) {
         val theButton = e.source as JButton

        //Это кнопка Новая игра?
        if (theButton === ticTacGUI.newGameButton) {
            for (i in ticTacGUI.squares.indices) {
                ticTacGUI.squares[i]!!.isEnabled = true
                ticTacGUI.squares[i]!!.text = ""
                ticTacGUI.squares[i]!!.background = Color.ORANGE
            }
            ticTacGUI.emptySquaresLeft = 9
            ticTacGUI.score.text = "Ваш ход"
            ticTacGUI.newGameButton.isEnabled = true
            return
        }
        var winner = ""

        //Это одна из клеток?
        for (square in ticTacGUI.squares) {
            if (theButton === square) {
                square.text = "X"
                winner = ticTacComputer.lookForWinner()
                if ("" != winner) {
                    ticTacComputer.endTheGame()
                } else {
                    ticTacComputer.computerMove()
                    winner = ticTacComputer.lookForWinner()
                    if ("" != winner) {
                       ticTacComputer.endTheGame()
                    }
                }
                break
            }
        } //Конец цикла for
        if (winner == "X") {
           ticTacGUI.score.text = "Вы выиграли!"
        } else if (winner == "0") {
           ticTacGUI.score.text = "Вы проиграли!"
        } else if (winner == "T") {
           ticTacGUI.score.text = "Ничья!"
        }
    } //Конец метода actionPerformed
}