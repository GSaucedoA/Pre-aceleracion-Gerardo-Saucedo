package dev.cynomys.movieapp.model

data class Genre(val id: Int, val name: String) {
    override fun toString(): String {
        return this.name
    }
}
