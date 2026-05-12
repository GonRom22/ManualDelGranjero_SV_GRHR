package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import com.example.agendacontactosgrhr.data.remote.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculatorRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun calcularCultivos(request: CalculoCultivosRequest): Result<CalculoCultivosResponse> {
        return try {
            val response = apiService.calcularCultivos(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun calcularEdificios(request: CalculoEdificiosRequest): Result<CalculoEdificiosResponse> {
        return try {
            val response = apiService.calcularEdificios(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
