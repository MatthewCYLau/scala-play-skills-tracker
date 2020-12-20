package user.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import user.models.User

import scala.concurrent.{ExecutionContext, Future}

class UserRepositoryImpl @Inject()(db: Database,
                                   databaseExecutionContext: ExecutionContext,
                                   userRepositoryDAO: UserRepositoryDAO)
    extends UserRepository {

  val parser: RowParser[User] = Macro.namedParser[User]

  def getUsers(): Future[List[User]] = {
    Future {
      db.withConnection { implicit conn =>
        userRepositoryDAO.get()
      }
    }(databaseExecutionContext)
  }

  def getUserById(id: UUID): Future[Option[User]] = {
    Future {
      db.withConnection { implicit conn =>
        userRepositoryDAO.getBy(id)
      }
    }(databaseExecutionContext)
  }

  def createUser(user: User): Future[Boolean] = {
    Future {
      db.withConnection { implicit conn =>
        userRepositoryDAO.insert(user)
      }
    }(databaseExecutionContext)
  }

  def updateUserById(id: UUID, user: User): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        userRepositoryDAO.update(id, user)
      }
    }(databaseExecutionContext)
  }

  def deleteUserById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        userRepositoryDAO.delete(id)
      }
    }(databaseExecutionContext)
  }
}
