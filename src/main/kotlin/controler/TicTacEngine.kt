/**
 * Этот класс отвечает за обработку событий в игре
 */

package controler

import view.TicTacGUI
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import javax.swing.JButton

class TicTacEngine internal constructor(private var ticTacGUI: TicTacGUI, private var ticTacComputer: TicTacComputer) :
    ActionListener {

    /**
     * Этот метод будет обрабатывать все события
     * @param e нажатая кнопка
     */
    override fun actionPerformed(e: ActionEvent) {
        val theButton = e.source as JButton

        //Это кнопка Новая игра?
        val winner: String = if (theButton === ticTacGUI.newGameButton) {
            newGameButtonClicked()
            ""
        } else {
            playingButtonClicked(theButton)
        }

        //Это одна из клеток?

        when (winner) {
            "X" -> {
                ticTacGUI.score.text = "Вы выиграли!"
            }
            "0" -> {
                ticTacGUI.score.text = "Вы проиграли!"
            }
            "T" -> {
                ticTacGUI.score.text = "Ничья!"
            }
        }
    }

    /**
     * Обработчик нажатия на любую кнопку на игровом поле
     */
    private fun playingButtonClicked(theButton: JButton): String {
        var winner = ""
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
        }
        return winner
    }

    /**
     * Обработчик нажатия на кнопку "Новая игра"
     */
    private fun newGameButtonClicked() {
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
}