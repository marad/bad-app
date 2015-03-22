package mvctest.web

import java.math.BigInteger
import javax.persistence.EntityManager

import mvctest.domain.User
import mvctest.service.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{PathVariable, RequestMethod, RequestMapping, RequestParam}

@Controller
class LoginController {
  @Autowired
  var userRepository: UserRepository = _

  @Autowired
  var em: EntityManager = _

  @RequestMapping(value = Array("/show"), method = Array(RequestMethod.GET))
  def show(model: Model, @RequestParam(required = false) error: String): String = {
    model.addAttribute("error", error)
    "/login"
  }

  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST))
  def login(@RequestParam username: String, @RequestParam password: String): String = {
    val count = em.createNativeQuery(s"select count(*) from users where username = '$username' and password = '$password'").getSingleResult.asInstanceOf[BigInteger]
    if (count.compareTo(BigInteger.ONE) == 0) {
      "redirect:/success/1"
    } else {
      "redirect:/show?error=bad password"
    }
  }

  @RequestMapping(value = Array("/success/{id}"), method = Array(RequestMethod.GET))
  def success(model: Model, @PathVariable id: String): String = {
    val user = em.createNativeQuery(s"select * from users where id=$id", classOf[User]).getSingleResult.asInstanceOf[User]
    model.addAttribute("user", user)
    "/success"
  }
}
