# SpringHomework
MB homework part2: Spring. App for Car listing. 

###   Author: GORELOV YURIY

Features:
- REST controller
- Liquibase implementation, hibernate.ddl-auto: validate; datasource:
    platform: postgres;
    url: jdbc:postgresql://localhost:5432/mylists
- spring security and auth (access token + refresh token)

#### Task:
*        POST /lists - создание нового списка
*        GET /lists - отображает все имеющиеся списки (с краткой информацией по ним) для данного пользователя
*        GET /lists/{id} - Пользователь может получить более подробную информацию по каждому списку по id этого списка (вывод всех элементов списка)
*        POST /lists/{id}/elements - добавление элемента(из тела запроса) в конец списка с заданным идентификатором
*        DELETE /lists/{id}/elements/{id_element} - удаление элеиента по его id из списка с заданным идентификатором
*        GET /lists/{id}/elements/{id_element} - возвращает элемент из списка
*        GET /lists/{id}/size - размер списка
*        PUT /lists/{id}/elements - добавить список элементов
*        GET /lists/{id}/find?element={json_element} - возвращает, сколько раз элемент встречается в списке
*        Для sort и shuffle методы
*        GET /lists/{id}/sort - возвращает отсортированный список
*        в качестве сортировки можно сразу же определить, по какому компаратору будет происходить сортировка
*        GET /lists/{id}/shuffle - возвращает "перемешанный" список


Authentication admin user added by default:

 login: Admin
 
 password: test

---------------------------

* Example of CarList json: 

      {

        "shortDescription":"Some text here",
  
        "cars": [
  
          {
    
       	    "name": "bmw",
        
  		      "price": "2000.0",
      
 		        "horsePower": "350.0"
    
          },
    
          {
    
     	      "name": "AUDI",
      
  		      "price": "1000.0",
      
 		        "horsePower": "150.0"
    
          }
    
        ]
  
      }


* Example of Car json:
{
  "name": "AUDI",
  "price": "1000.0",
  "horsePower": "150.0"
}

* Example GET /lists/{id}/find?element={json_element}
http://localhost:8080/lists/45/find?element={"name":"bmw","price":"2000","horsepower":"350"}
