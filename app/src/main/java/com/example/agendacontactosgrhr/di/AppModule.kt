package com.example.agendacontactosgrhr.di

import android.app.Application
import androidx.room.Room
import com.example.agendacontactosgrhr.data.RepositorioContactos
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.database.ContactoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**@Module es una anotación que indica que esta clase es un módulo de Hilt.
 * En la clase vamos a colocar funciones anotadas con @Provides que enseñan
 * a Hilt como crear instancias de dependencias. Es decir, estamos indicando
 * a Hilt cómo crear objetos con las funciones con @Provides.
 *
 * Esto se necesita porque hay casos donde Hilt no puede crear una instancia
 * automáticamente, como por ejemplo:
 * - clases de librerias externas como Retrofit, Room o Firebase
 * - Clases que no tienen un inyector con @Inject
 * - Clases que necesitan argumentos personalizados que Hilt no conoce
 *
 * @InstallIn al que pasamos un SingletonComponent:
 * - @InstallIn indica en qué nivel del ciclo de vida estará disponible esa dependencia
 * - SingletonComponent vive mientras esté activa la aplicación.
 * - Hilt creará una instancia  * solo una vez y la reutilizará durante toda la aplicación.
 * SingletonComponent es el contenedor global o grafo de dependencias que vive durante toda la
 * vida de la aplicación.
 * Todo lo que se registre aquí estará disponible desde que la app se inicia hasta que se cierra.
 * Es ideal para objetos que deben mantenerse como una única instancia como retrofit, repositorios,
 * etc.... Así evitamos recrearlos innecesariamente y ahorramos recursos.
 *
 * object: lo tenemos porque no necesitamos crear una instancia manualmente. Hilt se encarga de
 * registrarlo automáticamente gracias a la anotación @InstallIn. Sin @InstallIn, tendríamos
 * que hacer val modulo = AppModulo() (Crear una instancia manualmente).
 *
 * - Cuando Hilt detecta que alguien, como por ejemplo un Viewodel necesita una instancia,
 *  busca en el grafo de dependencias que función con la anotación @Provides puede entregarle.
 *
 * - @Provides: le decimos a Hilt que instancia debe usar, en este caso RepositorioSimulado es la
 * función encargada de construir y proporcionar esta dependencia. Es como decir: "Oye Hilt, aquí
 * tienes la instancia lista. Úsala cuando alguien la necesite."
 *
 * - @Singleton: es la anotación que le dice a Hilt que la instancia debe ser única dentro del
 * contenedor donde ha sido registrada, SingletonComponent. Hilt mantendrá esta única
 * instancia, en este caso RepositorioSimulado durante toda la vida de la aplicación.
 *
 * Es útil cuando queremos que esta clase funcione como un singleton real que se caracterizan por
 * ser reutilizables.
 *
 * - Con return estamos creando manualmente la instancia.
 *
 * Un dato importante: si usamos @Module junto con @Provides, Hilt utilizará lo que retorne esa
 * función como instancia a inyectar.
 *
 * La diferencia entre SingletonComponent y @Singleton es que SingletonComponent es el contenedor
 * global o grafo de dependencias que vive durante toda la vida de la aplicación (por ejemplo, el
 * frigorífico de tu casa, todo lo que guardes estará disponible todo el tiempo que la casa esté
 * abierta.). @Singleton, sin embargo, representa el hecho de guardar una única instancia (por
 * ejemplo: una botella de agua en ese frigorífico.Cada vez que alguien tenga sed, usa esa botella
 * y no se crean más botellas). Si pones un @Singleton sin SingletonComponent, no funciona bien porque
 * Hilt no sabe cuanto debe de vivir ese Singleton. Y si se pone @InstallIn(SingletonComponent::class)
 * pero no colocas @Singleton, la instancia puede ser creada varias veces dependiendo de como
 * Hilt lo gestione.
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): ContactoDataBase {
        return Room.databaseBuilder(
            app,
            ContactoDataBase::class.java,
            "contacto_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: ContactoDataBase): ContactoDao = db.contactoDao()
    /*
    fun provideRepositorioContactos(): RepositorioContactos {
        return RepositorioContactos()
    }*/
}