package controllers

import controller.Controller
import javax.inject._
import model.{Creator, Difficulty, FieldMatrix, Model}
import model.Difficulty.Difficulty
import model.GameStatus.GameStatus
import observerpattern.Observer
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Observer {

  val model = new Model(Difficulty.Easy)
  val controller = new Controller(model)
  val creator = new Creator
  val difficulty = 1
  val diff = difficulty match {
    case 1 => new Difficulty(9,9,10);
    case 2 => new Difficulty(14,14,26);
    case 3 => new Difficulty(20,20,45);
    case _ => new Difficulty(9,9,10)
  }


  model.addGameListener(this);
  println("added listener")
  val randomBombs = creator.createRandomBombLocations(diff)
  val fieldMatrix = creator.create(diff, randomBombs)
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def start() = Action {
println("start")
//    val creator = new Creator
//
//    val diff = difficulty match {
//      case 1 => new Difficulty(9,9,10);
//      case 2 => new Difficulty(14,14,26);
//      case 3 => new Difficulty(20,20,45);
//      case _ => new Difficulty(9,9,10)
//    }
//
//    val randomBombs = creator.createRandomBombLocations(diff)
//    val fieldMatrix = creator.create(diff, randomBombs)
//
//    val model = new Model(diff)
//    val controller = new Controller(model)

    Ok(views.html.minesweeper(controller, model, fieldMatrix))
  }

  def restart(difficulty: Int) = Action {

    val diff = difficulty match {
      case 1 => new Difficulty(9,9,10);
      case 2 => new Difficulty(14,14,26);
      case 3 => new Difficulty(20,20,45);
      case _ => new Difficulty(9,9,10)
    }

    val randomBombs = creator.createRandomBombLocations(diff)
    val fieldMatrix = creator.create(diff, randomBombs)
    Ok(views.html.minesweeper(controller, model, fieldMatrix))
  }

  def open(x:Int, y:Int) = Action {
    controller.handleClick(x, y, fieldMatrix, false)
    //controller.HomeController.update
    Redirect(routes.HomeController.start())
    //Ok(views.html.minesweeper(controller, model, fieldMatrix))
  }

  def flag(x:Int, y:Int) = Action {
    controller.handleClick(x, y, fieldMatrix, true)
    Ok("mark as Flag; x: " +x + " y: " + y)
  }

  def print_wui(): Unit = Action {
    println("action")
    Redirect(routes.HomeController.start())
  }

  override def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus):Unit= {
    println("gamefield_updated: "+ gameStatus+ " matrix: ")
    updateField()
  }

  def updateField() = {
    println("updating")
//    Redirect(routes.HomeController.start())
//    println("redirected")
//    Action {
//      println("updated")
//      Ok("updated")
//    }
  }
}
