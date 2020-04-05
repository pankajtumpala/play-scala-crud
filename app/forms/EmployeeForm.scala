package forms

import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import models.EmployeeFormData

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