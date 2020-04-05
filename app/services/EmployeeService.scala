package services

import com.google.inject.Inject
import models.{Employee, Employees}

import scala.concurrent.Future

class EmployeeService @Inject() (employees: Employees) {

  def addEmployee(employee: Employee): Future[String] = {
    employees.add(employee)
  }

  def deleteEmployee(id: Long): Future[Int] = {
    employees.delete(id)
  }

  def getEmployee(id: Long): Future[Option[Employee]] = {
    employees.get(id)
  }

  def update(id:Long,employee:Employee): Future[Int] = {
    employees.update(employee)
  }

  def listAllEmployees: Future[Seq[Employee]] = {
    employees.listAll
  }
}
