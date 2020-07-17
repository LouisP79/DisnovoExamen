package com.disnovoexamen.data.form.request

import com.fasterxml.jackson.annotation.JsonProperty

class FormRequest(@field:JsonProperty var elements: List<ElementRequest> = listOf(),
                  @field:JsonProperty var id: Long = 0){

    fun getJson(): String {
        var json = "{\n" +
                "  \"elements\": [\n"

        for ((index, element) in elements.withIndex()) {
            json = "$json ${element.getJson()}"
            json = if(index == elements.size -1)
                "$json        }\n"
            else
                "$json        },\n"
        }

        json = "$json  ],\n" +
            "  \"id\": $id\n" +
            "}"

        return json
    }
}