# Que se hizo para conectar la app con el backend (explicado facil)

> La idea de este documento es que cualquiera del grupo entienda QUE se toco y
> POR QUE, sin tecnicismos raros. Piensa en esto como "el diario de lo que
> cambiamos en el proyecto".

Antes que nada, la idea general en una frase:

> La app (Android) y el backend (Python) son dos programas separados. Para que
> "se hablen", la app necesita un mensajero que mande y reciba datos por
> internet. Ese mensajero se llama **Retrofit**. Casi todo lo nuevo gira
> alrededor de eso.

---

## 1. Lo NUEVO que se creo

### 1.1 La parte que habla con el backend

Imaginate que el backend es un restaurante y la app es el cliente. Para pedir
comida necesitas: saber la direccion del restaurante, un mesero, y una carta con
lo que puedes pedir. Eso es justo lo que creamos:

- **`util/Constantes.kt`** -> es **la direccion del restaurante**. Aca esta
  escrita la URL del backend (`http://10.0.2.2:8080/api/`). Si el backend se
  muda, solo se cambia aca.

- **`retrofit/ClienteRetrofit.kt`** -> es **el mesero**. Lo armamos una sola vez
  y queda listo para llevar y traer pedidos al backend.

- **`retrofit/api/IGasMapochoService.kt`** -> es **la carta**. Dice exactamente
  que se le puede pedir al backend: iniciar sesion, ver el inventario y crear un
  pedido.

- **Los "moldes" de datos** (`retrofit/response/`): cuando el backend manda o
  recibe informacion, viaja como texto (JSON). Estos archivos son los **moldes**
  que dicen "asi se ve un producto", "asi se ve un pedido", etc. Son:
  `LoginRequest`, `LoginResponse`, `Producto`, `PedidoRequest`, `PedidoResponse`.

- **`util/PedidoEnProceso.kt`** -> es como **una libreta** donde vamos anotando
  el pedido mientras el usuario avanza por las pantallas (que balon eligio, su
  nombre, su direccion, como va a pagar). Recien al final mandamos todo junto.

### 1.2 Las pantallas (Activities)

Cada pantalla de la app tiene su archivo de codigo. Estos son los que conectan
cada pantalla con el backend:

- **`view/LoginActivity.kt`** -> la pantalla de inicio de sesion. Manda el correo
  y la clave al backend para entrar.

- **`view/SeleccionarCompraActivity.kt`** -> el catalogo. Le pide al backend la
  lista de balones y los muestra.

- **`view/adapter/ProductoAdapter.kt`** -> el "ayudante" del catalogo: agarra la
  lista de balones y la pinta una por una en la pantalla (eso es un RecyclerView).

- **`view/DatosEnvioActivity.kt`** -> donde el usuario pone su nombre y direccion.

- **`view/MetodoPagoActivity.kt`** -> donde elige como pagar y, al darle
  continuar, **se manda el pedido completo al backend**.

- **`view/PedidoRealizadoActivity.kt`** -> la pantalla de "listo, tu pedido se
  hizo". Limpia la libreta y te regresa al inicio.

---

## 2. Lo que se MODIFICO (cosas que ya existian y se ajustaron)

### 2.1 Configuracion del proyecto

- **`app/build.gradle.kts`**: aca se anota que librerias usa la app. Activamos
  **View Binding** (para usar las vistas del XML sin complicarse) y agregamos
  **Retrofit** y **RecyclerView**.

- **`gradle/libs.versions.toml`**: es como la lista de versiones de las
  librerias. Agregamos RecyclerView y **bajamos la version de `core-ktx`** porque
  la que estaba (1.19.0) exigia una version de Android mas nueva de la que usa el
  proyecto. La bajamos a 1.16.0 y listo.

- **`AndroidManifest.xml`**: es como el "DNI" de la app. Aca pusimos el **permiso
  de Internet** (sin eso, la app no puede hablar con el backend) y registramos
  las pantallas, diciendo que el **Login es la primera** que se abre.

