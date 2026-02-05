package com.example.agendacontactosgrhr.viewmodel;


import static java.util.Collections.emptyList;

import android.util.Log;

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
    fun insertarContacto(contacto: ContactoEntity) = viewModelScope.launch {
                        repository.insertarContacto(contacto)
            }
    fun actualizarContacto(contacto: ContactoEntity) = viewModelScope.launch {
                repository.actualizarContacto(contacto)
            }
    fun eliminarContacto(contacto: ContactoEntity) = viewModelScope.launch {
                repository.eliminarContacto(contacto)
            }
    fun obtenerContacto(contacto: ContactoEntity) = viewModelScope.launch {
                repository.obtenerContacto(contacto)
            }
    fun onContactoSeleccionado(name:String) {
                        viewModelScope.launch {
                            _eventoUI.emit("El contacto $name ha sido cargado")
                }
            }

           //Lógica para la APi
        //LLamamos la función desde el botón +

    fun cargarContactoAPI() { //loadContactoAPI
            viewModelScope.launch {
                try {
                    repository.getNewContact()
                    Log.d( "Contacto aleatorio solicitado") //Aún no hemos podido crear y vincular la api, lo haremos posteriormente
                } catch (e: Exception) {
                    Log.e("Error al cargar")
                    }
                }
                    }

                fun cargarContactoAPi() = getNewContact()

            //lista de personajes oficiales introducidos de forma manueal:
            private fun obtenerSolterosStardew(); List<ContactoEntity> {
                val ImgUrl = "https://raw.githubusercontent.com/GonRom22/AgendaContactosGRHR/main/assets/"

                return listOf(
                        ContactoEntity(
                                name = "Abigail",
                                thumbnail = "${imgUrl}abigail.png",
                                estacion = "otoño",
                                cumpleanos = 13,
                                regalosAmados = "Amatista, tarta de calabaza",
                                regalosOdiados = "Arcilla",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                name = "Alex",
                                thumbnail = "${imgUrl}alex.png",
                                estacion = "verano",
                                cumpleanos = 13,
                                regalosAmados = "Desayuno completo",
                                regalosOdiados = "Cuarzo",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                name = "Elliot",
                                thumbnail = "${imgUrl}Elliot.png",
                                estacion = "otoño",
                                cumpleanos = 5,
                                regalosAmados = "Pastel de cangrejo",
                                regalosOdiados = "Amaranto",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                name = "Harvey",
                                thumbnail = "${imgUrl}harvey.png",
                                estacion = "otoño",
                                cumpleanos = 13,
                                regalosAmados = "café, vino",
                                regalosOdiados = "Coral",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                        name = "Leah",
                                        thumbnail = "${imgUrl}leah.png",
                                        estacion = "invierno",
                                        cumpleanos = 23,
                                        regalosAmados = "Ensalada, queso de cabra",
                                        regalosOdiados = "pan, pizza",
                                        esSoltero = true
                                ),
                                ContactoEntity(
                                        name = "Maru",
                                        thumbnail = "${imgUrl}maru.png",
                                        estacion = "verano",
                                        cumpleanos = 10,
                                        regalosAmados = "bateria, diamante",
                                        regalosOdiados = "Miel",
                                        esSoltero = true
                                ),
                                ContactoEntity(
                                        name = "Penny",
                                        thumbnail = "${imgUrl}penny.png",
                                        estacion = "otoño",
                                        cumpleanos = 2,
                                        regalosAmados = "Esmeralda, amapola",
                                        regalosOdiados = "Cerveza",
                                        esSoltero = true
                                ),
                        ContactoEntity(
                                name = "Sam",
                                thumbnail = "${imgUrl}sam.png",
                                estacion = "invierno",
                                cumpleanos = 10,
                                regalosAmados = "Lágrima helada, sashimi",
                                regalosOdiados = "Tortilla",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                name = "Shane",
                                thumbnail = "${imgUrl}shane.png",
                                estacion = "primavera",
                                cumpleanos = 20,
                                regalosAmados = "Cerveza, pizza",
                                regalosOdiados = "Pepinillos",
                                esSoltero = true
                        ),
                        ContactoEntity(
                                name = "Penny",
                                thumbnail = "${imgUrl}penny.png",
                                estacion = "otoño",
                                cumpleanos = 2,
                                regalosAmados = "Esmeralda, amapola",
                                regalosOdiados = "Cerveza",
                                esSoltero = true
                        ),













                        ),




                )


            }









    }
}