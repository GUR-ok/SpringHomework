package homeworkSpringApp.controller;

import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;
import homeworkSpringApp.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
*        POST /lists - создание нового списка
*        GET /lists - отображает все имеющиеся списки (с краткой информацией по ним) для данного пользователя
*        GET /lists/{id} - Пользователь может получить более подробную информацию по каждому списку по id этого списка (вывод всех элементов списка)
*        POST /lists/{id}/elements - добавление элемента(из тела запроса) в конец списка с заданным идентификатором
*        DELETE /lists/{id}/elements/{id_element} - удаление элеиента по его id из списка с заданным идентификатором
*        GET /lists/{id}/elements/{id_element} - возвращает элемент из списка
*        GET /lists/{id}/size - размер списка
*        PUT /lists/{id}/elements - добавить список элементов
*        GET /lists/{id}/find?element={json_element} - возвращает, сколько раз элемент встречвается в списке
*        Для sort и shuffle методы
*        GET /lists/{id}/sort - возвращает отсортированный список
*        в качестве сортировки можно сразу же определить, по какому компаратору будет происходить сортировка
*        GET /lists/{id}/shuffle - возвращает "перемешанный" список
*/

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
    @PostMapping("/lists")
    public ResponseEntity<Long> addList(@RequestBody CarList list) {
        log.info("addUserList through controller");
        service.addCarList(list);
        return ResponseEntity.ok(list.getId());
    }

    //Получение всех списков пользователя по uuid
    @GetMapping("/lists")
    public ResponseEntity<List<CarListDTO>> getUserLists() {
        log.info("getLists through controller");
        return service.getLists();
    }

    //Получение подробной информации по списку
    @GetMapping("/lists/{id}")
    public ResponseEntity<CarListDTO> getListInformation(@PathVariable long id) {
        log.info("getListInformation through controller");
        return service.getCarList(id);
    }

    //Добавление элемента в список
    @PostMapping("/lists/{id}/elements")
    public ResponseEntity addElementToList(@PathVariable long id, @RequestBody Car car) {
        log.info("addElementToList through controller");
        return service.addCarToList(id, car);
    }

    //Удаление элемента списка
    @DeleteMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity deleteElement(@PathVariable long id, @PathVariable int id_element) {
        log.info("deleteElementInList through controller");
        return service.deleteEelementFromList(id, id_element);
    }

    //Получение элемента списка
    @GetMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity<CarDTO> getElement(@PathVariable long id, @PathVariable int id_element) {
        log.info("getElementInList through controller");
        return service.getCarFromList(id, id_element);
    }

    //Получение размера списка
    @GetMapping("/lists/{id}/size")
    public ResponseEntity<Integer> getListSize(@PathVariable long id) {
        log.info("getListSize through controller");
        return service.getListSize(id);
    }

    //Добавить список элементов
    @PutMapping("/lists/{id}/elements")
    public ResponseEntity<String> addListToList(@PathVariable long id, @RequestBody CarList list) {
        log.info("putElementsToList through controller");
        return service.addListToList(id, list);
    }

    //Сколько раз элемент встречается в списке
    @GetMapping("/lists/{id}/find")
    public ResponseEntity<Integer> countElement(@PathVariable long id, @RequestBody Car car) {
        log.info("countElementsInList through controller");
        return service.countElement(id, car);
    }


    /*     GET /lists/{id}/sort - возвращает отсортированный список
*        в качестве сортировки можно сразу же определить, по какому компаратору будет происходить сортировка
*        GET /lists/{id}/shuffle - возвращает "перемешанный" список
    */

}