- **`gradle.properties`**: aca fijamos con que **JDK (version 21)** se compila el
  proyecto, para evitar un error que daba con otro Java mal configurado.

### 2.2 Layouts (los XML que son el "diseno" de cada pantalla)

- **`activity_login.xml`, `seleccionar_compra.xml`, `metodo_pago.xml`,
  `pedido_realizado.xml`**: les agregamos un detallito (`android:id="@+id/main"`)
  que el codigo necesita para acomodar bien la pantalla y que el contenido no
  quede tapado por la barra de arriba del celular.

- **`datos_guardados.xml`**: tenia un **error de tipeo** en una linea
  (`layout_bottomToTopOf` cuando lo correcto es `layout_constraintBottom_toTopOf`).
  Eso rompia la compilacion, asi que se corrigio.

### 2.3 Los iconos de la app

- **`ic_launcher_background.xml`, `ic_launcher.xml`, `ic_launcher_round.xml`**:
  estos tres tenian la primera linea de XML (`<?xml ... ?>`) **mal ubicada**
  (estaba en la linea 16 en vez de la 1). Esa linea TIENE que ser la primera de
  todo el archivo, sin nada antes. La movimos/quitamos y dejo de fallar.

---

## 3. Lo que se BORRO

- **`MainActivity.kt` y `DatosEnvioActivity.kt`** (los que estaban sueltos en la
  raiz del paquete): eran versiones viejas y vacias. Las reemplazamos por las
  nuevas dentro de la carpeta `view/`, asi que las viejas ya no servian.

- **`gradle/gradle-daemon-jvm.properties`**: este archivo forzaba al proyecto a
  usar cierto Java, y por culpa de eso Android Studio agarraba el Java de la
  extension de VS Code (que esta incompleto) y daba error. Al borrarlo, el
  problema desaparecio.

---

## 4. Documentos que se crearon (esto es info, no codigo)

- **`plan.md`**: el plan de como se iba a conectar la app con el backend.
- **`EXPLICACION_KOTLIN.md`**: explica que hace cada funcion del codigo.
- **`CAMBIOS.md`**: este mismo archivo.
- **`PLAN_PRECIO.md`** (en la carpeta del backend): el plan para agregarle precio
  a los productos en el backend.

---

## 5. Un par de aclaraciones para que nadie se confunda

- Los archivos `.idea/gradle.xml` y `.idea/misc.xml` aparecen como cambiados,
  pero esos **los modifica Android Studio solo** cuando guardas configuraciones.
  No son cambios que hicimos a mano ni afectan la app.

- Cuando arriba veas `.../`, la ruta completa es:
  `app/src/main/java/pe/edu/cibertec/appgasmapocho/`

---

## 6. Bonus: los lios de compilacion que aparecieron y como se arreglaron

Mientras conectabamos todo, al intentar compilar salieron varios errores. Te los
dejo resumidos por si vuelven a aparecer:

1. **"No puede crear el archivo de bloqueo / Gradle user home"**: Android Studio
   tenia mal configurada la carpeta donde Gradle guarda sus descargas (apuntaba a
   una carpeta de solo-lectura). Se arreglo poniendo
   `C:\Users\<tu_usuario>\.gradle` en Settings.

2. **Error en los iconos (`<?xml ... ?>`)**: explicado arriba, la linea de XML
   estaba mal ubicada.

3. **"requires compileSdk 37"**: una libreria pedia una version de Android mas
   nueva. Se bajo la version de la libreria.

4. **Error de `jlink`**: Android Studio agarraba un Java incompleto (el de la
   extension de VS Code). Se forzo a usar el JDK 21 bueno.

5. **Atributo XML inexistente** (`layout_bottomToTopOf`): un typo en un layout.
   Corregido.

Todos esos eran problemas del entorno o de archivos que ya venian con el
proyecto, **no de la conexion en si**. Una vez resueltos, la app compilo bien
("BUILD SUCCESSFUL") y se puede correr en el emulador.

---

## 7. Los comandos que se usaron (y para que sirve cada uno)

