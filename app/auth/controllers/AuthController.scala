package auth.controllers

import auth.models.{LoginRequest, User}
import auth.services.AuthService
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import auth.repository.UserDAO

import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents,
                               authService: AuthService)(implicit ec: ExecutionContext)
    extends BaseController {

  def login: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body
      .validate[LoginRequest]
      .fold(
        errors => BadRequest(errors.mkString),
        req => {
          val auth_token = UserDAO.getAuthToken(req)
          auth_token match {
            case Some(s) => Ok(Json.parse(s"""{"auth_token": "$s"}"""))
            case None    => Unauthorized("Unauthorized to perform operation")
          }
        }
      )
  }
}
