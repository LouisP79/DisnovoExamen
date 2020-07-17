package com.disnovoexamen.data.form.request

import com.fasterxml.jackson.annotation.JsonProperty

class ElementRequest(@field:JsonProperty var element: String,
                     @field:JsonProperty var value: Any){

    fun getJson(): String {
        var json = "      {\n" +
                "          \"element\": \"$element\",\n"

        json = when(value){
            is String -> "$json          \"value\": \"$value\"\n"
            else -> "$json          \"value\": $value\n"
        }

        return json
    }

}