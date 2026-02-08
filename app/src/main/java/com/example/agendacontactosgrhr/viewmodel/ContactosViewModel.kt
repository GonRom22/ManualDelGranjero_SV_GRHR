package com.example.agendacontactosgrhr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.network.NetworkMonitor
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel().
//ViewModel que gestiona la lista de contactos usando StateFLow
@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val repositorio: ContactoRepository,//RepositorioContactos

    private val networkMonitor: NetworkMonitor // *******Inyección del montitor de red

): ViewModel() {


    val isOnline: StateFlow<Boolean> = networkMonitor.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)



    //STATE (Estado Persistente)
    //----------------------------
    //MutableStateFLow es un observable que mantiene un estado reactivo y mutable
    //Se declara como privado para seguir el principio de encapsulamiento
    private val _contactos = MutableStateFlow<List<ContactoEntity>>(emptyList())

    //Exponemos el flujo como StateFlow (solo lectura para la UI)
    val contactos: StateFlow<List<ContactoEntity>> = _contactos

    private val _contactoSeleccionado = MutableStateFlow<ContactoEntity?>(null)
    val contactoSeleccionado: StateFlow<ContactoEntity?> = _contactoSeleccionado

    //EVENTS (Eventos Unidireccionales)
    //----------------------------------
    //Creamos un objeto de tipo MutableSharedFlow<String> que emite Strings (en este caso)
    // _eventoUI es el flujo mutable y privado que guarda la dirección de este objeto.
    //MutableSharedFlow no guarda el valor emitido con lo que no es recolectado si la UI no está escuchando
    private val _eventoUI = MutableSharedFlow<String>()

    //SharedFlow público para que la UI escuche los eventos
    val eventoUI: SharedFlow<String> = _eventoUI


    //init se ejecuta cuando se crea una instancia de la clase
    //En este caso, cuando se inicialice la clase ContactoViewModel, se llamará a la función cargarContactos()
    //Simula la carga de datos desde una API o base de datos
    init {
        viewModelScope.launch {
            // Obtenemos los contactos existentes en la DB
            val existentes =
                repositorio.obtenerTodosContactos().first() // first() toma el primer valor emitido

            if (existentes.isEmpty()) {
                // Si no hay contactos, insertamos los por defecto
                val defaultContacts = listOf(
                    ContactoEntity(
                        name = "Gonzalo", estacion = "Invierno", cumpleanos = 10, thumbnail = "gonzalo.png"
                    )
                )
                defaultContacts.forEach { repositorio.insertarContacto(it) }
            }
            //Escuchar cambios den la base de datos
            repositorio.obtenerTodosContactos().collect {
                _contactos.value = it
            }
        }
    }

    //CRUD básico
    fun insertarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.insertarContacto(contacto)
    }

    fun actualizarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.actualizarContacto(contacto)
    }

    fun eliminarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.eliminarContacto(contacto)
    }

    fun obtenerContactoPorId(id: Int) = viewModelScope.launch {
        _contactoSeleccionado.value = repositorio.obtenerContactoPorId(id)
    }

    //Aquí la función que se llama cuando el usuario pulse un contacto, lanza un CORRUTINA
    //NO cambia el estado, sino que se emite un evento
    fun onContactoSeleccionado(name: String) =
        viewModelScope.launch {
            //emit es el metodito de SharedFlow que emite un valor al flujo.
            // Es una función suspendida, por ello podemos incluirla dentro de la corrutina
            // viewModelScope.launch.
            _eventoUI.emit("Detalles de $name cargados")
        }


    //Carga de contacto por API
    fun importarStardew() {
        viewModelScope.launch {
            try {
                val nuevoNpc = repositorio.importarUnStardew()
                if (nuevoNpc != null) {
                    Log.d("ContactosViewModel", "NPC insertado: ${nuevoNpc.name}")
                } else {
                    Log.d("ContactosViewModel", "Todos los NPCs ya están en la BBDD")
                }
            } catch (e: Exception) {
                Log.e("ContactosViewModel", "Error al importar NPC: $e")
            }
        }
    }
}

            /*
            try {
                //Obtenemos el contacto de la API
                val newContacto = repositorio.importarStardew()
                //Vemos su log
                Log.d("ContactoViewModel", newContacto.toString())
                //Lo insertamos en la BBDD
                insertarContacto(newContacto)
            } catch (e: Exception) {
                // Si algo falla, el estado sigue siendo el anterior
                // o podrías ponerlo a null de nuevo
                Log.e("ContactoViewModel", "Error al cargar el contacto: $e")}





                //lista de personajes oficiales introducidos de forma manueal:
                fun obtenerSolterosStardew(): List<ContactoEntity> {
                    val imgUrl = "https://raw.githubusercontent.com/GonRom22/AgendaContactosGRHR/master/imagenes/"

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
                            thumbnail = "${imgUrl}eliot.png",
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
                            name = "Emily",
                            thumbnail = "${imgUrl}emily.png",
                            estacion = "primavera",
                            cumpleanos = 27,
                            regalosAmados = "Amatista, Aguamarina",
                            regalosOdiados = "Sashimi",
                            esSoltero = true
                        )
                    )
                }*/
