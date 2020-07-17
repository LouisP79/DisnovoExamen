package com.disnovoexamen.data.form.request

import com.fasterxml.jackson.annotation.JsonProperty

class ElementRequest(@field:JsonProperty var element: String,
                     @field:JsonProperty var value: Any)