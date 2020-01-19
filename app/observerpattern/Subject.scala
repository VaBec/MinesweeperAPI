package observerpattern

import model.FieldMatrix
import model.GameStatus.GameStatus

trait Subject {

  private var observers: List[Observer] = Nil

  def addGameListener(observer: Observer): Unit = observers = observer :: observers
  def fireFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = {
    println("firing update: " + observers)
    observers.foreach(_.gameFieldUpdated(fieldMatrix, gameStatus))
  }

}
