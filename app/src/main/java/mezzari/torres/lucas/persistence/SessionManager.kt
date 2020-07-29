package mezzari.torres.lucas.persistence

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
object SessionManager {
    private var _baseUrl: String? = null
    var baseUrl: String get() {
        if (_baseUrl == null) {
            this._baseUrl = "https://viacep.com.br/ws/"
        }

        return _baseUrl!!
    } set(value) {
        this._baseUrl = value
    }
}