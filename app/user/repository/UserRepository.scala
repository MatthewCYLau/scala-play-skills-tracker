package user.repository

import java.util.UUID

import user.models.User

import scala.concurrent.Future

trait UserRepository {
  def getUsers(): Future[List[User]]
  def getUserById(id: UUID): Future[Option[User]]
  def createUser(user: User): Future[Boolean]
  def deleteUserById(id: UUID): Future[Int]
  def updateUserById(id: UUID, user: User): Future[Int]
}
