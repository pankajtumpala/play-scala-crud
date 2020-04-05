import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{ JsResult, Json }
import play.api.mvc.{ RequestHeader, Result }
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._
import models.{Employee, Employees}
import scala.concurrent.Future

class ApplicationControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ApplicationController" should {

    "render the list of employees" in {
      val request = FakeRequest(GET, "/api/v1/employee").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
        
      val result = contentAsJson(home)
      val posts: Seq[Employee] = (result \ "employees").as[Seq[Employee]]
      posts.filter(_.id == 1).head mustBe (Employee(1,"John","Doe","9874563210".toLong,"john@doe.com" ))
    }
    
    "create employee success" in {
      val request = FakeRequest(POST, "/api/v1/employee").withHeaders(HOST -> "localhost:9000").withJsonBody(Json.parse(
        s"""{
            |  "firstName": "John",
            |  "lastName": "Smith",
            |  "email": "smith@gmail.com",
            |  "mobile": "9874563211"
            |}""".stripMargin))
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "message").as[String] mustBe "Employee created successfully"
    }
    
    "create employee failure" in {
      val request = FakeRequest(POST, "/api/v1/employee").withHeaders(HOST -> "localhost:9000").withJsonBody(Json.parse(
        s"""{
            |  "firstName": "John",
            |  "lastName": "Smith",
            |  "email": "test",
            |  "mobile": "9874563211"
            |}""".stripMargin))
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "email" \ 0).as[String] mustBe "Valid email required"
    }
    
    "update employee success" in {
      val request = FakeRequest(PATCH, "/api/v1/employee/1").withHeaders(HOST -> "localhost:9000").withJsonBody(Json.parse(
        s"""{
            |  "firstName": "John",
            |  "lastName": "Smith",
            |  "email": "smith@gmail.com",
            |  "mobile": "9874563211"
            |}""".stripMargin))
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "message").as[String] mustBe "Employee updated successfully"
    }
    
    "update employee failure" in {
      val request = FakeRequest(PATCH, "/api/v1/employee/1").withHeaders(HOST -> "localhost:9000").withJsonBody(Json.parse(
        s"""{
            |  "firstName": "John",
            |  "lastName": "Smith",
            |  "email": "test",
            |  "mobile": "9874563211"
            |}""".stripMargin))
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "email" \ 0).as[String] mustBe "Valid email required"
    }
    
    "delete employee success" in {
      val request = FakeRequest(DELETE, "/api/v1/employee/1").withHeaders(HOST -> "localhost:9000")
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "message").as[String] mustBe "Employee deleted successfully"
    }
    
    "delete employee failure" in {
      val request = FakeRequest(DELETE, "/api/v1/employee/1").withHeaders(HOST -> "localhost:9000")
      val home:Future[Result] = route(app, request).get
      val result = contentAsJson(home)
      (result \ "error").as[String] mustBe "Employee not found"
    }
  }

}