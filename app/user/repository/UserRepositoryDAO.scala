package user.repository

import java.sql.Connection
import java.util.UUID

import anorm.{Macro, RowParser, SQL}
import javax.inject.Inject
import play.api.db.Database
import user.models.User

class UserRepositoryDAO @Inject()(db: Database) {
  val parser: RowParser[User] = Macro.namedParser[User]

  def get()(implicit conn: Connection): List[User] = {
    SQL("SELECT * FROM users").as(parser.*)
  }

  def getBy(id: UUID)(implicit conn: Connection): Option[User] = {
    SQL("SELECT user_id, name, email FROM users WHERE user_id = {id}::uuid")
      .on("id" -> id)
      .as(parser.singleOpt)
  }

  def insert(user: User)(implicit conn: Connection): Boolean = {
    SQL("INSERT INTO users (user_id, name, email) VALUES ({user_id}::uuid, {name}, {email})")
      .on("user_id" -> user.user_id, "name" -> user.name, "email" -> user.email)
      .execute()
  }

  def update(id: UUID, user: User)(implicit conn: Connection): Int = {
    SQL("UPDATE users SET name = {name}, email = {email} WHERE user_id = {id}::uuid")
      .on("id" -> user.user_id, "name" -> user.name, "email" -> user.email)
      .executeUpdate()
  }

  def delete(id: UUID)(implicit conn: Connection): Int = {
    SQL("DELETE FROM users WHERE user_id = {id}::uuid").on("id" -> id).executeUpdate()
  }
}
