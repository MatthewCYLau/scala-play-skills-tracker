package profile.services
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile
import anorm._
import profile.persistence.ProfileRepository

import scala.concurrent.{ExecutionContext, Future}

class ProfileService @Inject() (db: Database, databaseExecutionContext: ExecutionContext, profileRepository: ProfileRepository)  {

  val inMemoryProfiles = List(Profile(3, "Jon", "jon@doe.com"), Profile(4, "Jane", "jane@doe.com"))

  val parser: RowParser[Profile] = Macro.namedParser[Profile]

  def getInMemoryProfiles(): List[Profile] = {
    inMemoryProfiles
  }

  def getProfiles(): Future[List[Profile]]  = {
    profileRepository.getProfiles()
  }
}
