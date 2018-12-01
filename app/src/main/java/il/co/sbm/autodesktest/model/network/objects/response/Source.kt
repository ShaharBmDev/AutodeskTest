package il.co.sbm.autodesktest.model.network.objects.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Source(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("name")
    val name: String?
)