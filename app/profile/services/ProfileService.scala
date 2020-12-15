package profile.services
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile
import anorm._

import scala.concurrent.{ExecutionContext, Future}

class ProfileService @Inject() (db: Database, databaseExecutionContext: ExecutionContext)  {

  val profiles = List(Profile(3, "Jon", "jon@doe.com"), Profile(4, "Jane", "jane@doe.com"))

  val parser: RowParser[Profile] = Macro.namedParser[Profile]

  def listProfilesInMemory(): List[Profile] = {
    profiles
  }

  def listProfiles(): Future[List[Profile]]  = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT * FROM profiles".as(parser.*)
      }
    }(databaseExecutionContext)
  }
}
