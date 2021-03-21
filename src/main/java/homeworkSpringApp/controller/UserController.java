package homeworkSpringApp.controller;

import homeworkSpringApp.dto.NumberListDTO;
import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;
import homeworkSpringApp.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {

    private final ListService service;

    //Создание нового юзера
    @PostMapping
    public ResponseEntity<String> addUser( @RequestBody User user) {
        log.info("addUser through controller");
        service.createUser(user);
        return ResponseEntity.ok(user.getUuid().toString());
    }

    //Создание нового списка
    @PostMapping("/{uuid}/lists")
    public ResponseEntity addList(@PathVariable UUID uuid, @RequestBody NumberList list) {
        log.info("addUserList through controller");
        if (service.getUser(uuid).isPresent()) {
            service.addNumberList(list,uuid);
            return ResponseEntity.ok().body(null);
        }
        else return ResponseEntity.notFound().build();
    }

    //Получение всех списков пользователя по uuid
    @GetMapping("/{uuid}/lists")
    public ResponseEntity<List<NumberListDTO>> getUserLists(@PathVariable UUID uuid) {
        log.info("getUserLists through controller");
        List<NumberListDTO> result = service.getUserLists(uuid)
                .stream()
                .map(NumberListDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    //Получение подробной информации по списку
    @GetMapping("/lists/{id}")
    public ResponseEntity<NumberListDTO> getListInformation(@PathVariable long id) {
        log.info("getListInformation through controller");
        if (service.getNumberList(id).isPresent())
            return ResponseEntity.ok(NumberListDTO.from(service.getNumberList(id).get()));
        else
            return ResponseEntity.notFound().build();
    }

    //Добавление элемента в список
    @PostMapping("/lists/{id}/elements")
    public void addNumberToList(@PathVariable Long id, @RequestParam Double number) {
        log.info("addElementToList through controller");
        NumberList list = service.getNumberList(id).get();
        list.getNumlist().add(number);
        service.addNumberList(list, list.getUser().getUuid());
    }

    //Удаление элемента списка
    @DeleteMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity deleteElement(@PathVariable long id, @PathVariable int id_element) {
        log.info("deleteElementInList through controller");
        if (service.getNumberList(id).isPresent()) {
            NumberList numberList = service.getNumberList(id).get();
            numberList.getNumlist().remove(id_element);
            service.addNumberList(numberList, numberList.getUser().getUuid());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
