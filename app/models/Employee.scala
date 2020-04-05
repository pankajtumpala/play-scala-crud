package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

case class Employee(id: Long, firstName: String, lastName: String, mobile: Long, email: String)

object Employee {
  /**
    * Mapping to read/write a PostResource out as a JSON value.
    */
    implicit val format: Format[Employee] = Json.format
}

case class EmployeeFormData(firstName: String, lastName: String, mobile: Long, email: String)

object EmployeeForm {

  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> longNumber,
      "email" -> email
    )(EmployeeFormData.apply)(EmployeeFormData.unapply)
  )
}



class Employees @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) {
  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class EmployeeTableDef(tag: Tag) extends Table[Employee](tag, "Employee") {

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")
    def mobile = column[Long]("mobile")
    def email = column[String]("email")

    override def * =
      (id, firstName, lastName, mobile, email) <>((Employee.apply _).tupled, Employee.unapply)
  }

  val employees = TableQuery[EmployeeTableDef]
  
  def add(employee: Employee): Future[String] = {
    dbConfig.db.run(employees += employee).map(res => "Employee successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(employees.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Employee]] = {
    dbConfig.db.run(employees.filter(_.id === id).result.headOption)
  }

  def update(employee: Employee): Future[Int] = {
    dbConfig.db.run(employees.filter(_.id === employee.id).map(p => (p.firstName,p.lastName, p.email,p.mobile))
    .update((employee.firstName,employee.lastName, employee.email,employee.mobile)))
  }

  def listAll: Future[Seq[Employee]] = {
   dbConfig.db.run(employees.result)
  }

}
