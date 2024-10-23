package com.example.tictactoe.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.Coordinate
import com.example.tictactoe.MainViewModel


enum class Players(val symbol: String) {
    O("A"),
    X ("B")
}

@Composable
fun TicTacToe() {

    val myViewModel: MainViewModel = viewModel()
    val inputList = myViewModel.inputList

    val won = myViewModel.won.value

    val winningPlayer =  if (won) {inputList[myViewModel.winningCoordinates[0]]} else ""


    Column(modifier = Modifier.padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        Text(
            text = if (won) {
                "Winning player : ${winningPlayer?.let { Players.valueOf(it).symbol}}"
            } else
            "Player A: 0     Player B: X",
            color = Color.Black,
            modifier = Modifier
                .weight(1f), fontSize = 25.sp
        )

        DrawGrids(myViewModel)
        Button(onClick = { myViewModel.resetGame() }, modifier = Modifier.weight(1f).align(Alignment.End)) {
            Text(text = "New Game")
        }
    }
}

@Composable
fun DrawGrids(myViewModel: MainViewModel) {
    val player1 = "X"
    val player2 = "O"
    val inputList = myViewModel.inputList
    val won = myViewModel.won.value
    var lastPlayer by remember { mutableStateOf(player1) }

    Column(
       modifier = Modifier.fillMaxSize(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    val isWinningCell = won && myViewModel.winningCoordinates.contains(Coordinate(i, j))

                    val animatedColor by animateColorAsState(
                        targetValue = if (isWinningCell) Color(0xFFe3c8a5) else Color(0xFFda9c4c),
                        animationSpec = if (isWinningCell) {
                            infiniteRepeatable(
                                animation = tween(durationMillis = 500), // Adjust duration for faster/slower blink
                                repeatMode = RepeatMode.Reverse
                            )
                        } else {
                            tween(durationMillis = 0)
                        }, label = ""
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .padding(2.dp)
                            .clickable {
                                if (inputList[Coordinate(i, j)] == null && !won) {
                                    if (lastPlayer == player1) {
                                        inputList[Coordinate(i, j)] = player2
                                        lastPlayer = player2
                                    } else {
                                        inputList[Coordinate(i, j)] = player1
                                        lastPlayer = player1
                                    }
                                    myViewModel.winningCondition(Coordinate(i, j))
                                }
                            }
                            .background(animatedColor)

                    ) {
                        if(inputList[Coordinate(i, j)] != null)  {
                            Text(
                                color = if(inputList[Coordinate(i, j)] == "O") Color.Red else Color.Blue,
                                text = inputList[Coordinate(i, j)]!!,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 40.sp
                            )
                                 }
                    }
                }
            }
        }
    }

}


