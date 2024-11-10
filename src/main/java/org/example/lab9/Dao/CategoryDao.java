package org.example.lab9.Dao;

import org.example.lab9.Entity.Category;
import org.example.lab9.Entity.Meal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CategoryDao {

    private final String categories_url = "https://www.themealdb.com/api/json/v1/1/categories.php";
    private final String search_url = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    public List<Category> listar() {
        RestTemplate restTemplate = new RestTemplate();
        List<Category> lista = new ArrayList<>();

        Map<String, Object> response = restTemplate.getForObject(categories_url, Map.class);

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

    public List<Meal> buscarComidaPorNombre(String nombre) {
        RestTemplate restTemplate = new RestTemplate();
        List<Meal> meals = new ArrayList<>();

        ResponseEntity<Map> response = restTemplate.getForEntity(search_url + nombre, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("meals")) {
            List<Map<String, Object>> mealsData = (List<Map<String, Object>>) response.getBody().get("meals");
            for (Map<String, Object> mealData : mealsData) {
                Meal meal = new Meal();
                meal.setIdMeal((String) mealData.get("idMeal"));
                meal.setStrMeal((String) mealData.get("strMeal"));
                meal.setStrCategory((String) mealData.get("strCategory"));
                meal.setStrInstructions((String) mealData.get("strInstructions"));
                meal.setStrMealThumb((String) mealData.get("strMealThumb"));
                meals.add(meal);
            }
        }

        return meals;
    }

    public Meal obtenerDetalleComida(String idMeal) {
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idMeal;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("meals")) {
            List<Map<String, Object>> mealsData = (List<Map<String, Object>>) response.get("meals");
            if (!mealsData.isEmpty()) {
                Map<String, Object> mealData = mealsData.get(0);
                Meal meal = new Meal();
                meal.setIdMeal((String) mealData.get("idMeal"));
                meal.setStrMeal((String) mealData.get("strMeal"));
                meal.setStrCategory((String) mealData.get("strCategory"));
                meal.setStrArea((String) mealData.get("strArea"));
                meal.setStrInstructions((String) mealData.get("strInstructions"));
                meal.setStrMealThumb((String) mealData.get("strMealThumb"));
                meal.setStrTags((String) mealData.get("strTags"));
                meal.setStrYoutube((String) mealData.get("strYoutube"));

                // Procesar ingredientes y medidas
                List<String> ingredients = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    String ingredient = (String) mealData.get("strIngredient" + i);
                    String measure = (String) mealData.get("strMeasure" + i);

                    if (ingredient != null && !ingredient.isEmpty()) {
                        String fullIngredient = (measure != null ? measure : "") + " " + ingredient;
                        ingredients.add(fullIngredient.trim());
                    }
                }
                meal.setIngredients(ingredients);
                return meal;
            }
        }
        return null;
    }
}
