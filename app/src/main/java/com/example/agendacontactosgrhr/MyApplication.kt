package com.example.agendacontactosgrhr

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**La clase MyApplication hereda de la clase "Application" que se ejecuta antes
que cualquier otra clase o componente de la aplicación. Es ideal para inicializar configuraciones
globales, como por ejemplo:
 * inicialización de bibliotecas como Hilt o Firebase
 * configuración de Room
 * Declaración de variables globales

Agregamos la anotación @HiltAndroidApp a la clase MyApplication. Esta anotación le indica a Hilt que
esa clase es la clase principal de la aplicación y que, por tanto, debe inicializar la inyección de
dependencias desde el arranque. Es, en esencia, como decirle a Hilt: “esta app va a usar inyección
de dependencias, prepárate desde el inicio”.

Nota sobre las anotaciones: una anotación aporta instrucciones especiales al compilador o a
bibliotecas externas. No ejecuta código por sí sola, pero indica cómo debe comportarse una clase,
función o propiedad.

En Android Studio, el código fuente se compila a bytecode para ejecutarse en la JVM (Java Virtual
Machine), lo que permite que funcione en cualquier dispositivo Android. Ahora bien, la JVM no
“entiende” el significado de @HiltAndroidApp: únicamente lo detecta.

Quien realmente le da sentido es Hilt, ya que incluye un procesador de anotaciones que, durante la
compilación, genera automáticamente el código necesario para que la inyección de dependencias
funcione correctamente.

En concreto, @HiltAndroidApp le indica a Hilt que debe crear un contenedor global de dependencias
asociado a la aplicación, y que permanecerá activo mientras la app esté en ejecución.

A partir de ese contenedor global, Hilt puede inyectar dependencias en elementos como Activities,
Fragments o ViewModels.

Por eso, esta anotación es fundamental: si no se añade @HiltAndroidApp, Hilt no podrá crear el
contenedor principal y no se inyectará ninguna dependencia.
 */

@HiltAndroidApp
class MyApplication : Application()