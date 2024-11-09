package org.example.lab9.Dao;

import org.example.lab9.Entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CategoryDao {

    private final String CATEGORIES_URL = "https://www.themealdb.com/api/json/v1/1/categories.php";

    public List<Category> listar() {
        RestTemplate restTemplate = new RestTemplate();
        List<Category> lista = new ArrayList<>();

        // Realizar la solicitud y obtener la respuesta como un Map
        Map<String, Object> response = restTemplate.getForObject(CATEGORIES_URL, Map.class);

        // Extraer la lista de categorías del Map y convertir cada entrada a un objeto Category
        if (response != null && response.containsKey("categories")) {
            List<Map<String, Object>> categoriesData = (List<Map<String, Object>>) response.get("categories");
            for (Map<String, Object> categoryData : categoriesData) {
                Category category = new Category();
                category.setIdCategory((String) categoryData.get("idCategory"));
                category.setStrCategory((String) categoryData.get("strCategory"));
                category.setStrCategoryThumb((String) categoryData.get("strCategoryThumb"));
                category.setStrCategoryDescription((String) categoryData.get("strCategoryDescription"));
                lista.add(category);
            }
        }

        return lista;
    }
}
