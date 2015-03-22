# bad app in spring+scala
This app is extremely exploitable! Don't do this at home!

## Running with SBT

From command line do:

    sbt run

## Logging in

To log in visit [login page](http://localhost:8080/login) and use `admin`
as username and `mypass` as password.

## Exploiting

### Log in without password (SQL injection)

To log in bypassing the security mechanism simply put name of the user you want
to log in as in the first field and put this string as the `password`:

    p' or '1' = '1

This works because you simply modify SQL to say: Log me in if password is `p` or `1 == 1`.
So effectively we're ignoring the password.

### Fetch admin password from db (SQL injection)

To get admin password visit [this page](http://localhost:8080/success/0%20union%20select%200,%20'whatever',%20password%20from%20users%20where%20username%20=%20'admin'):

    http://localhost:8080/success/0 union select 0, 'whatever', password from users where username = 'admin'

We simply fetch password as `username` field which later gets displayed in the greeting.

### Simple XSS

To execute some script in the page you can simply visit [this link](http://localhost:8080/show?error=%3Cscript%3Ealert\(%22XSS!%22\)%3C/script%3E):

    http://localhost:8080/show?error=<script>alert("XSS!")</script>

This takes advantage of putting the `error` parameter in the HTML body without escaping.

NOTE: Google Chrome will refuse to execute this script because it's smart enough
to know that something is wrong. Firefox however will happily show our alert :)

