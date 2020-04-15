package com.target.myretail.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String name;
    private String id;
    private String value;
    private String currencyCode;
    private JsonNode item;
    private String[] symbols = new String[]{"$", "CA$", "€", "AED", "Af", "ALL", "AMD",
            "AR$", "AU$", "man.", "KM", "Tk", "BGN", "BD", "FBu", "BN$", "Bs", "R$",
            "BWP", "Br", "BZ$", "CDF", "CHF", "CL$", "CN¥", "CO$", "₡", "CV$", "Kč",
            "Fdj", "Dkr", "RD$", "DA", "Ekr", "EGP", "Nfk", "Br", "£", "GEL", "GH₵",
            "FG", "GTQ", "HK$", "HNL", "kn", "Ft", "Rp", "₪", "Rs", "IQD", "IRR", "Ikr",
            "J$", "JD", "¥", "Ksh", "KHR", "CF", "₩", "KD", "KZT", "LB£", "SLRs", "Lt",
            "Ls", "LD", "MAD", "MDL", "MGA", "MKD", "MMK", "MOP$", "MURs", "MX$", "RM",
            "MTn", "N$", "₦", "C$", "Nkr", "NPRs", "NZ$", "OMR", "B/.", "S/.", "₱", "PKRs",
            "zł", "₲", "QR", "RON", "din.", "RUB", "RWF", "SR", "SDG", "Skr", "S$", "Ssh", "SY£",
            "฿", "DT", "T$", "TL", "TT$", "NT$", "TSh", "₴", "USh", "$U", "UZS", "Bs.F.", "₫",
            "FCFA", "CFA", "YR", "R", "ZK", "ZWL$"};
    private String[] codes = new String[]{"USD", "CAD", "EUR", "AED", "AFN", "ALL", "AMD", "ARS",
            "AUD", "AZN", "BAM", "BDT", "BGN", "BHD", "BIF", "BND", "BOB", "BRL", "BWP", "BYN",
            "BZD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CVE", "CZK", "DJF", "DKK", "DOP",
            "DZD", "EEK", "EGP", "ERN", "ETB", "GBP", "GEL", "GHS", "GNF", "GTQ", "HKD", "HNL",
            "HRK", "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES",
            "KHR", "KMF", "KRW", "KWD", "KZT", "LBP", "LKR", "LTL", "LVL", "LYD", "MAD", "MDL",
            "MGA", "MKD", "MMK", "MOP", "MUR", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK",
            "NPR", "NZD", "OMR", "PAB", "PEN", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD",
            "RUB", "RWF", "SAR", "SDG", "SEK", "SGD", "SOS", "SYP", "THB", "TND", "TOP", "TRY",
            "TTD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "VEF", "VND", "XAF", "XOF", "YER",
            "ZAR", "ZMK", "ZWL"
    };
    HashMap<String,String> symbolsToCodes;
    ObjectMapper mapper;

    public Product(String id){
        this.mapper = new ObjectMapper();
        this.id = id;
        this.symbolsToCodes = new HashMap<>();
        int i = 0;
        while(i<symbols.length){
            symbolsToCodes.put(symbols[i],codes[i]);
            i++;
        }
    }

    public void parseProduct(JsonNode node){
        this.item = node;
        if(value == null){
            String price = node.get("product").get("price").get("offerPrice").get("formattedPrice").asText();
            String currency = price.substring(0,1);
            this.value = price.substring(1);
            this.currencyCode = symbolsToCodes.get(currency);
        }
        this.name = node.get("product").get("item").get("product_description").get("title").asText();
    }

    public JsonNode getJson() {
        JsonNode price = mapper.createObjectNode().put("value",value).put("currency_code",currencyCode);
        return mapper.createObjectNode().put("id",id).put("name",name).set("current_price",price);
    }

    public void setPrices(Prices prices){
        this.value = prices.getValue();
        this.currencyCode = prices.getCurrencyCode();
    }
}
