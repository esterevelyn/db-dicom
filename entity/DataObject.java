package processor.entity;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataObject {

    @Id
    private String id;

    /**
     * Encapsula o mapa multivalorado (mapa de listas).
     * 
     * Um exemplo de mapa contido na vari�vel 'data':
     * 
     * {AttrA=[valor1,valor2,valor3], AttrB=[valorX], AttrC=[ValueA, ValueB]}
     */

    Map<String, List<String>> data = new HashMap<>();
   // Map<String, String> data = new HashMap<>();

    public void insertData(String key, String value) {
        // Se for uma chave ainda inexistente
       if (data.get(key) == null) {
            List<String> newList = new ArrayList<>();
            newList.add(value);
            data.put(key, newList);
      } else { // Se j� existir chave, adiciona na lista j� existente
           data.get(key).add(value);
        }
        //System.out.println(data.get(key));

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void get(){

       // Stdata.size();
        System.out.println(data.get("Study ID"));
        //System.out.println(data.get("DLP"));
    }
    public void Nam(String key){
        System.out.println(data.isEmpty());



    }
}
