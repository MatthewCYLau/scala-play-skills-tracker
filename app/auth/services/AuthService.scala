package auth.services

import auth.models.User
import auth.repository.UserDAO
import javax.inject.Inject
import play.api.mvc.{
  AnyContent,
  BaseController,
  ControllerComponents,
  Request,
  RequestHeader,
  Result
}

import scala.concurrent.{ExecutionContext, Future}

class AuthService @Inject()(val controllerComponents: ControllerComponents)(
    implicit ec: ExecutionContext)
    extends BaseController {
  def extractUser(req: RequestHeader): Option[User] = {
    val authHeader = req.headers.get("Authorization")
    authHeader
      .flatMap(authHeader => UserDAO.getUser(authHeader))
  }

  def withUser[T](block: User => Future[Result])(
      implicit request: Request[AnyContent]): Future[Result] = {
    val user = extractUser(request)
    user.map(block).getOrElse(Future { Unauthorized("Unauthorized to perform operation") })
  }
}
