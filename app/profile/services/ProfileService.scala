package profile.services
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile

import scala.concurrent.{ExecutionContext, Future}

class ProfileService @Inject() (db: Database, databaseExecutionContext: ExecutionContext)  {

  val profiles = List(Profile(3, "Jon", "jon@doe.com"), Profile(4, "Jane", "jane@doe.com"))

  def listProfiles(): List[Profile] = {
    profiles
  }

  def updateSomething(): Unit = {
    Future {
      db.withConnection { conn =>
        // do whatever you need with the db connection
      }
    }(databaseExecutionContext)
  }
}
