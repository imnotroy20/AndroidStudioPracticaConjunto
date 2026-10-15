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
import androidx.compose.material.icons.filled.Lock //icono de candado
import androidx.compose.material.icons.filled.Menu //icono de menú
import androidx.compose.material.icons.filled.Person //icono de usuario opersona
import androidx.compose.material.icons.filled.Search //icono de lupa para buscar
import androidx.compose.material.icons.filled.ShoppingCart //icono de carrito de lAs compras
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

val fuenteUnageo = FontFamily(Font(R.font.unageo_bold))//font
val fuenteAlienTrial = FontFamily(Font(R.font.alientrial))//font

val fuenteAccid = FontFamily(Font(R.font.accid))//font
val fuenteMaxi = FontFamily(Font(R.font.maxi))//font


// data class para representar el producto q es la compartida entre pantallas
data class Producto(
    val nombreProducto: String,
    val precio: Double,
    val categoria: String,
    val proveedor: String = ""
)

// data class para representar el empleado
data class Empleado(
    val nombre: String,
    val puesto: String,
    val sueldo: Double,
    val sucursal: String = ""
)

// data class para representar sucursales y proveedores
data class Sucursal(val nombreSucursal: String, val direccion: String, val telefono: String)
data class Proveedor(val nombreProveedor: String, val contacto: String, val categoria: String)

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
    val navController = rememberNavController()

    // lista de los productos compartida entre el formulario y la lista
    val productos = remember {
        mutableStateListOf(
            Producto("Sponch Fresa", 23.0, "Galletas", "Distribuidora Bimbo"),
            Producto("Emperador Combinado", 22.0, "Galletas", "Distribuidora Bimbo"),
            Producto("Jugo de Naranja 1L", 35.5, "Bebidas", "Sabritas S.A.")
        )
    }

    // lista de los empleados compartida
    val empleados = remember {
        mutableStateListOf(
            Empleado("Juan Pérez", "Gerente", 15000.0, "Sucursal Centro"),
            Empleado("María López", "Vendedora", 8500.0, "Sucursal Norte")
        )
    }

    // listas compartidas de sucursales y proveedores
    val sucursales = remember {
        mutableStateListOf(
            Sucursal("Sucursal Centro", "Av. Principal #123", "555-1234"),
            Sucursal("Sucursal Norte", "Blvd. Norte #456", "555-5678")
        )
    }

    val proveedores = remember {
        mutableStateListOf(
            Proveedor("Distribuidora Bimbo", "Carlos Ruiz", "Alimentos"),
            Proveedor("Sabritas S.A.", "Ana Gómez", "Snacks")
        )
    }
