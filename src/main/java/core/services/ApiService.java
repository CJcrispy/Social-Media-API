package core.services;

import blue.profit.entities.Symptom;
import blue.profit.repositories.SymptomRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    SymptomRepository symptomRepository;


    public Map<String, String> getSymptomList(){
        try {
            System.out.println("Calling symptom checker api");
            final String uri = "https://priaid-symptom-checker-v1.p.rapidapi.com/symptoms?format=json&language=en-gb";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

            headers.add("x-rapidapi-key", "e7b3f357e6msh94806218876b0cap19ff75jsn07435d8fed2c");
            headers.add("x-rapidapi-host" , "priaid-symptom-checker-v1.p.rapidapi.com");

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            String json = result.getBody();
            Map<String, String> map = jsonParserSymptomList(json);
            for (Object key : map.keySet()) {
                System.out.println(key.toString() + " ==> " + map.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Parses json from getSymptomList
    public Map<String, String> jsonParserSymptomList(String json){
        Map<String, String> jsonMap =  new HashMap<String, String>();
        try {
            JSONArray jsonResponse = new JSONArray(json);

            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject object = jsonResponse.getJSONObject(i);
                String ID = object.getString("ID");
                String Name = object.getString("Name");

                jsonMap.put(ID, Name);

            }

            return jsonMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSymptom(List<String> symptom_id) {
        try{
            if(symptom_id.size() > 1) {
                String elements = null;
                for (String symptom : symptom_id) {
                    elements = String.join(", ", symptom);
                    System.out.println(elements);
                }
                System.out.println("Calling symptom checker api");
                final String uri = "https://priaid-symptom-checker-v1.p.rapidapi.com/symptoms?symptoms=[" + elements + "]&format=json&language=en-gb";

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();

                headers.add("x-rapidapi-key", "e7b3f357e6msh94806218876b0cap19ff75jsn07435d8fed2c");
                headers.add("x-rapidapi-host", "priaid-symptom-checker-v1.p.rapidapi.com");

                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

                ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
                System.out.println(result.getBody());

                String json = result.getBody();
            } else if (symptom_id.size() == 1 ){
                System.out.println("Calling symptom checker api");
                final String uri = "https://priaid-symptom-checker-v1.p.rapidapi.com/symptoms?symptoms=[" + symptom_id + "]&format=json&language=en-gb";

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();

                headers.add("x-rapidapi-key", "e7b3f357e6msh94806218876b0cap19ff75jsn07435d8fed2c");
                headers.add("x-rapidapi-host" , "priaid-symptom-checker-v1.p.rapidapi.com");

                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

                ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
                System.out.println(result.getBody());

                String json = result.getBody();
            } else if (symptom_id.isEmpty()){
                return null;
            }
        } catch (Exception e){

        }

        return null;
    }

    public Symptom getById(int id) {
        return this.symptomRepository.getById(id);
    }

    public Symptom getByName(String name) {
        return this.symptomRepository.getBySymptom(name);
    }

    public Symptom create(Symptom symptom) {
        return this.symptomRepository.create(symptom);
    }

    public Symptom update(Symptom symptom) {
        return this.symptomRepository.update(symptom);
    }

    public Symptom deleteById(int id) {
        return this.symptomRepository.deleteById(id);
    }


}
