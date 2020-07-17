package com.disnovoexamen.data.form.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DataResponse {

    @JsonProperty
    var elements = listOf<ElementResponse>()

    @JsonProperty
    var id: Long = 0

    fun getJson(): String {
        var json =  "[\n"

        for ((index, element) in elements.withIndex()) {
            json = "$json ${element.getJson()}"
            json = if(index == elements.size -1)
                "$json    }\n"
            else
                "$json    },\n"
        }

        json = "$json]"
        return json
    }
}