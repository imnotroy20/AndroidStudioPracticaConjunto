package com.example.conjunto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.shape.CircleShape //forma para el circulo
import androidx.compose.foundation.shape.RoundedCornerShape //forma con esquinas redondeadas
import androidx.compose.material.icons.Icons //catalogo principal de iconos
import androidx.compose.material.icons.automirrored.filled.ArrowBack //icono de flecha regresar
import androidx.compose.material.icons.automirrored.filled.ArrowForward //icono de flecha ir adelante
import androidx.compose.material.icons.automirrored.filled.ExitToApp //icono para cerrar sesión
import androidx.compose.material.icons.automirrored.filled.List //icono de catalogo o lalista
import androidx.compose.material.icons.filled.Add //icono de suma para agregar
import androidx.compose.material.icons.filled.Check //icono de palomita de confirmación
import androidx.compose.material.icons.filled.Clear //icono x para limpiar la busqueda
import androidx.compose.material.icons.filled.Delete //icono de bote de basura para eliminar
import androidx.compose.material.icons.filled.Edit //icono de lápiz para editar
import androidx.compose.material.icons.filled.Lock //icono de candado
import androidx.compose.material.icons.filled.Menu //icono de menú
import androidx.compose.material.icons.filled.Person //icono de usuario opersona
import androidx.compose.material.icons.filled.Search //icono de lupa para buscar
import androidx.compose.material.icons.filled.ShoppingCart //icono de carrito de lAs compras
import androidx.compose.material3.AlertDialog //diálogo de alerta / edición
import androidx.compose.material3.Button //botn principal estandarizado
import androidx.compose.material3.ButtonDefaults // es el q nos permite personalizar colores y estilos del boton
import androidx.compose.material3.CardDefaults //permite personalizar elevaciones y los colores de tarjetas
import androidx.compose.material3.CenterAlignedTopAppBar //barra de titulo de arriba centrada
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard //la tarjeta con sombra y elevacion 3D
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon //componente para dibujar iconos
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.IconButton //boton interactivo contenedor de los iconos
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton // Botón con borde secundario
import androidx.compose.material3.OutlinedCard //tarjeta delineada con elborde suave
import androidx.compose.material3.OutlinedTextField //caampo de la entrada de texto con borde
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation //ocultalos caracteres de contraseña •••
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController //el controlador que controla la navegación de la app
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.example.conjunto.ui.theme.ConjuntoTheme

import android.content.Context
import java.io.IOException
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import com.google.gson.annotations.SerializedName

//Pregunta de DAM
// Pregunta 2 DAM

val fuenteUnageo = FontFamily(Font(R.font.unageo_bold))//font
val fuenteAlienTrial = FontFamily(Font(R.font.alientrial))//font

val fuenteAccid = FontFamily(Font(R.font.accid))//font
val fuenteMaxi = FontFamily(Font(R.font.maxi))//font


// data class para representar el producto q es la compartida entre pantallas
data class Producto(
    val nombreProducto: String,
    val precio: Double,
    val categoria: String,
    val proveedor: String = "",
    val idProducto: Int = 0,
    val idProveedor: Int = 0
)

// data class para representar el empleado
data class Empleado(
    val nombre: String,
    val puesto: String,
    val sueldo: Double,
    val sucursal: String = "",
    val idEmpleado: Int = 0,
    val idSucursal: Int = 0
)

// data class para representar sucursales y proveedores
data class Sucursal(
    val nombreSucursal: String,
    val direccion: String,
    val telefono: String,
    val idSucursal: Int = 0
)
data class Proveedor(
    val nombreProveedor: String,
    val contacto: String,
    val categoria: String,
    val idProveedor: Int = 0
)