Aca van los comandos que aparecen en el proyecto, explicados como si se los
contaras a un compañero. Un "comando" es una orden que escribes en la terminal
para que la maquina haga algo.

### 7.1 Para prender el backend (Python)

```bash
cd C:\Users\GamingWorld\Desktop\app_cibertec\backend
uvicorn main:app --reload --port 8080
```

- **`cd ...`** = "change directory", o sea **meterte en la carpeta** del backend.
  Es como hacer doble clic para entrar a una carpeta, pero escrito.
- **`uvicorn`** = el programa que **prende el servidor** del backend (FastAPI por
  si solo no se ejecuta, necesita a uvicorn que lo "levante").
- **`main:app`** = "en el archivo `main.py`, usa la variable `app`". Le dice a
  uvicorn DONDE esta la aplicacion.
- **`--reload`** = que se **reinicie solo** cada vez que cambias el codigo del
  backend (comodo mientras programas).
- **`--port 8080`** = que escuche en el **puerto 8080**. Un puerto es como el
  "numero de oficina" dentro de tu PC. La app busca al backend justo en el 8080,
  por eso tiene que ser ese.

> Cuando esto esta corriendo, NO cierres esa terminal: si la cierras, el backend
> se apaga y la app dejara de conectarse.

### 7.2 Para compilar / correr la app (Android)

La forma normal es con los botones de Android Studio, pero por debajo usa estos:

```bash
gradlew.bat assembleDebug      (en Windows)
./gradlew assembleDebug        (en Linux/Mac)
```

- **`gradlew`** = el "Gradle Wrapper". Gradle es el que **arma la app** (junta el
  codigo, las librerias y los recursos y genera el APK). El "wrapper" es un
  ayudante que usa la version exacta de Gradle que el proyecto necesita, sin que
  tengas que instalarla a mano.
- **`assembleDebug`** = la **tarea** que le pides: "construye la version de prueba
  (debug) de la app".

En Android Studio esto se traduce a:
- **Sync** (el elefante 🐘): "lee de nuevo la configuracion del proyecto". Se usa
  cada vez que cambias `build.gradle.kts`, `libs.versions.toml` o
  `gradle.properties`.
- **Build -> Make Project**: solo **compila** (revisa que no haya errores).
- **Run ▶️** (o Shift+F10): **compila + instala + abre** la app en el emulador.

### 7.3 Comandos de Git (para guardar y subir los cambios)

```bash
git status                 # ver que archivos cambiaron
git add .                  # marcar TODOS los cambios para guardar
git commit -m "mensaje"    # guardar una "foto" de los cambios con un mensaje
git push                   # subir esa foto a GitHub
```

- **`git status`** = te muestra **que tocaste** (modificado, nuevo o borrado).
- **`git add .`** = el punto significa "todo". Prepara todos los cambios para el
  siguiente commit. (Tambien puedes poner un archivo especifico en vez del punto.)
- **`git commit -m "..."`** = guarda un **punto de control** en la historia del
  proyecto, con un mensaje que explica que hiciste.
- **`git push`** = manda esos commits a **GitHub** para que queden en la nube y
  los vea el resto del grupo.

### 7.4 Comandos que se usaron para borrar archivos

Cuando se eliminaron las pantallas viejas y el archivo que daba problemas, se uso:

```bash
rm archivo.kt
```

- **`rm`** = "remove", **borrar** un archivo. (Se uso para quitar las Activities
  viejas y el `gradle-daemon-jvm.properties`.) En Windows con explorador seria
  simplemente mandar el archivo a la papelera; `rm` hace lo mismo pero por
  terminal.

---

### Mini-resumen para correr todo de cero

1. Prende el backend: `uvicorn main:app --reload --port 8080` (dejalo abierto).
2. Abre el proyecto en Android Studio y dale **Sync** si te lo pide.
3. Elige el **emulador** (Pixel 6) y dale **Run ▶️**.
4. Entra con `admin@gasmapocho.cl` / `admin123` y prueba el flujo.
