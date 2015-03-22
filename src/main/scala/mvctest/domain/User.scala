package mvctest.domain

import javax.persistence.{Table, Entity, Id}
import java.lang.Long

import scala.beans.BeanProperty

@Entity
@Table(name = "users")
class User {
  @Id
  @BeanProperty
  var id: Long = _

  @BeanProperty
  var username: String = _

  @BeanProperty
  var password: String = _
}
