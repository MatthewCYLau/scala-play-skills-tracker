package auth.repository

import auth.models.{LoginRequest, User}

object UserDAO {

  // To-Do: get users from database
  def userList: List[User] = {
    val adminUser = User("admin", "password", "auth1", true)
    val basicUser = User("basic_user", "password", "auth2", false)
    List(adminUser, basicUser)
  }

  // create Map of auth token, and User
  final val usersMap = userList.map(user => (user.authToken, user)).toMap

  def getUser(username: String): Option[User] = {
    usersMap.get(username)
  }

  def getAuthToken(req: LoginRequest): Option[String] = {
    usersMap.find(_._2.username == req.username).map(_._1)
  }
}
