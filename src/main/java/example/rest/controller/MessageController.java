package example.rest.controller;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;

    // импровизированная база данных (создаем список пар (id_сообщеий -> сообщение) )
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    //get - получаем одно сообщение
    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    //создаем новое сообщение (передаем в RequestBody)
    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return message;
    }

    //обновляем сообщение
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    //удаляем сообщение
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);

        messages.remove(message);
    }


        //поиск сообщения по ID (проходим в цикле по всем сообщениям и находим сообщение с ID из пути)
    public Map<String,String> getMessage(String id) {
        Map<String, String> resultMessage = new HashMap<String, String>();


        for (Map<String, String> message : messages) { //message = 1 с
            if (message.get("id").equals(id)) {
                resultMessage = message;
            }
        }
        return resultMessage;
    }


}



