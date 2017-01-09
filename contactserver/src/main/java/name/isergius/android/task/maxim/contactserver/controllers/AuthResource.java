package name.isergius.android.task.maxim.contactserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import name.isergius.android.task.maxim.contactserver.model.Message;

/**
 * Created by isergius on 06.01.17.
 */
@RestController("/Hello")
public class AuthResource {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Message check(@RequestParam(name = "login",required = true) String username,
                         @RequestParam(name = "password",required = true) String password) {
        return new Message("OK", true);
    }
}
