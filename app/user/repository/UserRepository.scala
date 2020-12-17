package user.repository

import java.util.UUID
import java.util.UUID.randomUUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import user.models.User

import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject()(db: Database, databaseExecutionContext: ExecutionContext) {

  val parser: RowParser[User] = Macro.namedParser[User]

  def getUsers(): Future[List[User]]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT * FROM users".as(parser.*)
      }
    }(databaseExecutionContext)
  }

  def getUserById(id: UUID): Future[Option[User]]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT user_id, name, email FROM users WHERE user_id = ${id}::uuid".as(parser.singleOpt)
      }
    }(databaseExecutionContext)
  }

  def createUser(user: User): Future[Boolean]  = {
    val id = user.user_id.getOrElse(randomUUID())
    Future {
      db.withConnection { implicit conn =>
        SQL"INSERT INTO users (user_id, name, email) VALUES (${id}::uuid, ${user.name}, ${user.email})".execute()
      }
    }(databaseExecutionContext)
  }

  def deleteUserById(id: UUID): Future[Int]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"DELETE FROM users WHERE user_id = ${id}::uuid".executeUpdate()
      }
    }(databaseExecutionContext)
  }

  def updateUserById(id: UUID, user: User): Future[Int]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"UPDATE users SET name = ${user.name}, email = ${user.email} WHERE user_id = ${id}::uuid".executeUpdate()
      }
    }(databaseExecutionContext)
  }
}
