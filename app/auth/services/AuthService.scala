package auth.services

import auth.models.User
import auth.repository.UserDAO
import play.api.mvc.{RequestHeader}

class AuthService {
  def extractUser(req: RequestHeader): Option[User] = {
    val authHeader = req.headers.get("Authorization")
    authHeader
      .flatMap(authHeader => UserDAO.getUser(authHeader))
  }
}
