package profile.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile

import scala.concurrent.{ExecutionContext, Future}

class ProfileRepositoryImpl @Inject()(db: Database,
                                      databaseExecutionContext: ExecutionContext,
                                      profileRepositoryDAO: ProfileRepositoryDAO)
    extends ProfileRepository {

  val parser: RowParser[Profile] = Macro.namedParser[Profile]

  def getProfiles(): Future[List[Profile]] = {
    Future {
      db.withConnection { implicit conn =>
        profileRepositoryDAO.get()
      }
    }(databaseExecutionContext)
  }

  def getProfileById(id: UUID): Future[Option[Profile]] = {
    Future {
      db.withConnection { implicit conn =>
        profileRepositoryDAO.getBy(id)
      }
    }(databaseExecutionContext)
  }

  def createProfile(profile: Profile): Future[Boolean] = {
    Future {
      db.withConnection { implicit conn =>
        profileRepositoryDAO.insert(profile)
      }
    }(databaseExecutionContext)
  }

  def updateProfileById(id: UUID, profile: Profile): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        profileRepositoryDAO.update(id, profile)
      }
    }(databaseExecutionContext)
  }

  def deleteProfileById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        profileRepositoryDAO.delete(id)
      }
    }(databaseExecutionContext)
  }
}
