package models.security

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import play.api.libs.json.{Json, _}

import scala.util.{Failure, Success, Try}

case class User(
    id: Option[String], 
    loginInfo: LoginInfo, 
    username: String, 
    email: String,
    firstName: String, 
    lastName: String, 
    avatarURL: Option[String], 
    activated: Boolean
    ) extends Identity

object User {
    implicit val format: Format[User] = Json.format
}
