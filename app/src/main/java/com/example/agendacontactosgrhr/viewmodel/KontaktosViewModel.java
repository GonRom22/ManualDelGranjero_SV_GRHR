package com.example.agendacontactosgrhr.viewmodel;


import static java.util.Collections.emptyList;

import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity;
import com.example.agendacontactosgrhr.data.repository.ContactoRepository;

import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;
import jakarta.inject.Inject;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;

@HiltViewModel
public class KontaktosViewModel @Inject constructor (
        private val repository: ContactoRepository
): ViewModel(){
    //Estado de la lista
    private val _contactos = MutableStateFlow<List< ContactoEntity>>(emptyList())
            val contactos:StateFlow<List<ContactoEntity>> = _contactos

    //Contacto seleccionado
    private val _contactoSeleccionado = MutableStateFlow<ContactoEntity>(null)
            val contactoSeleccionado: StateFlow<ContactoEntity?> = _contactoSelecionado

    //Eventos
    private val _eventoUI = MutableSharedFlow<String> = eventoUI

//Bloque init: El que se ejecuta al abrir la ventana:
    init {
        viewModelScope.launch {
            //Se inicia la recolección de datos en tiempo real
    launch {
        repository.obtenerTodosContactos().collect {
            _contactos.value = it
                }
            }

            //Lógica de carga:
            val listaAcutal = repository.obtenerTodosContactos().first()
                    if (listaActual.isEmpty()){
                        log.d("StardewApp","Base de datos vacía. Insertando personajes")
                                val listaStardew = obtenerSolterosStardew()
                                        listaStardew = obtenerSolterosStardew()
                                                listaStardew.forEach { npc ->
                            repository.insertarContacto(npc)
                    }


        }

                    //>>>>>CRUD<<<<<
    }
}