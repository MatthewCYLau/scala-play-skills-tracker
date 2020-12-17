package user.services
import java.util.UUID

import javax.inject.Inject
import play.api.db.Database
import user.models.User
import user.repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(db: Database, databaseExecutionContext: ExecutionContext, userRepository: UserRepository)  {

  def getUsers(): Future[List[User]]  = {
    userRepository.getUsers()
  }

  def getUserById(id: UUID): Future[Option[User]]  = {
    userRepository.getUserById(id)
  }

  def createUser(user: User): Future[Boolean] = {
    userRepository.createUser(user)
  }

  def deleteUserById(id: UUID): Future[Int]  = {
    userRepository.deleteUserById(id)
  }

  def updateUserById(id: UUID, user: User): Future[Int]  = {
    userRepository.updateUserById(id, user)
  }
}
