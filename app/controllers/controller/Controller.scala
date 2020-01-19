package controller

import model.{FieldMatrix, Model}

class Controller(model: Model) {

  def handleClick(x: Int, y: Int, matrix: FieldMatrix, flag: Boolean): Unit = {
    println("handling click.")
    model.update(matrix, flag, x, y)
  }

}
