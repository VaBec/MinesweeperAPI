package model

import model.GameStatus.GameStatus
import observerpattern.Observer

case class GameWrapper() extends Observer {
  var field: FieldMatrix = null
  var status: GameStatus = null

  override def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = {
      field = fieldMatrix
      status = gameStatus
  }

  def getGamefield(): FieldMatrix = {
      field
  }

  def getStatus(): GameStatus = {
    status
  }
}
