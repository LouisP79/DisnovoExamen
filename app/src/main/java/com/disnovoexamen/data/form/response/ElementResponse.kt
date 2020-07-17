package com.disnovoexamen.data.form.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ElementResponse {

    @JsonProperty("component_type")
    var componentType: String = ""

    @JsonProperty("field_name_key")
    var fieldNameKey: String = ""

    @JsonProperty("field_name_view")
    var fieldNameView: String = ""

    @JsonProperty
    var ordinal: Int = 0

    @JsonProperty
    var max: Int = 0

    @JsonProperty
    var min: Int = 0

    @JsonProperty("value")
    var valueElement: Any? = null

    fun getJson(): String {
        var json =  "  {\n" +
                "      \"component_type\": \"$componentType\",\n" +
                "      \"field_name_key\": \"$fieldNameKey\",\n" +
                "      \"field_name_view\": \"$fieldNameView\",\n"

        if(max!=0) json= "$json      \"max\": $max\n"
        if(min!=0) json= "$json      \"min\": $min\n"
        json= "$json      \"ordinal\": $ordinal\n"
        if(valueElement!=null) json= "$json      \"value\": $valueElement\n"

        return json
    }

}