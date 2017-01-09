package name.isergius.android.task.maxim.contactserver.dao;

import name.isergius.android.task.maxim.contactserver.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by isergius on 06.01.17.
 */

public interface EmployeeDao extends CrudRepository<Employee, Long> {
}
