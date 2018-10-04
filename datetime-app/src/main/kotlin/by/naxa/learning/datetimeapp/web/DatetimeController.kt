package by.naxa.learning.datetimeapp.web

import by.naxa.learning.datetimeapp.service.DatetimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DatetimeController(@Autowired val service: DatetimeService) {

    @GetMapping("/current/datetime")
    fun weather(): String = "the current time is ${service.datetime()}"

}
