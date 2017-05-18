package com.sh.ori.feedmeplease.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by omer on 18/05/2017.
 */

public class Recipe {
    private String lable;
    public ArrayList<Ingredient> ingredients;
    private String imageURL;
    public float matchRate;

    public Recipe(String lable){
        this.lable = lable;
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingre){
        this.ingredients.add(ingre);
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public int compareTo(Recipe other){
        return Float.compare(this.matchRate, other.matchRate);
    }




    public static Recipe[] getRecipes(Ingredient[] ingredients){

        String strIngr = "";
        for(int i=0; i<ingredients.length;i++){
            strIngr += ingredients[i].name;
            if(i>0){
                strIngr +="+";
            }
        }
        String getRequestURL = "https://edamam-recipe-search-and-diet-v1.p.mashape.com/search?q="+strIngr;
        String key1 = "X-Mashape-Key";
        String value1 = "k0od1PUczFmshycscTJ1SsAYQUKwp18ABlsjsnMnnKXqYYCkBb";
        String key2 = "Accept";
        String value2 = "application/json";

        JSONObject jsn = null;
        try {
            jsn = new JSONObject(someJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Recipe[] recipes = new Recipe[0];

        //createRecipes:
        try {
            JSONArray hits = jsn.getJSONArray("hits");
            recipes = new Recipe[hits.length()];
            for(int i=0; i<hits.length(); i++){

                JSONObject jsonRecipe = ((JSONObject) hits.get(i)).getJSONObject("recipe");
                String recipeLable = jsonRecipe.getString("label");
                Recipe recipe = new Recipe(recipeLable);
                String imageURL = jsonRecipe.getString("image");
                recipe.setImageURL(imageURL);
                JSONArray recipeIngredients = jsonRecipe.getJSONArray("ingredients");
                for(int j=0; j<recipeIngredients.length();j++){
                    JSONObject ingredientObj = recipeIngredients.getJSONObject(j);
                    String foodName = ingredientObj.getString("food");
                    Ingredient ingre = new Ingredient(foodName);
                    recipe.addIngredient(ingre);
                }
                recipes[i] = recipe;
            }

            //set recipe values:
            for(Recipe recipe: recipes){
                int sameIngre=0;
                for (Ingredient ingre: ingredients){
                    for(Ingredient ingreRecipe : recipe.ingredients){
                        if(ingreRecipe.name.toLowerCase().indexOf(ingre.name.toLowerCase()) != -1){
                            sameIngre++;
                        }
                    }
                    if(recipe.ingredients.contains(ingre)){
                        sameIngre++;
                    }
                }
                recipe.matchRate = (float)sameIngre/(float)recipe.ingredients.size();
            }

            Arrays.sort(recipes, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return -o1.compareTo(o2);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }


    static String someJSON = "{\n" +
            "  \"q\": \"bread\",\n" +
            "  \"from\": 0,\n" +
            "  \"to\": 10,\n" +
            "  \"params\": {\n" +
            "    \"sane\": [],\n" +
            "    \"q\": [\n" +
            "      \"bread\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"more\": true,\n" +
            "  \"count\": 1000,\n" +
            "  \"hits\": [\n" +
            "    {\n" +
            "      \"recipe\": {\n" +
            "        \"uri\": \"http://www.edamam.com/ontologies/edamam.owl#recipe_ca56190d6be97e526a060d97145ed87c\",\n" +
            "        \"label\": \"Picnic Breads\",\n" +
            "        \"image\": \"https://www.edamam.com/web-img/1a9/1a937dec6e5db2ba8742d96e1b9acfb7.jpg\",\n" +
            "        \"source\": \"Honest Cooking\",\n" +
            "        \"url\": \"http://honestcooking.com/picnic-bread-rolls-recipe/\",\n" +
            "        \"shareAs\": \"http://www.edamam.com/recipe/picnic-breads-ca56190d6be97e526a060d97145ed87c/bread\",\n" +
            "        \"yield\": 4,\n" +
            "        \"dietLabels\": [],\n" +
            "        \"healthLabels\": [\n" +
            "          \"Vegetarian\",\n" +
            "          \"Egg-Free\",\n" +
            "          \"Peanut-Free\",\n" +
            "          \"Soy-Free\",\n" +
            "          \"Fish-Free\",\n" +
            "          \"Shellfish-Free\"\n" +
            "        ],\n" +
            "        \"cautions\": [],\n" +
            "        \"ingredientLines\": [\n" +
            "          \"4 small bread rolls (crispy on the outside)\",\n" +
            "          \"1 zucchini, sliced in thin rounds\",\n" +
            "          \"4 tablespoons sundried tomato spread (or pesto/tapenade)\",\n" +
            "          \"A handful of fresh basil leaves\",\n" +
            "          \"7 oz. (200 gr.) mozzarella cheese\",\n" +
            "          \"2 roasted red bell peppers\"\n" +
            "        ],\n" +
            "        \"ingredients\": [\n" +
            "          {\n" +
            "            \"text\": \"4 small bread rolls (crispy on the outside)\",\n" +
            "            \"quantity\": 4,\n" +
            "            \"measure\": \"roll\",\n" +
            "            \"food\": \"small bread rolls\",\n" +
            "            \"weight\": 168\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 zucchini, sliced in thin rounds\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"zucchini, sliced\",\n" +
            "            \"weight\": 196\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"4 tablespoons sundried tomato spread (or pesto/tapenade)\",\n" +
            "            \"quantity\": 4,\n" +
            "            \"measure\": \"tablespoon\",\n" +
            "            \"food\": \"tapenade\",\n" +
            "            \"weight\": 30\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"A handful of fresh basil leaves\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"leaf\",\n" +
            "            \"food\": \"fresh basil leaves\",\n" +
            "            \"weight\": 0.5\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"7 oz. (200 gr.) mozzarella cheese\",\n" +
            "            \"quantity\": 7,\n" +
            "            \"measure\": \"ounce\",\n" +
            "            \"food\": \"mozzarella cheese\",\n" +
            "            \"weight\": 198.4466552734375\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 roasted red bell peppers\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"pepper\",\n" +
            "            \"food\": \"roasted red bell peppers\",\n" +
            "            \"weight\": 146\n" +
            "          }\n" +
            "        ],\n" +
            "        \"calories\": 1197.4353637695312,\n" +
            "        \"totalWeight\": 738.9466552734375,\n" +
            "        \"totalNutrients\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 1197.4353637695312,\n" +
            "            \"unit\": \"kcal\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 59.858855859375,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 33.001529027099615,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FATRN\": {\n" +
            "            \"label\": \"Trans\",\n" +
            "            \"quantity\": 0.04368,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAMS\": {\n" +
            "            \"label\": \"Monounsaturated\",\n" +
            "            \"quantity\": 18.39978646606445,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAPU\": {\n" +
            "            \"label\": \"Polyunsaturated\",\n" +
            "            \"quantity\": 4.789399978027344,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 103.7268823852539,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 11.43,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"SUGAR\": {\n" +
            "            \"label\": \"Sugars\",\n" +
            "            \"quantity\": 23.406011218261717,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 64.9136275390625,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 176.61752319335938,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 2747.831252441406,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 1425.4932678222658,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 170.77379760742187,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 1214.1899914550781,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 7.819743310546875,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 7.699437719726562,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 1170.1602197265624,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 632.4799108886718,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 285.17,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 0.9097614648437499,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 1.1913659692382814,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 11.28412985595703,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 0.9792519262695312,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 253.83573242187498,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 1.4486605834960937,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 0.9922332763671875,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 4.5271379760742185,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 31.56116638183594,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"totalDaily\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 59.871768188476565,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 92.09054747596154,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 165.00764513549808,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 34.5756274617513,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 45.72,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 129.827255078125,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 58.87250773111979,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 114.49296885172524,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 142.5493267822266,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 42.69344940185547,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 34.691142613002235,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 43.44301839192708,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 51.32958479817708,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 167.1657456752232,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 70.27554565429688,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 475.28333333333336,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 60.650764322916665,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 70.0803511316636,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 56.42064927978515,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 48.96259631347656,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 63.45893310546874,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 24.14434305826823,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 0.24805831909179688,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 22.635689880371093,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 39.45145797729492,\n" +
            "            \"unit\": \"%\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"digest\": [\n" +
            "          {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"tag\": \"FAT\",\n" +
            "            \"schemaOrgTag\": \"fatContent\",\n" +
            "            \"total\": 59.858855859375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 92.09054747596154,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Saturated\",\n" +
            "                \"tag\": \"FASAT\",\n" +
            "                \"schemaOrgTag\": \"saturatedFatContent\",\n" +
            "                \"total\": 33.001529027099615,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 165.00764513549808,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Trans\",\n" +
            "                \"tag\": \"FATRN\",\n" +
            "                \"schemaOrgTag\": \"transFatContent\",\n" +
            "                \"total\": 0.04368,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Monounsaturated\",\n" +
            "                \"tag\": \"FAMS\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 18.39978646606445,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Polyunsaturated\",\n" +
            "                \"tag\": \"FAPU\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 4.789399978027344,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"tag\": \"CHOCDF\",\n" +
            "            \"schemaOrgTag\": \"carbohydrateContent\",\n" +
            "            \"total\": 103.7268823852539,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 34.5756274617513,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Carbs (net)\",\n" +
            "                \"tag\": \"CHOCDF.net\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 92.2968823852539,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Fiber\",\n" +
            "                \"tag\": \"FIBTG\",\n" +
            "                \"schemaOrgTag\": \"fiberContent\",\n" +
            "                \"total\": 11.43,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 45.72,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars\",\n" +
            "                \"tag\": \"SUGAR\",\n" +
            "                \"schemaOrgTag\": \"sugarContent\",\n" +
            "                \"total\": 23.406011218261717,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"tag\": \"PROCNT\",\n" +
            "            \"schemaOrgTag\": \"proteinContent\",\n" +
            "            \"total\": 64.9136275390625,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 129.827255078125,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"tag\": \"CHOLE\",\n" +
            "            \"schemaOrgTag\": \"cholesterolContent\",\n" +
            "            \"total\": 176.61752319335938,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 58.87250773111979,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"tag\": \"NA\",\n" +
            "            \"schemaOrgTag\": \"sodiumContent\",\n" +
            "            \"total\": 2747.831252441406,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 114.49296885172524,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"tag\": \"CA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1425.4932678222658,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 142.5493267822266,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"tag\": \"MG\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 170.77379760742187,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 42.69344940185547,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"tag\": \"K\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1214.1899914550781,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 34.691142613002235,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"tag\": \"FE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 7.819743310546875,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 43.44301839192708,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"tag\": \"ZN\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 7.699437719726562,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 51.32958479817708,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"tag\": \"P\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1170.1602197265624,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 167.1657456752232,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"tag\": \"VITA_RAE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 632.4799108886718,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 70.27554565429688,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"tag\": \"VITC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 285.17,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 475.28333333333336,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"tag\": \"THIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.9097614648437499,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 60.650764322916665,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"tag\": \"RIBF\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.1913659692382814,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 70.0803511316636,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"tag\": \"NIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 11.28412985595703,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 56.42064927978515,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"tag\": \"VITB6A\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.9792519262695312,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 48.96259631347656,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"tag\": \"FOLDFE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 253.83573242187498,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 63.45893310546874,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"tag\": \"VITB12\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.4486605834960937,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 24.14434305826823,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"tag\": \"VITD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.9922332763671875,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 0.24805831909179688,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"tag\": \"TOCPHA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 4.5271379760742185,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 22.635689880371093,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"tag\": \"VITK1\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 31.56116638183594,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 39.45145797729492,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"bookmarked\": false,\n" +
            "      \"bought\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"recipe\": {\n" +
            "        \"uri\": \"http://www.edamam.com/ontologies/edamam.owl#recipe_94486a43e21538272a079ce4814fe891\",\n" +
            "        \"label\": \"Fast Breads' Crusty Artisanal Bread\",\n" +
            "        \"image\": \"https://www.edamam.com/web-img/7e3/7e3139049920dac6a9254cbe7f8fce6b.JPG\",\n" +
            "        \"source\": \"Serious Eats\",\n" +
            "        \"url\": \"http://www.seriouseats.com/recipes/2012/05/crusty-artisanal-bread-recipe.html\",\n" +
            "        \"shareAs\": \"http://www.edamam.com/recipe/fast-breads-crusty-artisanal-bread-94486a43e21538272a079ce4814fe891/bread\",\n" +
            "        \"yield\": 1,\n" +
            "        \"dietLabels\": [\n" +
            "          \"High-Fiber\",\n" +
            "          \"Low-Fat\"\n" +
            "        ],\n" +
            "        \"healthLabels\": [\n" +
            "          \"Vegan\",\n" +
            "          \"Vegetarian\",\n" +
            "          \"Dairy-Free\",\n" +
            "          \"Egg-Free\",\n" +
            "          \"Peanut-Free\",\n" +
            "          \"Tree-Nut-Free\",\n" +
            "          \"Soy-Free\",\n" +
            "          \"Fish-Free\",\n" +
            "          \"Shellfish-Free\"\n" +
            "        ],\n" +
            "        \"cautions\": [],\n" +
            "        \"ingredientLines\": [\n" +
            "          \"3 cups unbleached all-purpose/plain flour\",\n" +
            "          \"1 cup unbleached bread/strong flour\",\n" +
            "          \"1/4 cup whole-wheat flour\",\n" +
            "          \"1 tbsp kosher salt\",\n" +
            "          \"2 1/4 tsp active dry yeast (one 1/4-oz packet)\",\n" +
            "          \"2 cups water, at room temperature\"\n" +
            "        ],\n" +
            "        \"ingredients\": [\n" +
            "          {\n" +
            "            \"text\": \"3 cups unbleached all-purpose/plain flour\",\n" +
            "            \"quantity\": 3,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"flour\",\n" +
            "            \"weight\": 375\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 cup unbleached bread/strong flour\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"flour\",\n" +
            "            \"weight\": 125\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/4 cup whole-wheat flour\",\n" +
            "            \"quantity\": 0.25,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"whole-wheat flour\",\n" +
            "            \"weight\": 30\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 tbsp kosher salt\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"tablespoon\",\n" +
            "            \"food\": \"kosher salt\",\n" +
            "            \"weight\": 14.772500991821289\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 1/4 tsp active dry yeast (one 1/4-oz packet)\",\n" +
            "            \"quantity\": 2.25,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"yeast\",\n" +
            "            \"weight\": 9\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 cups water, at room temperature\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"water\",\n" +
            "            \"weight\": 474\n" +
            "          }\n" +
            "        ],\n" +
            "        \"calories\": 1951.25,\n" +
            "        \"totalWeight\": 1018.9898891583673,\n" +
            "        \"totalNutrients\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 1951.25,\n" +
            "            \"unit\": \"kcal\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 6.3349,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 0.99409,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAMS\": {\n" +
            "            \"label\": \"Monounsaturated\",\n" +
            "            \"quantity\": 0.9077099999999999,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAPU\": {\n" +
            "            \"label\": \"Polyunsaturated\",\n" +
            "            \"quantity\": 2.4166299999999996,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 406.8508,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 19.131,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"SUGAR\": {\n" +
            "            \"label\": \"Sugars\",\n" +
            "            \"quantity\": 1.473,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 59.2526,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 2355.7112400000005,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 103.55757339800816,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 160.7598988915837,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 730.3291911326694,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 7.145066634222611,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 5.047989889158367,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 704.4300000000001,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 0.027,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 1.7397,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 0.6094999999999999,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 11.3551,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 0.47709999999999997,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 353.79999999999995,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 0.0063,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 0.513,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 2.106,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"totalDaily\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 97.5625,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 9.746,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 4.9704500000000005,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 135.61693333333335,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 76.524,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 118.5052,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 98.15463500000003,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 10.355757339800816,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 40.189974722895926,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 20.86654831807627,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 39.694814634570065,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 33.653265927722444,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 100.63285714285715,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 0.045000000000000005,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 115.98,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 35.85294117647059,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 56.7755,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 23.854999999999997,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 88.44999999999999,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 0.105,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 2.5650000000000004,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 2.6325,\n" +
            "            \"unit\": \"%\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"digest\": [\n" +
            "          {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"tag\": \"FAT\",\n" +
            "            \"schemaOrgTag\": \"fatContent\",\n" +
            "            \"total\": 6.3349,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 9.746,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Saturated\",\n" +
            "                \"tag\": \"FASAT\",\n" +
            "                \"schemaOrgTag\": \"saturatedFatContent\",\n" +
            "                \"total\": 0.99409,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 4.9704500000000005,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Trans\",\n" +
            "                \"tag\": \"FATRN\",\n" +
            "                \"schemaOrgTag\": \"transFatContent\",\n" +
            "                \"total\": 0,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Monounsaturated\",\n" +
            "                \"tag\": \"FAMS\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 0.9077099999999999,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Polyunsaturated\",\n" +
            "                \"tag\": \"FAPU\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 2.4166299999999996,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"tag\": \"CHOCDF\",\n" +
            "            \"schemaOrgTag\": \"carbohydrateContent\",\n" +
            "            \"total\": 406.8508,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 135.61693333333335,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Carbs (net)\",\n" +
            "                \"tag\": \"CHOCDF.net\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 387.71979999999996,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Fiber\",\n" +
            "                \"tag\": \"FIBTG\",\n" +
            "                \"schemaOrgTag\": \"fiberContent\",\n" +
            "                \"total\": 19.131,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 76.524,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars\",\n" +
            "                \"tag\": \"SUGAR\",\n" +
            "                \"schemaOrgTag\": \"sugarContent\",\n" +
            "                \"total\": 1.473,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"tag\": \"PROCNT\",\n" +
            "            \"schemaOrgTag\": \"proteinContent\",\n" +
            "            \"total\": 59.2526,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 118.5052,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"tag\": \"CHOLE\",\n" +
            "            \"schemaOrgTag\": \"cholesterolContent\",\n" +
            "            \"total\": 0,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"tag\": \"NA\",\n" +
            "            \"schemaOrgTag\": \"sodiumContent\",\n" +
            "            \"total\": 2355.7112400000005,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 98.15463500000003,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"tag\": \"CA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 103.55757339800816,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 10.355757339800816,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"tag\": \"MG\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 160.7598988915837,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 40.189974722895926,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"tag\": \"K\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 730.3291911326694,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 20.86654831807627,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"tag\": \"FE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 7.145066634222611,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 39.694814634570065,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"tag\": \"ZN\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 5.047989889158367,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 33.653265927722444,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"tag\": \"P\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 704.4300000000001,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 100.63285714285715,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"tag\": \"VITA_RAE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"tag\": \"VITC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.027,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 0.045000000000000005,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"tag\": \"THIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.7397,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 115.98,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"tag\": \"RIBF\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.6094999999999999,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 35.85294117647059,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"tag\": \"NIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 11.3551,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 56.7755,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"tag\": \"VITB6A\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.47709999999999997,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 23.854999999999997,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"tag\": \"FOLDFE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 353.79999999999995,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 88.44999999999999,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"tag\": \"VITB12\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.0063,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 0.105,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"tag\": \"VITD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"tag\": \"TOCPHA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0.513,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 2.5650000000000004,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"tag\": \"VITK1\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2.106,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 2.6325,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"bookmarked\": false,\n" +
            "      \"bought\": false\n" +
            "    }," +
            "{\n" +
            "      \"recipe\": {\n" +
            "        \"uri\": \"http://www.edamam.com/ontologies/edamam.owl#recipe_917d99da711d9e410bda66e4584b900c\",\n" +
            "        \"label\": \"Stuffed Bread\",\n" +
            "        \"image\": \"https://www.edamam.com/web-img/40b/40bfe88c879112dfc1786938c6c36832.jpg\",\n" +
            "        \"source\": \"Not Without Salt\",\n" +
            "        \"url\": \"http://notwithoutsalt.com/stuffed-bread/\",\n" +
            "        \"shareAs\": \"http://www.edamam.com/recipe/stuffed-bread-917d99da711d9e410bda66e4584b900c/bread\",\n" +
            "        \"yield\": 8,\n" +
            "        \"dietLabels\": [\n" +
            "          \"Low-Carb\"\n" +
            "        ],\n" +
            "        \"healthLabels\": [\n" +
            "          \"Peanut-Free\",\n" +
            "          \"Tree-Nut-Free\",\n" +
            "          \"Soy-Free\",\n" +
            "          \"Fish-Free\",\n" +
            "          \"Shellfish-Free\"\n" +
            "        ],\n" +
            "        \"cautions\": [],\n" +
            "        \"ingredientLines\": [\n" +
            "          \"1/2 lb sliced ham\",\n" +
            "          \"5 x hard boiled eggs\",\n" +
            "          \"1 1/2 cup cheese\",\n" +
            "          \"5-7 large fresh basil leaves, torn\",\n" +
            "          \"1/2 cup sun-dried tomatoes\",\n" +
            "          \"Olive oil\",\n" +
            "          \"Salt and pepper to taste\",\n" +
            "          \"1 x recipe white bread\"\n" +
            "        ],\n" +
            "        \"ingredients\": [\n" +
            "          {\n" +
            "            \"text\": \"1/2 lb sliced ham\",\n" +
            "            \"quantity\": 0.5,\n" +
            "            \"measure\": \"pound\",\n" +
            "            \"food\": \"sliced ham\",\n" +
            "            \"weight\": 226.7961883544922\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"5 x hard boiled eggs\",\n" +
            "            \"quantity\": 5,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"hard boiled eggs\",\n" +
            "            \"weight\": 200\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 1/2 cup cheese\",\n" +
            "            \"quantity\": 1.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"cheese\",\n" +
            "            \"weight\": 169.5\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"5-7 large fresh basil leaves, torn\",\n" +
            "            \"quantity\": 6,\n" +
            "            \"measure\": \"leaf\",\n" +
            "            \"food\": \"large fresh basil leaves, torn\",\n" +
            "            \"weight\": 3.75\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/2 cup sun-dried tomatoes\",\n" +
            "            \"quantity\": 0.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"sun-dried tomatoes\",\n" +
            "            \"weight\": 27\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Olive oil\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"Olive oil\",\n" +
            "            \"weight\": 8.913388166809082\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Salt and pepper to taste\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"Salt\",\n" +
            "            \"weight\": 3.9323771324157715\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Salt and pepper to taste\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"pepper\",\n" +
            "            \"weight\": 1.9661885662078857\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 x recipe white bread\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"white bread\",\n" +
            "            \"weight\": 28.350000381469727\n" +
            "          }\n" +
            "        ],\n" +
            "        \"calories\": 1597.7942727321206,\n" +
            "        \"totalWeight\": 666.2757654689789,\n" +
            "        \"totalNutrients\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 1597.7942727321206,\n" +
            "            \"unit\": \"kcal\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 108.7712981249134,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 47.61646307596652,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FATRN\": {\n" +
            "            \"label\": \"Trans\",\n" +
            "            \"quantity\": 2.0057760000991824,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAMS\": {\n" +
            "            \"label\": \"Monounsaturated\",\n" +
            "            \"quantity\": 39.13886781715996,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAPU\": {\n" +
            "            \"label\": \"Polyunsaturated\",\n" +
            "            \"quantity\": 8.744539330519249,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 43.39337678776646,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 7.960796171117783,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"SUGAR\": {\n" +
            "            \"label\": \"Sugars\",\n" +
            "            \"quantity\": 14.563218629368592,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 110.72719929976826,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 1048.1638273620606,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 4143.289940306289,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 1381.398434942402,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 186.55984405786515,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 2044.4768411791647,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 8.76794301804203,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 11.904036191263009,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 1636.301246685371,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 766.0958709128762,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 20.38754753494263,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 1.8611201243337248,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 2.375471855655289,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 10.873334866564809,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 1.2038570688373758,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 197.25648561872484,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 4.664143991088867,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 7.0045733184814445,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 4.949621514434051,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 42.65416037799339,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"totalDaily\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 79.88971363660603,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 167.34045865371291,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 238.0823153798326,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 14.464458929255485,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 31.843184684471133,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 221.45439859953652,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 349.38794245402016,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 172.6370808460954,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 138.13984349424018,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 46.63996101446629,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 58.413624033690425,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 48.71079454467795,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 79.36024127508672,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 233.75732095505302,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 85.12176343476402,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 33.97924589157105,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 124.07467495558166,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 139.7336385679582,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 54.36667433282405,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 60.192853441868785,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"quantity\": 49.31412140468121,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 77.73573318481445,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 1.7511433296203611,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 24.74810757217026,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 53.317700472491744,\n" +
            "            \"unit\": \"%\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"digest\": [\n" +
            "          {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"tag\": \"FAT\",\n" +
            "            \"schemaOrgTag\": \"fatContent\",\n" +
            "            \"total\": 108.7712981249134,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 167.34045865371291,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Saturated\",\n" +
            "                \"tag\": \"FASAT\",\n" +
            "                \"schemaOrgTag\": \"saturatedFatContent\",\n" +
            "                \"total\": 47.61646307596652,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 238.0823153798326,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Trans\",\n" +
            "                \"tag\": \"FATRN\",\n" +
            "                \"schemaOrgTag\": \"transFatContent\",\n" +
            "                \"total\": 2.0057760000991824,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Monounsaturated\",\n" +
            "                \"tag\": \"FAMS\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 39.13886781715996,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Polyunsaturated\",\n" +
            "                \"tag\": \"FAPU\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 8.744539330519249,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"tag\": \"CHOCDF\",\n" +
            "            \"schemaOrgTag\": \"carbohydrateContent\",\n" +
            "            \"total\": 43.39337678776646,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 14.464458929255485,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Carbs (net)\",\n" +
            "                \"tag\": \"CHOCDF.net\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 35.43258061664867,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Fiber\",\n" +
            "                \"tag\": \"FIBTG\",\n" +
            "                \"schemaOrgTag\": \"fiberContent\",\n" +
            "                \"total\": 7.960796171117783,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 31.843184684471133,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars\",\n" +
            "                \"tag\": \"SUGAR\",\n" +
            "                \"schemaOrgTag\": \"sugarContent\",\n" +
            "                \"total\": 14.563218629368592,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"tag\": \"PROCNT\",\n" +
            "            \"schemaOrgTag\": \"proteinContent\",\n" +
            "            \"total\": 110.72719929976826,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 221.45439859953652,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"tag\": \"CHOLE\",\n" +
            "            \"schemaOrgTag\": \"cholesterolContent\",\n" +
            "            \"total\": 1048.1638273620606,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 349.38794245402016,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"tag\": \"NA\",\n" +
            "            \"schemaOrgTag\": \"sodiumContent\",\n" +
            "            \"total\": 4143.289940306289,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 172.6370808460954,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"tag\": \"CA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1381.398434942402,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 138.13984349424018,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"tag\": \"MG\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 186.55984405786515,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 46.63996101446629,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"tag\": \"K\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2044.4768411791647,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 58.413624033690425,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"tag\": \"FE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 8.76794301804203,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 48.71079454467795,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"tag\": \"ZN\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 11.904036191263009,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 79.36024127508672,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"tag\": \"P\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1636.301246685371,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 233.75732095505302,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"tag\": \"VITA_RAE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 766.0958709128762,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 85.12176343476402,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"tag\": \"VITC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 20.38754753494263,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 33.97924589157105,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"tag\": \"THIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.8611201243337248,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 124.07467495558166,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"tag\": \"RIBF\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2.375471855655289,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 139.7336385679582,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"tag\": \"NIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 10.873334866564809,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 54.36667433282405,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"tag\": \"VITB6A\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.2038570688373758,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 60.192853441868785,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate (Equivalent)\",\n" +
            "            \"tag\": \"FOLDFE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 197.25648561872484,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 49.31412140468121,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"tag\": \"VITB12\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 4.664143991088867,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 77.73573318481445,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"tag\": \"VITD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 7.0045733184814445,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1.7511433296203611,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"tag\": \"TOCPHA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 4.949621514434051,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 24.74810757217026,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"tag\": \"VITK1\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 42.65416037799339,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 53.317700472491744,\n" +
            "            \"unit\": \"µg\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"bookmarked\": false,\n" +
            "      \"bought\": false\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