data class ModeloSucursales (val id_sucursal: Int,val nombre_sucursal: String, val direccion: String, val telefono: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConjuntoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
class Preferencias(context: Context) {

    private val preferences = context.getSharedPreferences(
        "app",
        Context.MODE_PRIVATE
    )

    fun guardar(prop: String, value: String) {
        preferences.edit()
            .putString(prop, value)
            .apply()
    }

    fun obtener(prop: String, def: String): String? {
        return preferences.getString(prop, def)
    }

    fun remover(prop: String) {
        preferences.edit()
            .remove(prop)
            .apply()
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    if (LocalNavigationEventDispatcherOwner.current == null) {
        val dispatcherOwner = remember {
            object : NavigationEventDispatcherOwner {
                override val navigationEventDispatcher = NavigationEventDispatcher()

            }
        }
        CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
            AppContentBody(modifier = modifier)
        }
    } else {
        AppContentBody(modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContentBody(modifier: Modifier = Modifier) {
    // Revisar si existe una sesión guardada para decidir qué pantalla mostrar.
    val context = LocalContext.current
    val preferencias = Preferencias(context)

    val destination = if (!preferencias.obtener("id", "").isNullOrEmpty()) {
        "MenuContent"
    } else {
        "LoginContent"
    }

    val navController = rememberNavController()

    // lista de los productos compartida entre el formulario y la lista
    val productos = remember { mutableStateListOf<Producto>() }

    // lista de los empleados compartida
    val empleados = remember { mutableStateListOf<Empleado>() }

    // listas compartidas de sucursales y proveedores
    val sucursales = remember { mutableStateListOf<Sucursal>() }
    val proveedores = remember { mutableStateListOf<Proveedor>() }

    LaunchedEffect(destination) {
        if (destination == "MenuContent") {
            try {
                val productosApi = cargarProductosApi()
                val empleadosApi = cargarEmpleadosApi()
                val sucursalesApi = cargarSucursalesApi()
                val proveedoresApi = cargarProveedoresApi()

                productos.clear()
                productos.addAll(productosApi)
                empleados.clear()
                empleados.addAll(empleadosApi)
                sucursales.clear()
                sucursales.addAll(sucursalesApi)
                proveedores.clear()
                proveedores.addAll(proveedoresApi)
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    "No se pudieron cargar los datos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "MenuContent"

    // este oculta la barra de pestañas inferiores cuando se esta en el login
    val showBottomBar = currentRoute != "LoginContent"

    val topBarTitle = when (currentRoute) {
        "LoginContent" -> "Inicio de Sesión"
        "MenuContent" -> "Menú Principal"
        "FormularioProductosContent" -> "Nuevo Producto"
        "ListaProductosContent" -> "Catálogo de Productos"
        "FormularioEmpleadosContent" -> "Registro de Empleado"
        "EmpleadosContent" -> "Catálogo de Empleados"
        "FormularioSucursalesContent" -> "Registro de Sucursal"
        "SucursalesContent" -> "Catálogo de Sucursales"
        "FormularioProveedoresContent" -> "Registro de Proveedor"
        "ProveedoresContent" -> "Catálogo de Proveedores"
        else -> "Conjunto"
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF0D3EA1), //Titulos les cambie el color
                        fontFamily = fuenteUnageo

//Titulo de arriba

                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {


            if (showBottomBar) {
                //empleados
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "EmpleadosContent",
                        onClick = {
                            if (currentRoute != "EmpleadosContent") {
                                navController.navigate("EmpleadosContent")
                            }
                        },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Empleados") },
                        label = { Text("Empleados") }
                    )

                    //sucursales
                    NavigationBarItem(
                        selected = currentRoute == "SucursalesContent",
                        onClick = {
                            if (currentRoute != "SucursalesContent") {
                                navController.navigate("SucursalesContent")
                            }
                        },
                        icon = { Icon(Icons.Default.Menu, contentDescription = "Sucursales") },
                        label = { Text("Sucursales") }
                    )

                    //menu
                    NavigationBarItem(
                        selected = currentRoute == "MenuContent",
                        onClick = {
                            if (currentRoute != "MenuContent") {
                                navController.navigate("MenuContent") {
                                    popUpTo("MenuContent") { inclusive = true }
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Add, contentDescription = "Añadir") },
                        label = { Text("Añadir") }
                    )

                    //proveedores
                    NavigationBarItem(
                        selected = currentRoute == "ProveedoresContent",
                        onClick = {
                            if (currentRoute != "ProveedoresContent") {
                                navController.navigate("ProveedoresContent")
                            }
                        },
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Proveedores") },
                        label = { Text("Proveedores") }
                    )

                    //lista
                    NavigationBarItem(
                        selected = currentRoute == "ListaProductosContent",
                        onClick = {
                            if (currentRoute != "ListaProductosContent") {
                                navController.navigate("ListaProductosContent")
                            }
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Lista") },
                        label = { Text("Lista") }
                    )
                }
            }
        }

    ) { innerPadding ->

        //union de las pestanas
        NavHost(
            navController = navController,
            startDestination = destination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("LoginContent") { LoginContent(navController) }
            composable("MenuContent") { MenuContent(navController = navController, productos = productos) }
            composable("FormularioProductosContent") {
                FormularioProductosContent(navController, productos, proveedores = proveedores)
            }
            composable("ListaProductosContent") {
                ListaProductosContent(navController, productos, proveedores = proveedores)
            }
            composable("FormularioEmpleadosContent") {
                FormularioEmpleadosContent(navController, empleados, sucursales = sucursales)
            }
            composable("EmpleadosContent") {
                EmpleadosContent(navController, empleados, sucursales = sucursales)
            }
            composable("SucursalesContent") {
                SucursalesContent(navController, sucursales)
            }
            composable("FormularioSucursalesContent") {
                FormularioSucursalesContent(navController, sucursales)
            }
            composable("ProveedoresContent") {
                ProveedoresContent(navController, proveedores)
            }
            composable("FormularioProveedoresContent") {
                FormularioProveedoresContent(navController, proveedores)
            }
        }
    }
}
// pantalla del inicio de sesion
// Enrique Ruiz Comentario 13/10/26
// Comentario de Comish Practica 4 Roy

data class ApiEnvelope<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

data class ProveedorApi(
    @SerializedName("id_proveedor") val id: Int,
    @SerializedName("nombre_proveedor") val nombre: String,
    val contacto: String,
    val categoria: String
)

data class ProveedorCreado(@SerializedName("id_proveedor") val id: Int)

data class ProductoApi(
    @SerializedName("id_producto") val id: Int,
    @SerializedName("nombre_producto") val nombre: String,
    val precio: Double,
    val categoria: String,
    @SerializedName("id_proveedor") val idProveedor: Int,
    @SerializedName("nombre_proveedor") val nombreProveedor: String
)

data class EmpleadoApi(
    @SerializedName("id_empleado") val id: Int,
    val nombre: String,
    val puesto: String,
    val sueldo: Double,
    val usuario: String,
    @SerializedName("id_sucursal") val idSucursal: Int,
    @SerializedName("nombre_sucursal") val nombreSucursal: String
)

data class SucursalApi(
    @SerializedName("id_sucursal") val id: Int,
    @SerializedName("nombre_sucursal") val nombre: String,
    val direccion: String,
    val telefono: String
)

interface ApiService {
    @POST("login.php")
    @FormUrlEncoded
    suspend fun iniciarSesion(
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String
    ): Response<String>

    @GET("productos.php?buscar")
    suspend fun buscarProductos(@Query("busqueda") busqueda: String = ""): Response<List<ProductoApi>>

    @POST("productos.php?registrar")
    @FormUrlEncoded
    suspend fun registrarProducto(
        @Field("nombre_producto") nombre: String,
        @Field("precio") precio: Double,
        @Field("categoria") categoria: String,
        @Field("id_proveedor") idProveedor: Int
    ): Response<String>

    @POST("productos.php?eliminar")
    @FormUrlEncoded
    suspend fun eliminarProducto(@Field("id") id: Int): Response<String>

    @GET("empleados.php?buscar")
    suspend fun buscarEmpleados(@Query("busqueda") busqueda: String = ""): Response<List<EmpleadoApi>>

    @POST("empleados.php?registrar")
    @FormUrlEncoded
    suspend fun registrarEmpleado(
        @Field("nombre") nombre: String,
        @Field("puesto") puesto: String,
        @Field("sueldo") sueldo: Double,
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String,
        @Field("id_sucursal") idSucursal: Int
    ): Response<String>

    @POST("empleados.php?eliminar")
    @FormUrlEncoded
    suspend fun eliminarEmpleado(@Field("id") id: Int): Response<String>

    @GET("sucursales.php?buscar")
    suspend fun buscarSucursales(@Query("busqueda") busqueda: String = ""): Response<List<SucursalApi>>

    @POST("sucursales.php?registrar")
    @FormUrlEncoded
    suspend fun registrarSucursal(
        @Field("nombre_sucursal") nombre: String,
        @Field("direccion") direccion: String,
        @Field("telefono") telefono: String
    ): Response<String>

    @POST("sucursales.php?eliminar")
    @FormUrlEncoded
    suspend fun eliminarSucursal(@Field("id") id: Int): Response<String>

    @GET("proveedores.php?buscar")
    suspend fun buscarProveedores(
        @Query("busqueda") busqueda: String = ""
    ): Response<ApiEnvelope<List<ProveedorApi>>>

    @POST("proveedores.php?registrar")
    @FormUrlEncoded
    suspend fun registrarProveedor(
        @Field("nombre_proveedor") nombre: String,
        @Field("contacto") contacto: String,
        @Field("categoria") categoria: String
    ): Response<ApiEnvelope<ProveedorCreado>>

    @POST("proveedores.php?eliminar")
    @FormUrlEncoded
    suspend fun eliminarProveedor(@Field("id") id: Int): Response<ApiEnvelope<ProveedorCreado>>

    @POST("productos.php?modificar")
    @FormUrlEncoded
    suspend fun editarProducto(
        @Field("id") id: Int,
        @Field("nombre_producto") nombre: String,
        @Field("precio") precio: Double,
        @Field("categoria") categoria: String,
        @Field("id_proveedor") idProveedor: Int
    ): Response<String>

    @POST("empleados.php?modificar")
    @FormUrlEncoded
    suspend fun editarEmpleado(
        @Field("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("puesto") puesto: String,
        @Field("sueldo") sueldo: Double,
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String,
        @Field("id_sucursal") idSucursal: Int
    ): Response<String>

    @POST("sucursales.php?modificar")
    @FormUrlEncoded
    suspend fun editarSucursal(
        @Field("id") id: Int,
        @Field("nombre_sucursal") nombre: String,
        @Field("direccion") direccion: String,
        @Field("telefono") telefono: String
    ): Response<String>

    @POST("proveedores.php?modificar")
    @FormUrlEncoded
    suspend fun editarProveedor(
        @Field("id") id: Int,
        @Field("nombre_proveedor") nombre: String,
        @Field("contacto") contacto: String,
        @Field("categoria") categoria: String
    ): Response<ApiEnvelope<ProveedorCreado>>
}

private fun <T> Response<T>.cuerpoExitoso(): T {
    if (!isSuccessful) throw IOException("Error del servidor (HTTP ${code()}).")
    return body() ?: throw IOException("El servidor devolvió una respuesta vacía.")
}

private fun <T> Response<ApiEnvelope<T>>.respuestaApi(): ApiEnvelope<T> {
    val respuesta = cuerpoExitoso()
    if (!respuesta.success) {
        throw IOException(respuesta.message ?: "La operación no se pudo completar.")
    }
    return respuesta
}

private fun Response<String>.respuestaOk() {
    if (cuerpoExitoso().trim() != "ok") {
        throw IOException("El servidor no confirmó la operación.")
    }
}

private fun ProductoApi.aProducto() = Producto(
    nombreProducto = nombre,
    precio = precio,
    categoria = categoria,
    proveedor = nombreProveedor,
    idProducto = id,
    idProveedor = idProveedor
)

private fun EmpleadoApi.aEmpleado() = Empleado(
    nombre = nombre,
    puesto = puesto,
    sueldo = sueldo,
    sucursal = nombreSucursal,
    idEmpleado = id,
    idSucursal = idSucursal
)

private fun SucursalApi.aSucursal() = Sucursal(
    nombreSucursal = nombre,
    direccion = direccion,
    telefono = telefono,
    idSucursal = id
)

private fun ProveedorApi.aProveedor() = Proveedor(
    nombreProveedor = nombre,
    contacto = contacto,
    categoria = categoria,
    idProveedor = id
)

val retrofit = Retrofit.Builder()
    .baseUrl("https://functioning-isaac-suggests-burn.trycloudflare.com/AW/conexconjunto/api/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(ApiService::class.java)

private suspend fun cargarProductosApi(): List<Producto> =
    api.buscarProductos().cuerpoExitoso().map { it.aProducto() }

private suspend fun cargarEmpleadosApi(): List<Empleado> =
    api.buscarEmpleados().cuerpoExitoso().map { it.aEmpleado() }

private suspend fun cargarSucursalesApi(): List<Sucursal> =
    api.buscarSucursales().cuerpoExitoso().map { it.aSucursal() }

private suspend fun cargarProveedoresApi(): List<Proveedor> {
    val proveedoresApi = api.buscarProveedores().respuestaApi().data
        ?: throw IOException("El servidor no devolvió la lista de proveedores.")
    return proveedoresApi.map { it.aProveedor() }
}


@Composable
fun LoginContent(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferencias = remember(context) { Preferencias(context) }
    val scope = rememberCoroutineScope()
    var usuario: String by remember { mutableStateOf("") }
    var contrasena: String by remember { mutableStateOf("") }
    var isLoading: Boolean by remember { mutableStateOf(false) }
    var loginError: String by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    )

    {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp) //sombra del cuadro
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(72.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFFD32F2F), // icono del candado rojo
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                //Este es un comentario del dia 07/10/2026
                //Comentario por: Santiago Hernandez 13/10/2026
                Text(
                    text = "Bienvenido de Nuevo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Ingresa tus credenciales",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(28.dp))

                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    placeholder = { Text("Ej. usuario123") }, //sugerencia
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    placeholder = { Text("••••••••") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(28.dp))

                if (loginError.isNotEmpty()) {
                    Text(
                        text = loginError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        if (usuario.isBlank() || contrasena.isBlank()) {
                            loginError = "Ingresa usuario y contraseña."
                        } else {
                            loginError = ""
                            isLoading = true
                            scope.launch {
                                try {
                                    val response = api.iniciarSesion(usuario.trim(), contrasena)
                                    if (!response.isSuccessful) {
                                        loginError = if (response.code() == 401) {
                                            "Usuario o contraseña incorrectos."
                                        } else {
                                            "Error del servidor (HTTP ${response.code()})."
                                        }
                                    } else {
                                        val idEmpleado = response.body()?.trim().orEmpty()
                                        if (idEmpleado.isEmpty()) {
                                            loginError = "Usuario o contraseña incorrectos."
                                        } else {
                                            preferencias.guardar("id", idEmpleado)
                                            navController.navigate("MenuContent") {
                                                popUpTo("LoginContent") { inclusive = true }
                                            }
                                        }
                                    }
                                } catch (e: IOException) {
                                    loginError = "No se pudo conectar con el servidor."
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    },

                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03A9F4), // boton de iniciar sesion verde
                        contentColor = Color.White

                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )

                {
                    Text(
                        if (isLoading) "Validando..." else "Iniciar Sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Pantalla Menu

@Composable
fun MenuContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    productos: MutableList<Producto> = mutableStateListOf()
) {
    val context = LocalContext.current
    val preferencias = remember(context) { Preferencias(context) }
    val totalValor = productos.sumOf { it.precio }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // tarjeta resumen del panel
        ElevatedCard(
            //el fondo cuadrado
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                Color(0xFF1565C0)
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp) //sombra ligera por efectp de la luz

        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Resumen del Inventario",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fuenteAccid

                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "${productos.size}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            fontFamily = fuenteAccid
                        )
                        Text(
                            text = "Productos en catálogo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontFamily = fuenteAccid
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$${"%.2f".format(totalValor)}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            fontFamily = fuenteAccid
                        )
                        Text(
                            text = "Valor estimado total",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontFamily = fuenteAccid
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // tarjeta opción 1 formulario
        ElevatedCard(
            onClick = { navController.navigate("FormularioProductosContent") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Formulario de Productos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Registra y añade un nuevo artículo",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta Opción Empleados Formulario
        ElevatedCard(
            onClick = { navController.navigate("FormularioEmpleadosContent") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Formulario de Empleados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Registra y añade un nuevo empleado",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            onClick = { navController.navigate("FormularioSucursalesContent") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Formulario de Sucursales",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Registra y añade una sucursal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            onClick = { navController.navigate("FormularioProveedoresContent") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Formulario de Proveedores",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Registra y añade un proveedor",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // botón cerrar sesión
        OutlinedCard(
            onClick = {
                preferencias.remover("id")
                navController.navigate("LoginContent") {
                    popUpTo("MenuContent") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// 3 formulario
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductosContent(
    navController: NavHostController,
    productos: MutableList<Producto>,
    modifier: Modifier = Modifier,
    proveedores: List<Proveedor> = emptyList()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nombreProducto: String by remember { mutableStateOf("") }
    var precio: String by remember { mutableStateOf("") }
    var categoria: String by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var proveedorSeleccionado by remember {
        mutableStateOf(proveedores.firstOrNull()?.nombreProveedor ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedButton(
            onClick = { navController.navigate("MenuContent") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Regresar al Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Registro de Producto",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Completa los campos para agregar un producto al catálogo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombreProducto,
                    onValueChange = { nombreProducto = it },
                    label = { Text("Nombre del Producto") },
                    placeholder = { Text("Ej. Sponch Fresa") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio ($)") },
                    placeholder = { Text("Ej. 25.50") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    placeholder = { Text("Ej. Galletas, Bebidas") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Combo box de Proveedor
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = proveedorSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Proveedor") },
                        placeholder = { Text("Selecciona un proveedor") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        singleLine = true
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (proveedores.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No hay proveedores registrados") },
                                onClick = { expanded = false }
                            )
                        } else {
                            proveedores.forEach { prov ->
                                DropdownMenuItem(
                                    text = { Text(prov.nombreProveedor) },
                                    onClick = {
                                        proveedorSeleccionado = prov.nombreProveedor
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        val nuevoPrecio = precio.toDoubleOrNull()
                        val proveedorElegido = proveedores.firstOrNull {
                            it.nombreProveedor == proveedorSeleccionado
                        }
                        if (
                            nombreProducto.isBlank() ||
                            nuevoPrecio == null ||
                            nuevoPrecio < 0 ||
                            categoria.isBlank() ||
                            proveedorElegido == null
                        ) {
                            Toast.makeText(
                                context,
                                "Completa los datos y selecciona un proveedor.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            scope.launch {
                                try {
                                    api.registrarProducto(
                                        nombreProducto.trim(),
                                        nuevoPrecio,
                                        categoria.trim(),
                                        proveedorElegido.idProveedor
                                    ).respuestaOk()
                                    productos.clear()
                                    productos.addAll(cargarProductosApi())
                                    Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
                                    navController.navigate("MenuContent")
                                } catch (e: IOException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)

                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Producto", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// pantalla 4 lista con la busqueda y filtrado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProductosContent(
    navController: NavHostController,
    productos: MutableList<Producto>,
    modifier: Modifier = Modifier,
    proveedores: List<Proveedor> = emptyList()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busqueda: String by remember { mutableStateOf("") }
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }

    val productosFiltrados = productos.filter { producto ->
        producto.nombreProducto.contains(busqueda, ignoreCase = true) ||
                producto.categoria.contains(busqueda, ignoreCase = true) ||
                producto.proveedor.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { navController.navigate("MenuContent") },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Menú")
            }

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${productosFiltrados.size} de ${productos.size} productos",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }


        Spacer(modifier = Modifier.height(14.dp))

        // Campo de Búsqueda
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar por nombre o categoría...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (productosFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (busqueda.isEmpty()) "No hay productos registrados" else "No se encontraron coincidencias",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(productosFiltrados) { producto ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = producto.nombreProducto.take(1).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = producto.nombreProducto,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = producto.categoria,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    if (producto.proveedor.isNotBlank()) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = MaterialTheme.colorScheme.tertiaryContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = producto.proveedor,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "$${"%.2f".format(producto.precio)}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { productoAEditar = producto },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${producto.nombreProducto}",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                api.eliminarProducto(producto.idProducto).respuestaOk()
                                                productos.remove(producto)
                                                Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                                            } catch (e: IOException) {
                                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${producto.nombreProducto}",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (productoAEditar != null) {
            val prod = productoAEditar!!
            var editNombre by remember(prod) { mutableStateOf(prod.nombreProducto) }
            var editPrecio by remember(prod) { mutableStateOf(prod.precio.toString()) }
            var editCategoria by remember(prod) { mutableStateOf(prod.categoria) }
            var editExpanded by remember(prod) { mutableStateOf(false) }
            var editProveedor by remember(prod) {
                mutableStateOf(proveedores.firstOrNull { it.idProveedor == prod.idProveedor }?.nombreProveedor ?: prod.proveedor)
            }

            AlertDialog(
                onDismissRequest = { productoAEditar = null },
                title = { Text("Editar Producto", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = editNombre,
                            onValueChange = { editNombre = it },
                            label = { Text("Nombre del Producto") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editPrecio,
                            onValueChange = { editPrecio = it },
                            label = { Text("Precio ($)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editCategoria,
                            onValueChange = { editCategoria = it },
                            label = { Text("Categoría") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenuBox(
                            expanded = editExpanded,
                            onExpandedChange = { editExpanded = !editExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = editProveedor,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Proveedor") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = editExpanded) },
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth(),
                                singleLine = true
                            )
                            ExposedDropdownMenu(
                                expanded = editExpanded,
                                onDismissRequest = { editExpanded = false }
                            ) {
                                if (proveedores.isEmpty()) {
                                    DropdownMenuItem(
                                        text = { Text(if (editProveedor.isNotBlank()) editProveedor else "Sin proveedores") },
                                        onClick = { editExpanded = false }
                                    )
                                } else {
                                    proveedores.forEach { p ->
                                        DropdownMenuItem(
                                            text = { Text(p.nombreProveedor) },
                                            onClick = {
                                                editProveedor = p.nombreProveedor
                                                editExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val nuevoPrecio = editPrecio.toDoubleOrNull()
                            val proveedorElegido = proveedores.firstOrNull { it.nombreProveedor == editProveedor }
                            val idProv = proveedorElegido?.idProveedor ?: prod.idProveedor

                            if (editNombre.isBlank() || nuevoPrecio == null || editCategoria.isBlank()) {
                                Toast.makeText(context, "Ingresa datos válidos", Toast.LENGTH_SHORT).show()
                            } else {
                                scope.launch {
                                    try {
                                        api.editarProducto(
                                            id = prod.idProducto,
                                            nombre = editNombre.trim(),
                                            precio = nuevoPrecio,
                                            categoria = editCategoria.trim(),
                                            idProveedor = idProv
                                        ).respuestaOk()
                                        productos.clear()
                                        productos.addAll(cargarProductosApi())
                                        Toast.makeText(context, "Producto actualizado", Toast.LENGTH_SHORT).show()
                                        productoAEditar = null
                                    } catch (e: IOException) {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { productoAEditar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadosContent(
    navController: NavHostController,
    empleados: MutableList<Empleado>,
    modifier: Modifier = Modifier,
    sucursales: List<Sucursal> = emptyList()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busqueda: String by remember { mutableStateOf("") }
    var empleadoAEditar by remember { mutableStateOf<Empleado?>(null) }

    val empleadosFiltrados = empleados.filter { emp ->
        emp.nombre.contains(busqueda, ignoreCase = true) ||
                emp.puesto.contains(busqueda, ignoreCase = true) ||
                emp.sucursal.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { navController.navigate("MenuContent") },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Menú")
            }

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${empleadosFiltrados.size} de ${empleados.size} empleados",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Campo de Búsqueda
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar empleado por nombre o puesto...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (empleadosFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (busqueda.isEmpty()) "No hay empleados registrados" else "No se encontraron coincidencias",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(empleadosFiltrados) { emp ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = emp.nombre.take(1).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = emp.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = emp.puesto,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    if (emp.sucursal.isNotBlank()) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = MaterialTheme.colorScheme.tertiaryContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = emp.sucursal,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "$${"%.2f".format(emp.sueldo)}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { empleadoAEditar = emp },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${emp.nombre}",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                api.eliminarEmpleado(emp.idEmpleado).respuestaOk()
                                                empleados.remove(emp)
                                                Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                                            } catch (e: IOException) {
                                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${emp.nombre}",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (empleadoAEditar != null) {
            val emp = empleadoAEditar!!
            var editNombre by remember(emp) { mutableStateOf(emp.nombre) }
            var editPuesto by remember(emp) { mutableStateOf(emp.puesto) }
            var editSueldo by remember(emp) { mutableStateOf(emp.sueldo.toString()) }
            var editUsuario by remember(emp) { mutableStateOf("") }
            var editContrasena by remember(emp) { mutableStateOf("") }
            var editExpanded by remember(emp) { mutableStateOf(false) }
            var editSucursal by remember(emp) {
                mutableStateOf(sucursales.firstOrNull { it.idSucursal == emp.idSucursal }?.nombreSucursal ?: emp.sucursal)
            }

            AlertDialog(
                onDismissRequest = { empleadoAEditar = null },
                title = { Text("Editar Empleado", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = editNombre,
                            onValueChange = { editNombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editPuesto,
                            onValueChange = { editPuesto = it },
                            label = { Text("Puesto") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editSueldo,
                            onValueChange = { editSueldo = it },
                            label = { Text("Sueldo ($)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editUsuario,
                            onValueChange = { editUsuario = it },
                            label = { Text("Usuario (opcional)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editContrasena,
                            onValueChange = { editContrasena = it },
                            label = { Text("Contraseña (opcional)") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenuBox(
                            expanded = editExpanded,
                            onExpandedChange = { editExpanded = !editExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = editSucursal,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Sucursal") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = editExpanded) },
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth(),
                                singleLine = true
                            )
                            ExposedDropdownMenu(
                                expanded = editExpanded,
                                onDismissRequest = { editExpanded = false }
                            ) {
                                if (sucursales.isEmpty()) {
                                    DropdownMenuItem(
                                        text = { Text(if (editSucursal.isNotBlank()) editSucursal else "Sin sucursales") },
                                        onClick = { editExpanded = false }
                                    )
                                } else {
                                    sucursales.forEach { s ->
                                        DropdownMenuItem(
                                            text = { Text(s.nombreSucursal) },
                                            onClick = {
                                                editSucursal = s.nombreSucursal
                                                editExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val nuevoSueldo = editSueldo.toDoubleOrNull()
                            val sucursalElegida = sucursales.firstOrNull { it.nombreSucursal == editSucursal }
                            val idSuc = sucursalElegida?.idSucursal ?: emp.idSucursal

                            if (editNombre.isBlank() || editPuesto.isBlank() || nuevoSueldo == null) {
                                Toast.makeText(context, "Ingresa datos válidos", Toast.LENGTH_SHORT).show()
                            } else {
                                scope.launch {
                                    try {
                                        api.editarEmpleado(
                                            id = emp.idEmpleado,
                                            nombre = editNombre.trim(),
                                            puesto = editPuesto.trim(),
                                            sueldo = nuevoSueldo,
                                            usuario = editUsuario.trim(),
                                            contrasena = editContrasena,
                                            idSucursal = idSuc
                                        ).respuestaOk()
                                        empleados.clear()
                                        empleados.addAll(cargarEmpleadosApi())
                                        Toast.makeText(context, "Empleado actualizado", Toast.LENGTH_SHORT).show()
                                        empleadoAEditar = null
                                    } catch (e: IOException) {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { empleadoAEditar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEmpleadosContent(
    navController: NavHostController,
    empleados: MutableList<Empleado>,
    modifier: Modifier = Modifier,
    sucursales: List<Sucursal> = emptyList()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nombreEmpleado: String by remember { mutableStateOf("") }
    var puesto: String by remember { mutableStateOf("") }
    var sueldo: String by remember { mutableStateOf("") }
    var usuario: String by remember { mutableStateOf("") }
    var contrasena: String by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var sucursalSeleccionada by remember {
        mutableStateOf(sucursales.firstOrNull()?.nombreSucursal ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedButton(
            onClick = { navController.navigate("MenuContent") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Regresar al Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Registro de Empleado",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Completa los campos para registrar un nuevo empleado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombreEmpleado,
                    onValueChange = { nombreEmpleado = it },
                    label = { Text("Nombre del Empleado") },
                    placeholder = { Text("Ej. Juan Pérez") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = puesto,
                    onValueChange = { puesto = it },
                    label = { Text("Puesto") },
                    placeholder = { Text("Ej. Gerente, Vendedor") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = sueldo,
                    onValueChange = { sueldo = it },
                    label = { Text("Sueldo ($)") },
                    placeholder = { Text("Ej. 12000.00") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Combo box de Sucursal
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = sucursalSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sucursal") },
                        placeholder = { Text("Selecciona una sucursal") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        singleLine = true
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (sucursales.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No hay sucursales registradas") },
                                onClick = { expanded = false }
                            )
                        } else {
                            sucursales.forEach { suc ->
                                DropdownMenuItem(
                                    text = { Text(suc.nombreSucursal) },
                                    onClick = {
                                        sucursalSeleccionada = suc.nombreSucursal
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        val nuevoSueldo = sueldo.toDoubleOrNull()
                        val sucursalElegida = sucursales.firstOrNull {
                            it.nombreSucursal == sucursalSeleccionada
                        }
                        if (
                            nombreEmpleado.isBlank() ||
                            puesto.isBlank() ||
                            nuevoSueldo == null ||
                            nuevoSueldo < 0 ||
                            usuario.isBlank() ||
                            contrasena.isBlank() ||
                            sucursalElegida == null
                        ) {
                            Toast.makeText(
                                context,
                                "Completa todos los campos y selecciona una sucursal.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            scope.launch {
                                try {
                                    api.registrarEmpleado(
                                        nombreEmpleado.trim(),
                                        puesto.trim(),
                                        nuevoSueldo,
                                        usuario.trim(),
                                        contrasena,
                                        sucursalElegida.idSucursal
                                    ).respuestaOk()
                                    empleados.clear()
                                    empleados.addAll(cargarEmpleadosApi())
                                    Toast.makeText(context, "Empleado guardado", Toast.LENGTH_SHORT).show()
                                    navController.navigate("MenuContent")
                                } catch (e: IOException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Empleado", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}



@Composable
fun SucursalesContent(
    navController: NavHostController,
    sucursales: MutableList<Sucursal>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busqueda: String by remember { mutableStateOf("") }
    var sucursalAEditar by remember { mutableStateOf<Sucursal?>(null) }

    val sucursalesFiltradas = sucursales.filter { suc ->
        suc.nombreSucursal.contains(busqueda, ignoreCase = true) ||
                suc.direccion.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { navController.navigate("MenuContent") },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Menú")
            }

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${sucursalesFiltradas.size} de ${sucursales.size} sucursales",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar sucursal por nombre o dirección...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (sucursalesFiltradas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (busqueda.isEmpty()) "No hay sucursales registradas" else "No se encontraron coincidencias",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(sucursalesFiltradas) { suc ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = suc.nombreSucursal.take(1).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = suc.nombreSucursal,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = suc.direccion,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Tel: ${suc.telefono}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { sucursalAEditar = suc },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${suc.nombreSucursal}",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                api.eliminarSucursal(suc.idSucursal).respuestaOk()
                                                sucursales.remove(suc)
                                                Toast.makeText(context, "Sucursal eliminada", Toast.LENGTH_SHORT).show()
                                            } catch (e: IOException) {
                                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${suc.nombreSucursal}",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (sucursalAEditar != null) {
            val suc = sucursalAEditar!!
            var editNombre by remember(suc) { mutableStateOf(suc.nombreSucursal) }
            var editDireccion by remember(suc) { mutableStateOf(suc.direccion) }
            var editTelefono by remember(suc) { mutableStateOf(suc.telefono) }

            AlertDialog(
                onDismissRequest = { sucursalAEditar = null },
                title = { Text("Editar Sucursal", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = editNombre,
                            onValueChange = { editNombre = it },
                            label = { Text("Nombre de Sucursal") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editDireccion,
                            onValueChange = { editDireccion = it },
                            label = { Text("Dirección") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editTelefono,
                            onValueChange = { editTelefono = it },
                            label = { Text("Teléfono") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (editNombre.isBlank() || editDireccion.isBlank() || editTelefono.isBlank()) {
                                Toast.makeText(context, "Ingresa datos válidos", Toast.LENGTH_SHORT).show()
                            } else {
                                scope.launch {
                                    try {
                                        api.editarSucursal(
                                            id = suc.idSucursal,
                                            nombre = editNombre.trim(),
                                            direccion = editDireccion.trim(),
                                            telefono = editTelefono.trim()
                                        ).respuestaOk()
                                        sucursales.clear()
                                        sucursales.addAll(cargarSucursalesApi())
                                        Toast.makeText(context, "Sucursal actualizada", Toast.LENGTH_SHORT).show()
                                        sucursalAEditar = null
                                    } catch (e: IOException) {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { sucursalAEditar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun FormularioSucursalesContent(
    navController: NavHostController,
    sucursales: MutableList<Sucursal>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nombreSucursal: String by remember { mutableStateOf("") }
    var direccion: String by remember { mutableStateOf("") }
    var telefono: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedButton(
            onClick = { navController.navigate("MenuContent") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Regresar al Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Registro de Sucursal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Completa los campos para registrar una nueva sucursal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombreSucursal,
                    onValueChange = { nombreSucursal = it },
                    label = { Text("Nombre de la Sucursal") },
                    placeholder = { Text("Ej. Sucursal Sur") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    placeholder = { Text("Ej. Av. Juárez #500") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    placeholder = { Text("Ej. 555-9876") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        if (
                            nombreSucursal.isBlank() ||
                            direccion.isBlank() ||
                            telefono.isBlank()
                        ) {
                            Toast.makeText(context, "Completa todos los campos.", Toast.LENGTH_SHORT).show()
                        } else {
                            scope.launch {
                                try {
                                    api.registrarSucursal(
                                        nombreSucursal.trim(),
                                        direccion.trim(),
                                        telefono.trim()
                                    ).respuestaOk()
                                    sucursales.clear()
                                    sucursales.addAll(cargarSucursalesApi())
                                    Toast.makeText(context, "Sucursal guardada", Toast.LENGTH_SHORT).show()
                                    navController.navigate("MenuContent")
                                } catch (e: IOException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Sucursal", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProveedoresContent(
    navController: NavHostController,
    proveedores: MutableList<Proveedor>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busqueda: String by remember { mutableStateOf("") }
    var proveedorAEditar by remember { mutableStateOf<Proveedor?>(null) }

    val proveedoresFiltrados = proveedores.filter { prov ->
        prov.nombreProveedor.contains(busqueda, ignoreCase = true) ||
                prov.categoria.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { navController.navigate("MenuContent") },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Menú")
            }

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${proveedoresFiltrados.size} de ${proveedores.size} proveedores",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar proveedor por nombre o categoría...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (proveedoresFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (busqueda.isEmpty()) "No hay proveedores registrados" else "No se encontraron coincidencias",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(proveedoresFiltrados) { prov ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = prov.nombreProveedor.take(1).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = prov.nombreProveedor,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = prov.categoria,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Contacto: ${prov.contacto}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { proveedorAEditar = prov },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${prov.nombreProveedor}",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                api.eliminarProveedor(prov.idProveedor).respuestaApi()
                                                proveedores.remove(prov)
                                                Toast.makeText(context, "Proveedor eliminado", Toast.LENGTH_SHORT).show()
                                            } catch (e: IOException) {
                                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${prov.nombreProveedor}",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (proveedorAEditar != null) {
            val prov = proveedorAEditar!!
            var editNombre by remember(prov) { mutableStateOf(prov.nombreProveedor) }
            var editContacto by remember(prov) { mutableStateOf(prov.contacto) }
            var editCategoria by remember(prov) { mutableStateOf(prov.categoria) }

            AlertDialog(
                onDismissRequest = { proveedorAEditar = null },
                title = { Text("Editar Proveedor", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = editNombre,
                            onValueChange = { editNombre = it },
                            label = { Text("Nombre del Proveedor") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editContacto,
                            onValueChange = { editContacto = it },
                            label = { Text("Contacto") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editCategoria,
                            onValueChange = { editCategoria = it },
                            label = { Text("Categoría") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (editNombre.isBlank() || editContacto.isBlank() || editCategoria.isBlank()) {
                                Toast.makeText(context, "Ingresa datos válidos", Toast.LENGTH_SHORT).show()
                            } else {
                                scope.launch {
                                    try {
                                        api.editarProveedor(
                                            id = prov.idProveedor,
                                            nombre = editNombre.trim(),
                                            contacto = editContacto.trim(),
                                            categoria = editCategoria.trim()
                                        ).respuestaApi()
                                        proveedores.clear()
                                        proveedores.addAll(cargarProveedoresApi())
                                        Toast.makeText(context, "Proveedor actualizado", Toast.LENGTH_SHORT).show()
                                        proveedorAEditar = null
                                    } catch (e: IOException) {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { proveedorAEditar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun FormularioProveedoresContent(
    navController: NavHostController,
    proveedores: MutableList<Proveedor>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nombreProveedor: String by remember { mutableStateOf("") }
    var contacto: String by remember { mutableStateOf("") }
    var categoria: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedButton(
            onClick = { navController.navigate("MenuContent") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Regresar al Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Registro de Proveedor",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Completa los campos para registrar un nuevo proveedor",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombreProveedor,
                    onValueChange = { nombreProveedor = it },
                    label = { Text("Nombre del Proveedor") },
                    placeholder = { Text("Ej. Bimbo") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = contacto,
                    onValueChange = { contacto = it },
                    label = { Text("Contacto") },
                    placeholder = { Text("Ej. Juan Pérez") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría / Producto") },
                    placeholder = { Text("Ej. Alimentos, Lácteos") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        if (
                            nombreProveedor.isBlank() ||
                            contacto.isBlank() ||
                            categoria.isBlank()
                        ) {
                            Toast.makeText(context, "Completa todos los campos.", Toast.LENGTH_SHORT).show()
                        } else {
                            scope.launch {
                                try {
                                    api.registrarProveedor(
                                        nombreProveedor.trim(),
                                        contacto.trim(),
                                        categoria.trim()
                                    ).respuestaApi()
                                    proveedores.clear()
                                    proveedores.addAll(cargarProveedoresApi())
                                    Toast.makeText(context, "Proveedor guardado", Toast.LENGTH_SHORT).show()
                                    navController.navigate("MenuContent")
                                } catch (e: IOException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Proveedor", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// previews individuales y generales

@Preview(name = "1. Login", showBackground = true)
@Composable
fun LoginContentPreview() {
    val dispatcherOwner = remember {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = NavigationEventDispatcher()
        }
    }
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
        ConjuntoTheme {
            LoginContent(rememberNavController())
        }
    }
}

@Preview(name = "2. Menú", showBackground = true)
@Composable
fun MenuContentPreview() {
    val dispatcherOwner = remember {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = NavigationEventDispatcher()
        }
    }
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
        ConjuntoTheme {
            MenuContent(rememberNavController())
        }
    }
}

@Preview(name = "3. Formulario", showBackground = true)
@Composable
fun FormularioContentPreview() {
    val dispatcherOwner = remember {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = NavigationEventDispatcher()
        }
    }
    val productos = remember {
        mutableStateListOf(
            Producto("Sponch Fresa", 23.0, "Galletas", "Distribuidora Bimbo")
        )
    }
    val proveedores = remember {
        mutableStateListOf(
            Proveedor("Distribuidora Bimbo", "Carlos Ruiz", "Alimentos")
        )
    }
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
        ConjuntoTheme {
            FormularioProductosContent(rememberNavController(), productos, proveedores = proveedores)
        }
    }
}

@Preview(name = "4. Lista", showBackground = true)
@Composable
fun ListaContentPreview() {
    val dispatcherOwner = remember {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = NavigationEventDispatcher()
        }
    }
    val productos = remember {
        mutableStateListOf(
            Producto("Sponch Fresa", 23.0, "Galletas"),
            Producto("Emperador Combinado", 22.0, "Galletas")
        )
    }
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
        ConjuntoTheme {
            ListaProductosContent(rememberNavController(), productos)
        }
    }
}

@Preview(name = "5. App Completa", showBackground = true)
@Composable
fun AppContentPreview() {
    ConjuntoTheme {
        AppContent()
    }
}
