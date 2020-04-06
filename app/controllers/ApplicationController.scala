package controllers

import javax.inject._
import models.{Employee}
import forms.EmployeeForm
import play.api.Logging
import play.api.mvc._
import play.api.libs.json.Json
import services.EmployeeService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.i18n.{Langs, MessagesApi}

@Singleton
class ApplicationController @Inject()(cc: ControllerComponents, employeeService: EmployeeService,messagesApi: MessagesApi,langs: Langs) extends AbstractController(cc) with Logging with play.api.i18n.I18nSupport {
  
  def create() = Action.async { implicit request: Request[AnyContent] =>
    logger.trace("Create Employee")
    EmployeeForm.form.bindFromRequest.fold(
      errorForm => {
        logger.warn(s"Form submission with error: ${errorForm.errors}")
        Future.successful(BadRequest(errorForm.errorsAsJson))
      },
      data => {
        val newEmployee = Employee(0, data.firstName, data.lastName, data.mobile, data.email)
        employeeService.findByEmail(newEmployee.email).flatMap{
          case Some(res) =>
            Future.successful(BadRequest(Json.obj("email" -> List("Email already exists"))))
          case None =>
            employeeService.addEmployee(newEmployee).map(_ => Ok(Json.obj("message" -> "Employee created successfully")))
        }
      })
  }
  
  def get() = Action.async { implicit request: Request[AnyContent] =>
    logger.trace("Get Employees")
    employeeService.listAllEmployees map { employees =>
      Ok(Json.obj("employees" -> Json.toJson(employees)))
    }
  }
  
  def getEmployee(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    logger.trace("Get Employee Details")
    employeeService.getEmployee(id) map { employee =>
      println(employee)
      if(employee != None) Ok(Json.obj("employee" -> Json.toJson(employee))) else BadRequest(Json.obj("error" -> "Employee not found"))
    }
  }
  
  def update(id: Long)  = Action.async { implicit request: Request[AnyContent] =>
    logger.trace("Update Employee")
    EmployeeForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        logger.warn(s"Form submission with error: ${errorForm.errors}")
        Future.successful(BadRequest(errorForm.errorsAsJson))
      },
      data => {
        var employee = Employee(id, data.firstName, data.lastName, data.mobile.toLong, data.email)
        employeeService.update(id,employee) map { res =>
            if(res == 1) Ok(Json.obj("message" -> "Employee updated successfully")) else BadRequest(Json.obj("error" -> "Employee not found"))
        }
      })    
  }
  
  def delete(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    logger.trace("Delete Employee")
    employeeService.deleteEmployee(id) map { res =>
      if(res == 1) Ok(Json.obj("message" -> "Employee deleted successfully")) else BadRequest(Json.obj("error" -> "Employee not found"))
    }
  }  

}
