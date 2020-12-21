package auth.repository

import auth.models.User

object UserDAO {

  final val users: Map[String, User] =
    Map("auth1" -> User("admin", "password"), "auth2" -> User("basic_user", "password"))

  def getUser(username: String): Option[User] = {
    users.get(username)
  }

  def getAuthToken(user: User): Option[String] = {
    users.find(_._2 == user).map(_._1)
  }
}
