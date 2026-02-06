package com.example.agendacontactosgrhr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel().
//ViewModel que gestiona la lista de contactos usando StateFLow
@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val repositorio: ContactoRepository//RepositorioContactos
): ViewModel() {

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
        viewModelScope.launch{
            // Obtenemos los contactos existentes en la DB
            val existentes = repositorio.obtenerTodosContactos().first() // first() toma el primer valor emitido

            if (existentes.isEmpty()) {
                // Si no hay contactos, insertamos los por defecto
                val defaultContacts = listOf(
                    ContactoEntity(
                        title = "Don", name = "Gonzalo Romero", lastName = "Romero", phone = "+34888888888",
                        email = "carnedepiedra@gmail.com", city = "Leganés", country = "España", thumbnail = "imagencita"
                    )
                )
                defaultContacts.forEach { repositorio.insertarContacto(it) }
            }
            repositorio.obtenerTodosContactos().collect {
                _contactos.value = it
            }
        }

        /*
        //cargarContactos()
        viewModelScope.launch {
            repositorio.obtenerTodosContactos().collect(){
                _contactos.value = it
            }
        }*/
    }

    fun insertarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.insertarContacto(contacto)
    }
    fun actualizarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.actualizarContacto(contacto)
    }
    fun eliminarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.eliminarContacto(contacto)
    }
    fun obtenerContactoPorId(id:Int) = viewModelScope.launch {
        _contactoSeleccionado.value = repositorio.obtenerContactoPorId(id)
    }


    /*
    //Función que simula la carga de productos de una API
    private fun cargarContactos() {
        //viewModelScope.launch es la forma de lanzar corrutinas (hilo en segundo plano) desde un ViewModel ligadas al ciclo de vida del ViewModel.
        //Todas las corrutinas lanzada dentro de este scope serán canceladas cuando el ViewModel sea destruido.
        //Aquí estamos simulando traer la información de la API y guardarla en la base de datos local
        viewModelScope.launch {
            val contactos = repositorio.obtenerContactos()
            _contactos.value = contactos
        }
   }*/

    //Aquí la función que se llama cuando el usuario pulse un contacto, lanza un CORRUTINA
    //NO cambia el estado, sino que se emite un evento
    fun onContactoSeleccionado(name: String){
        viewModelScope.launch{
            //emit es el metodito de SharedFlow que emite un valor al flujo.
            // Es una función suspendida, por ello podemos incluirla dentro de la corrutina
            // viewModelScope.launch.
            _eventoUI.emit("Detalles de $name cargados")
        }
    }

    //Carga de contacto por API
    fun loadContactoAPI() {
        viewModelScope.launch {
            try {
                //Obtenemos el contacto de la API
                val newContacto = repositorio.getNewContact()
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
                }
        }

    }
}