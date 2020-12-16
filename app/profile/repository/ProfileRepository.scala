package profile.repository

import java.util.UUID
import java.util.UUID.randomUUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile

import scala.concurrent.{ExecutionContext, Future}

class ProfileRepository @Inject() (db: Database, databaseExecutionContext: ExecutionContext) {

  val parser: RowParser[Profile] = Macro.namedParser[Profile]

  def getProfiles(): Future[List[Profile]]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT * FROM profiles".as(parser.*)
      }
    }(databaseExecutionContext)
  }

  def getProfileById(id: UUID): Future[Option[Profile]]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT profile_id, name, email FROM profiles WHERE profile_id = ${id}::uuid".as(parser.singleOpt)
      }
    }(databaseExecutionContext)
  }

  def createProfile(profile: Profile): Future[Boolean]  = {
    val id = profile.profile_id.getOrElse(randomUUID())
    Future {
      db.withConnection { implicit conn =>
        SQL"INSERT INTO profiles (profile_id, name, email) VALUES (${id}::uuid, ${profile.name}, ${profile.email})".execute()
      }
    }(databaseExecutionContext)
  }
}
