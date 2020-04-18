package com.target.myretail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.models.Prices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class MyRetailControllerTest {
    @Mock
    private PricesRepository pricesRepository = mock(PricesRepository.class);

    @Mock
    private RestTemplate restTemplate = mock(RestTemplate.class);

    @InjectMocks
    private MyRetailController myRetailController = new MyRetailController();

    JsonNode node;
    JsonNode result;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        node = mapper.reader().readTree("{\n" +
                "  \"product\": {\n" +
                "    \"price\": {\n" +
                "      \"partNumber\": \"13860428\",\n" +
                "      \"listPrice\": {\n" +
                "        \"null\": false,\n" +
                "        \"price\": 12.49,\n" +
                "        \"formattedPrice\": \"$12.49\",\n" +
                "        \"priceType\": \"Reg\",\n" +
                "        \"maxPrice\": 0,\n" +
                "        \"minPrice\": 0\n" +
                "      },\n" +
                "      \"offerPrice\": {\n" +
                "        \"null\": false,\n" +
                "        \"startDate\": 1576742400000,\n" +
                "        \"price\": 12.49,\n" +
                "        \"eyebrow\": \"\",\n" +
                "        \"formattedPrice\": \"$12.49\",\n" +
                "        \"saveDollar\": 0,\n" +
                "        \"priceType\": \"Reg\",\n" +
                "        \"endDate\": 253402214400000,\n" +
                "        \"maxPrice\": 0,\n" +
                "        \"savePercent\": 0,\n" +
                "        \"minPrice\": 0\n" +
                "      },\n" +
                "      \"ppu\": \"\",\n" +
                "      \"mapPriceFlag\": \"N\",\n" +
                "      \"channelAvailability\": \"0\"\n" +
                "    },\n" +
                "    \"item\": {\n" +
                "      \"tcin\": \"13860428\",\n" +
                "      \"bundle_components\": {},\n" +
                "      \"dpci\": \"058-34-0436\",\n" +
                "      \"upc\": \"025192110306\",\n" +
                "      \"product_description\": {\n" +
                "        \"title\": \"The Big Lebowski (Blu-ray)\",\n" +
                "        \"downstream_description\": \"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\",\n" +
                "        \"bullet_description\": [\n" +
                "          \"<B>Movie Studio:</B> Universal Studios\",\n" +
                "          \"<B>Movie Genre:</B> Comedy\",\n" +
                "          \"<B>Run Time (minutes):</B> 119\",\n" +
                "          \"<B>Software Format:</B> Blu-ray\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"buy_url\": \"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428\",\n" +
                "      \"enrichment\": {\n" +
                "        \"images\": [\n" +
                "          {\n" +
                "            \"base_url\": \"https://target.scene7.com/is/image/Target/\",\n" +
                "            \"primary\": \"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\",\n" +
                "            \"content_labels\": [\n" +
                "              {\n" +
                "                \"image_url\": \"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ],\n" +
                "        \"sales_classification_nodes\": [\n" +
                "          {\n" +
                "            \"node_id\": \"hp0vg\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"node_id\": \"5xswx\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"return_method\": \"Temporary return policy: For a limited time, returns are not accepted in store. Return windows will be extended to accommodate this change. Standard return policy: This item can be returned to any Target store or Target.com.\",\n" +
                "      \"handling\": {},\n" +
                "      \"recall_compliance\": {\n" +
                "        \"is_product_recalled\": false\n" +
                "      },\n" +
                "      \"tax_category\": {\n" +
                "        \"tax_class\": \"G\",\n" +
                "        \"tax_code_id\": 99999,\n" +
                "        \"tax_code\": \"99999\"\n" +
                "      },\n" +
                "      \"display_option\": {\n" +
                "        \"is_size_chart\": false\n" +
                "      },\n" +
                "      \"fulfillment\": {\n" +
                "        \"is_po_box_prohibited\": true,\n" +
                "        \"po_box_prohibited_message\": \"We regret that this item cannot be shipped to PO Boxes.\",\n" +
                "        \"box_percent_filled_by_volume\": 0.27,\n" +
                "        \"box_percent_filled_by_weight\": 0.43,\n" +
                "        \"box_percent_filled_display\": 0.43\n" +
                "      },\n" +
                "      \"package_dimensions\": {\n" +
                "        \"weight\": \"0.18\",\n" +
                "        \"weight_unit_of_measure\": \"POUND\",\n" +
                "        \"width\": \"5.33\",\n" +
                "        \"depth\": \"6.65\",\n" +
                "        \"height\": \"0.46\",\n" +
                "        \"dimension_unit_of_measure\": \"INCH\"\n" +
                "      },\n" +
                "      \"environmental_segmentation\": {\n" +
                "        \"is_hazardous_material\": false,\n" +
                "        \"has_lead_disclosure\": false\n" +
                "      },\n" +
                "      \"manufacturer\": {},\n" +
                "      \"product_vendors\": [\n" +
                "        {\n" +
                "          \"id\": \"1984811\",\n" +
                "          \"manufacturer_style\": \"025192110306\",\n" +
                "          \"vendor_name\": \"Ingram Entertainment\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"4667999\",\n" +
                "          \"manufacturer_style\": \"61119422\",\n" +
                "          \"vendor_name\": \"UNIVERSAL HOME VIDEO\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"1979650\",\n" +
                "          \"manufacturer_style\": \"61119422\",\n" +
                "          \"vendor_name\": \"Universal Home Ent PFS\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"product_classification\": {\n" +
                "        \"product_type\": \"542\",\n" +
                "        \"product_type_name\": \"ELECTRONICS\",\n" +
                "        \"item_type_name\": \"Movies\",\n" +
                "        \"item_type\": {\n" +
                "          \"category_type\": \"Item Type: MMBV\",\n" +
                "          \"type\": 300752,\n" +
                "          \"name\": \"movies\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"product_brand\": {\n" +
                "        \"brand\": \"Universal Home Video\",\n" +
                "        \"manufacturer_brand\": \"Universal Home Video\",\n" +
                "        \"facet_id\": \"55zki\"\n" +
                "      },\n" +
                "      \"item_state\": \"READY_FOR_LAUNCH\",\n" +
                "      \"specifications\": [],\n" +
                "      \"attributes\": {\n" +
                "        \"gift_wrapable\": \"Y\",\n" +
                "        \"has_prop65\": \"N\",\n" +
                "        \"is_hazmat\": \"N\",\n" +
                "        \"manufacturing_brand\": \"Universal Home Video\",\n" +
                "        \"max_order_qty\": 10,\n" +
                "        \"street_date\": \"2011-11-15\",\n" +
                "        \"media_format\": \"Blu-ray\",\n" +
                "        \"merch_class\": \"MOVIES\",\n" +
                "        \"merch_classid\": 58,\n" +
                "        \"merch_subclass\": 34,\n" +
                "        \"return_method\": \"Temporary return policy: For a limited time, returns are not accepted in store. Return windows will be extended to accommodate this change. Standard return policy: This item can be returned to any Target store or Target.com.\",\n" +
                "        \"ship_to_restriction\": \"United States Minor Outlying Islands,American Samoa (see also separate entry under AS),Puerto Rico (see also separate entry under PR),Northern Mariana Islands,Virgin Islands, U.S.,APO/FPO,Guam (see also separate entry under GU)\"\n" +
                "      },\n" +
                "      \"country_of_origin\": \"US\",\n" +
                "      \"relationship_type_code\": \"Stand Alone\",\n" +
                "      \"subscription_eligible\": false,\n" +
                "      \"ribbons\": [],\n" +
                "      \"tags\": [],\n" +
                "      \"ship_to_restriction\": \"This item cannot be shipped to the following locations: United States Minor Outlying Islands, American Samoa, Puerto Rico, Northern Mariana Islands, Virgin Islands, U.S., APO/FPO, Guam\",\n" +
                "      \"estore_item_status_code\": \"A\",\n" +
                "      \"is_proposition_65\": false,\n" +
                "      \"return_policies\": {\n" +
                "        \"user\": \"Regular Guest\",\n" +
                "        \"policyDays\": \"30\",\n" +
                "        \"guestMessage\": \"This item must be returned within 30 days of the in-store purchase, ship date, or online order pickup. See return policy for details.\"\n" +
                "      },\n" +
                "      \"gifting_enabled\": false,\n" +
                "      \"packaging\": {\n" +
                "        \"is_retail_ticketed\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"circle_offers\": {\n" +
                "      \"universal_offer_exists\": false,\n" +
                "      \"non_universal_offer_exists\": true\n" +
                "    }\n" +
                "  }\n" +
                "}");
        result = mapper.reader().readTree("{\"id\":\"1\",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":\"10\",\"currency_code\":\"USD\"}}");
        myRetailController.setRepository(pricesRepository,restTemplate);
    }

    @Test
    void getPrice() {
        Prices prices = new Prices("1","10","USD");
        when(pricesRepository.findById(anyString())).thenReturn(Optional.of(prices));
        when(restTemplate.getForObject(anyString(),any())).thenReturn(node);
        JsonNode price = myRetailController.getPrice("1");
        assertEquals(result,price);
    }

    @Test
    void updatePrice() {
        Prices resPrice = new Prices("1","10","USD");
        when(pricesRepository.existsById(anyString())).thenReturn(true);
        when(pricesRepository.save(ArgumentMatchers.any())).thenReturn(resPrice);
        Prices prices = myRetailController.updatePrice("1", result);
        assertEquals(resPrice,prices);
    }
}