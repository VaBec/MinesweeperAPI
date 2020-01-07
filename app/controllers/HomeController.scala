package controllers

import controller.Controller
import javax.inject._
import model.{Creator, Model}
import model.Difficulty.Difficulty
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

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

  def start(difficulty: Int) = Action {

    val creator = new Creator

    val diff = difficulty match {
      case 1 => new Difficulty(9,9,10);
      case 2 => new Difficulty(14,14,26);
      case 3 => new Difficulty(20,20,45);
      case _ => new Difficulty(9,9,10)
    }

    val randomBombs = creator.createRandomBombLocations(diff)
    val fieldMatrix = creator.create(diff, randomBombs)

    val model = new Model(diff)
    val controller = new Controller(model)

    Ok(views.html.minesweeper(controller, model, fieldMatrix))
  }

  def open(x:Int, y:Int) = Action {
    Ok("open x:"+x + " y: " +y)
  }

  def flag(x:Int, y:Int) = Action {
    Ok("mark as Flag; x: " +x + " y: " + y)
  }
}