//comentario
    

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
            startDestination = "MenuContent",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("LoginContent") { LoginContent(navController) }
            composable("MenuContent") { MenuContent(navController = navController, productos = productos) }
            composable("FormularioProductosContent") {
                FormularioProductosContent(navController, productos, proveedores = proveedores)
            }
            composable("ListaProductosContent") {
                ListaProductosContent(navController, productos)
            }
            composable("FormularioEmpleadosContent") {
                FormularioEmpleadosContent(navController, empleados, sucursales = sucursales)
            }
            composable("EmpleadosContent") {
                EmpleadosContent(navController, empleados)
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
@Composable
fun LoginContent(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var usuario: String by remember { mutableStateOf("") }
    var contrasena: String by remember { mutableStateOf("") }

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

                Button(
                    onClick = {
                        if (usuario.isNotBlank()) {
                            Toast.makeText(context, "¡Bienvenido, $usuario!", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("MenuContent") {
                            popUpTo("LoginContent") { inclusive = true }
                        }
                    },

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
                    Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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
    val totalValor = productos.sumOf { it.precio }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center, // arriba o center si lo quiero centrar
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
                        if (nombreProducto.isNotBlank()) {
                            val nuevoNombre = nombreProducto.trim()
                            val nuevoPrecio = precio.toDoubleOrNull() ?: 0.0
                            val nuevaCat = categoria.ifBlank { "General" }.trim()

                            productos.add(
                                Producto(
                                    nombreProducto = nuevoNombre,
                                    precio = nuevoPrecio,
                                    categoria = nuevaCat,
                                    proveedor = proveedorSeleccionado
                                )
                            )
                            Toast.makeText(context, "Producto '$nuevoNombre' guardado", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("MenuContent")
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
@Composable
fun ListaProductosContent(
    navController: NavHostController,
    productos: MutableList<Producto>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var busqueda: String by remember { mutableStateOf("") }

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

                            IconButton(
                                onClick = {
                                    productos.remove(producto)
                                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
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
}

@Composable
fun EmpleadosContent(
    navController: NavHostController,
    empleados: MutableList<Empleado>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var busqueda: String by remember { mutableStateOf("") }

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

                            IconButton(
                                onClick = {
                                    empleados.remove(emp)
                                    Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show()
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
    var nombreEmpleado: String by remember { mutableStateOf("") }
    var puesto: String by remember { mutableStateOf("") }
    var sueldo: String by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var sucursalSeleccionada by remember {
        mutableStateOf(sucursales.firstOrNull()?.nombreSucursal ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
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
                        if (nombreEmpleado.isNotBlank()) {
                            val nuevoNombre = nombreEmpleado.trim()
                            val nuevoPuesto = puesto.ifBlank { "General" }.trim()
                            val nuevoSueldo = sueldo.toDoubleOrNull() ?: 0.0

                            empleados.add(
                                Empleado(
                                    nombre = nuevoNombre,
                                    puesto = nuevoPuesto,
                                    sueldo = nuevoSueldo,
                                    sucursal = sucursalSeleccionada
                                )
                            )
                            Toast.makeText(context, "Empleado '$nuevoNombre' guardado", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("MenuContent")
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
    var busqueda: String by remember { mutableStateOf("") }

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

                            IconButton(
                                onClick = {
                                    sucursales.remove(suc)
                                    Toast.makeText(context, "Sucursal eliminada", Toast.LENGTH_SHORT).show()
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
}

@Composable
fun FormularioSucursalesContent(
    navController: NavHostController,
    sucursales: MutableList<Sucursal>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var nombreSucursal: String by remember { mutableStateOf("") }
    var direccion: String by remember { mutableStateOf("") }
    var telefono: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
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
                        if (nombreSucursal.isNotBlank()) {
                            val nuevoNombre = nombreSucursal.trim()
                            val nuevaDir = direccion.ifBlank { "Sin dirección" }.trim()
                            val nuevoTel = telefono.ifBlank { "Sin teléfono" }.trim()

                            sucursales.add(
                                Sucursal(
                                    nombreSucursal = nuevoNombre,
                                    direccion = nuevaDir,
                                    telefono = nuevoTel
                                )
                            )
                            Toast.makeText(context, "Sucursal '$nuevoNombre' guardada", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("MenuContent")
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
    var busqueda: String by remember { mutableStateOf("") }

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

                            IconButton(
                                onClick = {
                                    proveedores.remove(prov)
                                    Toast.makeText(context, "Proveedor eliminado", Toast.LENGTH_SHORT).show()
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
}

@Composable
fun FormularioProveedoresContent(
    navController: NavHostController,
    proveedores: MutableList<Proveedor>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var nombreProveedor: String by remember { mutableStateOf("") }
    var contacto: String by remember { mutableStateOf("") }
    var categoria: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
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
                        if (nombreProveedor.isNotBlank()) {
                            val nuevoNombre = nombreProveedor.trim()
                            val nuevoContacto = contacto.ifBlank { "Sin contacto" }.trim()
                            val nuevaCat = categoria.ifBlank { "General" }.trim()

                            proveedores.add(
                                Proveedor(
                                    nombreProveedor = nuevoNombre,
                                    contacto = nuevoContacto,
                                    categoria = nuevaCat
                                )
                            )
                            Toast.makeText(context, "Proveedor '$nuevoNombre' guardado", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("MenuContent")
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
