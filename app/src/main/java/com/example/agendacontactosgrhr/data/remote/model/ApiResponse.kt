package com.example.agendacontactosgrhr.data.remote.model

data class ApiResponse (
    val results: List<Results> = emptyList()
)

data class Results(
    val name: ContactName,
    val contact: ContactContact,
    val location: ContactLocation,
    val picture: ContactPicture
)