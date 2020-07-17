package com.disnovoexamen.data.form.request

import com.fasterxml.jackson.annotation.JsonProperty

class FormRequest(@field:JsonProperty var elements: List<ElementRequest>,
                  @field:JsonProperty var id: Long)