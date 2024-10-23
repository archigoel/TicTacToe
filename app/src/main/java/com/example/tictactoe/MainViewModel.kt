package com.example.tictactoe

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var inputList = mutableStateMapOf<Coordinate, String>()
    var won = mutableStateOf(false)
    lateinit var winningCoordinates : MutableList<Coordinate>

    private var winningCoordinatesList = listOf(
        listOf(Coordinate(0, 0),Coordinate(1, 0),Coordinate(2, 0) ),
        listOf(Coordinate(0, 0),Coordinate(0, 1),Coordinate(0, 2) ),
        listOf(Coordinate(0, 0),Coordinate(1, 1),Coordinate(2, 2) ),
        listOf(Coordinate(1, 0),Coordinate(1, 1),Coordinate(1, 2) ),
        listOf(Coordinate(0, 1),Coordinate(1, 1),Coordinate(2, 1) ),
        listOf(Coordinate(0, 2),Coordinate(1, 2),Coordinate(2, 2) ),
        listOf(Coordinate(2, 0),Coordinate(1, 1),Coordinate(0, 2) ),
        listOf(Coordinate(2, 0),Coordinate(2, 1),Coordinate(2, 2) )
      )

    fun resetGame(){
        inputList.clear()
        won.value = false
    }

    fun winningCondition( coordinate: Coordinate) {
        for(line in winningCoordinatesList) {
            if(line.contains(coordinate)) {
                var match = 0
                for(j in line) {
                    if(inputList[coordinate] == null || inputList[j] != inputList[coordinate] ) break
                    else {
                        match ++
                    }
                }
                if(match == 3) {
                    winningCoordinates = mutableListOf<Coordinate>()
                    winningCoordinates.addAll(line)
                    won.value = true
                    break

                }
            }
        }
    }
}


data class Coordinate(var x: Int, var y: Int)