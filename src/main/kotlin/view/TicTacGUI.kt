/**
 * Игра крестики-нолики на доске 3Х3
 */
package view

import controler.TicTacComputer
import controler.TicTacEngine
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class TicTacGUI {
    var squares = arrayOfNulls<JButton>(9)
    private var frame: JFrame
    var newGameButton: JButton
    private var windowsContent = JPanel()
    private var playingField = JPanel()
    var score: JLabel
    var emptySquaresLeft = 9
    private val ticTacEngine = TicTacEngine(this, TicTacComputer(this))

    init {

        val borderLayout = BorderLayout()
        windowsContent.layout = borderLayout
        windowsContent.background = Color.CYAN

        //Создаем шрифты для кнопок и метки счета
        val labelFont = Font(Font.MONOSPACED, Font.BOLD, 30)
        val newGameButtonFont = Font(Font.MONOSPACED, Font.BOLD, 30)
        val buttonFont = Font(Font.MONOSPACED, Font.BOLD, 60)
        newGameButton = JButton("Новая игра")
        newGameButton.font = newGameButtonFont

        //Создаем метку для счета и задаем ее шрифт
        score = JLabel()
        score.text = "Ваш ход"
        score.font = labelFont
        windowsContent.add("North", newGameButton)
        windowsContent.add("South", score)
        for (i in 0..8) {
            squares[i] = JButton("")
            squares[i]!!.font = buttonFont
        }

        //Размещаем кнопки на игровом поле
        val gridLayout = GridLayout(3, 3)
        playingField.layout = gridLayout
        for (i in squares.indices) {
            playingField.add(squares[i])
        }
        windowsContent.add("Center", playingField)
        frame = JFrame("Крестики-нолики")
        frame.contentPane = windowsContent
        frame.setSize(500, 600)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        for (i in squares.indices) {
            squares[i]!!.addActionListener(ticTacEngine)
            squares[i]!!.background = Color.ORANGE
        }
        newGameButton.addActionListener(ticTacEngine)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TicTacGUI()
        }
    }
}