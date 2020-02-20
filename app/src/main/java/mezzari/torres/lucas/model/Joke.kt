package mezzari.torres.lucas.model

import com.google.gson.annotations.SerializedName

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class Joke (
    @SerializedName("id") val id: Long,
    @SerializedName("type") val type: String,
    @SerializedName("setup") val setup: String,
    @SerializedName("punchline") val punchline: String
)