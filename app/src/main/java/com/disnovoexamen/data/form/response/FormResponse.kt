package com.disnovoexamen.data.form.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class FormResponse {

    @JsonProperty
    var code: String = ""

    @JsonProperty
    var message: String = ""

    @JsonProperty
    var data: DataResponse? = DataResponse()

}