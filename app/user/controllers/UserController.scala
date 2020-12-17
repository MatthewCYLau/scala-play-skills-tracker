package user.controllers

import java.util.UUID

import javax.inject._
import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Json}
import play.api.mvc._
import user.models.User
import user.services.UserService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends BaseController {

  def getUsers(): Action[AnyContent] = Action.async { implicit request =>
    userService.getUsers().map { users =>
      Ok(Json.toJson(users))
    }
  }

  def getUserById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    userService.getUserById(id).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    }
  }

  def deleteUserById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    userService.deleteUserById(id).map{ res =>
      res match {
        case 1 => Ok("Ok")
        case 0 => BadRequest("Error when deleting user.")
      }
    }
  }

  def createUser: Action[JsValue] = Action(parse.json).async { implicit request =>

    implicit val userReads = Json.reads[User]
    val userFromJson: JsResult[User] = Json.fromJson[User](request.body)

    userFromJson match {
      case JsSuccess(user, _) => userService.createUser(user).map{ _ =>
        Ok("Ok")
      }
      case e: JsError => Future { BadRequest("Error when creating user " + JsError.toJson(e).toString())
      }
    }
  }

  def updateUserById(id: UUID): Action[JsValue] = Action(parse.json).async { implicit request =>

    implicit val userReads = Json.reads[User]
    val userFromJson: JsResult[User] = Json.fromJson[User](request.body)

    userFromJson match {
      case JsSuccess(user, _) => userService.updateUserById(id, user).map { res =>
        res match {
          case 1 => Ok("Ok")
          case 0 => BadRequest("Error when updating user.")
        }
      }
      case e: JsError => Future { BadRequest("Error when updating user " + JsError.toJson(e).toString())}
    }
  }
}
