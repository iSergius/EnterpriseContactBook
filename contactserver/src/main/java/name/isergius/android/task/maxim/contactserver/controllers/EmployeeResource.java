package name.isergius.android.task.maxim.contactserver.controllers;

import name.isergius.android.task.maxim.contactserver.dao.EmployeeDao;
import name.isergius.android.task.maxim.contactserver.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Created by isergius on 06.01.17.
 */
@RestController
public class EmployeeResource {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(path = "/GetAll", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Employee> getAll(@RequestParam(name = "login") String username,
                                     @RequestParam(name = "password") String password) {
        return employeeDao.findAll();
    }


    @RequestMapping(path = "/GetWPhoto", method = RequestMethod.GET, produces = "img/jpg")
    public StreamingResponseBody getPhoto(@RequestParam(name = "login") String username,
                                          @RequestParam(name = "password") String password,
                                          @RequestParam(name = "id") long id) {
        Employee employee = employeeDao.findOne(id);
        byte[] photo = employee.getPhoto();
        return outputStream -> {
            outputStream.write(photo);
        };
    }


}
