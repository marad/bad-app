package mvctest.service

import mvctest.domain.User
import org.springframework.data.repository.CrudRepository
import java.lang.Long

trait UserRepository extends CrudRepository[User, Long]
