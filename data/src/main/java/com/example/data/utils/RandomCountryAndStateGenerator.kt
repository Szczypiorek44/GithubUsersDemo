package com.example.data.utils


class RandomCountryAndStateGenerator {

    private val countriesAndStates = listOf(
        CountryAndState("United States", "California"),
        CountryAndState("United States", "Florida"),
        CountryAndState("United States", "Texas"),
        CountryAndState("United States", "Ohio"),
        CountryAndState("Argentina", "Cordoba"),
        CountryAndState("El Salvador", "Morazán"),
        CountryAndState("Poland", "Mazowieckie"),
        CountryAndState("Totonicapán", "Guatemala"),
        CountryAndState("Honduras", "Atlántida"),
        CountryAndState("Colombia", "Cundinamarca"),
        CountryAndState("Honduras", "Lempira"),
        CountryAndState("Colombia", "Putumayo"),
        CountryAndState("Costa Rica", "Heredia"),
        CountryAndState("Colombia", "Antioquia")
    )

    fun getRandomCountryAndState(): CountryAndState {
        return countriesAndStates.random()
    }

    data class CountryAndState(
        val countryName: String,
        val stateName: String
    )
}