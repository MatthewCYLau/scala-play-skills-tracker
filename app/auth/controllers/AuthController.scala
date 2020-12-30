package auth.controllers

import auth.models.User
import auth.services.AuthService
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import auth.repository.UserDAO

import scala.concurrent.{ExecutionContext}

@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents,
                               authService: AuthService)(implicit ec: ExecutionContext)
    extends BaseController {

  def login: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body
      .validate[User]
      .fold(
        errors => BadRequest(errors.mkString),
        user => {
          val auth_token = UserDAO.getAuthToken(user)
          auth_token match {
            case Some(s) => Ok(Json.parse(s"""{"auth_token": "$s"}"""))
            case None    => Unauthorized("Unauthorized to perform operation")
          }
        }
      )
  }
}
