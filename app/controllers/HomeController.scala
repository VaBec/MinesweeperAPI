package controllers

import controller.Controller
import javax.inject._
import model.{Creator, Difficulty, FieldMatrix, GameWrapper, Model}
import model.Difficulty.Difficulty
import model.GameStatus.GameStatus
import observerpattern.Observer
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val model = new Model(Difficulty.Easy)
  val controller = new Controller(model)
  val creator = new Creator
  val difficulty = 1
  val wrapper = new GameWrapper()

  val diff = difficulty match {
    case 1 => new Difficulty(9,9,10);
    case 2 => new Difficulty(14,14,26);
    case 3 => new Difficulty(20,20,45);
    case _ => new Difficulty(9,9,10)
  }

  model.addGameListener(wrapper);

  wrapper.setFieldMatrix(fieldMatrix)

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
    Ok(views.html.minesweeper(controller, model, wrapper.getGamefield(), wrapper.getStatus(), true))
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
    wrapper.setFieldMatrix(fieldMatrix)

    Ok(views.html.minesweeper(controller, model, fieldMatrix, wrapper.getStatus(), true))
  }

  def open(x:Int, y:Int) = Action {
    controller.handleClick(x, y, wrapper.getGamefield(), false)
    Ok(views.html.minesweeper(controller, model, wrapper.getGamefield(), wrapper.getStatus(), false))
  }

  def flag(x:Int, y:Int) = Action {
    controller.handleClick(x, y, wrapper.getGamefield(), true)
    Ok(views.html.minesweeper(controller, model, wrapper.getGamefield(), wrapper.getStatus(), false))
  }
}
