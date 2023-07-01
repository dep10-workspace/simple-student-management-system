package lk.ijse.dep10.sms.smsApp.api;

import lk.ijse.dep10.sms.smsApp.business.StudentBO;
import lk.ijse.dep10.sms.smsApp.dto.StudentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/students")
public class StudentHTTPController {
    private final StudentBO studentBO;

    public StudentHTTPController(StudentBO studentBO) {
        this.studentBO = studentBO;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> validExceptionHandler(MethodArgumentNotValidException exp) {
        LinkedHashMap<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", 400);
        errorAttributes.put("error", HttpStatus.BAD_REQUEST);

        List<FieldError> fieldErrors = exp.getFieldErrors();
        ArrayList<HashMap<String,Object>> errorList = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            LinkedHashMap<String, Object> error = new LinkedHashMap<>();
            error.put("field", fieldError.getField());
            error.put("rejected value", fieldError.getRejectedValue());
            error.put("field", fieldError.getDefaultMessage());
            errorList.add(error);
        });
        errorAttributes.put("errors", errorList);
        return errorAttributes;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() throws Exception {
        System.out.println("GetAll");
        return studentBO.getAllStudents();
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO saveStudent(@RequestBody @Valid StudentDTO studentDTO) throws Exception {
        System.out.println("Save");
        return studentBO.saveStudent(studentDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("id") @Valid int id) throws Exception {
        System.out.println("Delete");
        studentBO.deleteStudentByStudentId(id);
    }

}
