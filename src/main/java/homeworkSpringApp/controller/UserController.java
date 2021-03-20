package homeworkSpringApp.controller;

import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;
import homeworkSpringApp.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myapp")
@Slf4j
public class UserController {

    private final ListService service;

    @PostMapping
    public ResponseEntity<String> addUser( @RequestBody User user) {
        log.info("addUser through controller");
        service.createUser(user);
        return ResponseEntity.ok(user.getUuid().toString());
    }

    @PostMapping("/{uuid}/lists")
    public ResponseEntity addList(@PathVariable UUID uuid, @RequestBody NumberList list) {
        log.info("addUserList through controller");
        if (service.getUser(uuid).isPresent()) {
            service.addNumberList(list,uuid);
            return ResponseEntity.ok().body(null);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/lists/{id}/elements")
    public void addNumberToList(@PathVariable Long id, @RequestParam Double number) {
        log.info("addElementToList through controller");
        NumberList list = service.getNumberList(id).get();
        list.add(number);
        service.addNumberList(list, list.getUser().getUuid());
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<String> getListInformation(@PathVariable Long id, @RequestParam Double number) {
        log.info("getListInformation through controller");
        NumberList list = service.getNumberList(id).get();
         return ResponseEntity.ok().body(list.getShortDescription());
    }
}
