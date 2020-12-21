package profile.repository

import java.sql.Connection
import java.util.UUID

import anorm.{Macro, RowParser, SQL}
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile

class ProfileRepositoryDAO @Inject()(db: Database) {
  val parser: RowParser[Profile] = Macro.namedParser[Profile]

  def get()(implicit conn: Connection): List[Profile] = {
    SQL("SELECT * FROM profiles").as(parser.*)
  }

  def getBy(id: UUID)(implicit conn: Connection): Option[Profile] = {
    SQL("SELECT profile_id, name, email FROM profiles WHERE profile_id = {id}::uuid")
      .on("id" -> id)
      .as(parser.singleOpt)
  }

  def insert(profile: Profile)(implicit conn: Connection): Boolean = {
    SQL(
      "INSERT INTO profiles (profile_id, name, email) VALUES ({profile_id}::uuid, {name}, {email})")
      .on("profile_id" -> profile.profile_id, "name" -> profile.name, "email" -> profile.email)
      .execute()
  }

  def update(id: UUID, profile: Profile)(implicit conn: Connection): Int = {
    SQL("UPDATE profiles SET name = {name}, email = {email} WHERE profile_id = {id}::uuid")
      .on("id" -> profile.profile_id, "name" -> profile.name, "email" -> profile.email)
      .executeUpdate()
  }

  def delete(id: UUID)(implicit conn: Connection): Int = {
    SQL("DELETE FROM profiles WHERE profile_id = {id}::uuid").on("id" -> id).executeUpdate()
  }
}
