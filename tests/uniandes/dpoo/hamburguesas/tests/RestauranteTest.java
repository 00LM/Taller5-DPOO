package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {
	
	private Restaurante restaurante;
	private Pedido pedido;
	private Pedido antePedido; 
	private Pedido pedidoCurso;
	ArrayList<ProductoMenu> listaMenuEsperada;
	ArrayList<Combo> listaCombosEsperada;
	ArrayList<Ingrediente> listaIngredientesEsperada;
	
	
	@BeforeEach
    public void setUp() throws Exception {
        restaurante = new Restaurante();
        pedido = new Pedido("Maria", "Soacha");
        antePedido = new Pedido("Jose", "Sutatenza");
    }
	
	@Test
	public void testYaHayUnPedidoEnCursoException() throws YaHayUnPedidoEnCursoException {
	    restaurante.iniciarPedido("Claudia", "Tibirita");
	    assertThrows(YaHayUnPedidoEnCursoException.class, ()-> restaurante.iniciarPedido("Lina", "Ramiriquí"));

	}
	
	@Test
	public void testIniciarPedido() throws Exception {
	    restaurante.iniciarPedido("Claudia", "Tibirita");
	    Pedido pedidoCurso = restaurante.getPedidoEnCurso();
	    assertEquals("Claudia", pedidoCurso.getNombreCliente(), "El nombre del clinete no concuerda con el esperado");
	}
	
	@Test
	public void testNoHayPedidoEnCursoException() throws NoHayPedidoEnCursoException, IOException {
	    assertThrows(NoHayPedidoEnCursoException.class, ()-> restaurante.cerrarYGuardarPedido());

	}
	
	@Test
	public void testCerrarYGuardarPedido() throws NoHayPedidoEnCursoException, IOException, YaHayUnPedidoEnCursoException {
		String contenido;
		int idPedido;
		
		restaurante.iniciarPedido("Carla", "Mompox");
		pedidoCurso = restaurante.getPedidoEnCurso();
		idPedido =  restaurante.getPedidoEnCurso().getIdPedido();
		
		restaurante.cerrarYGuardarPedido();
		
		contenido = Files.readString(Paths.get("./facturas/"+"factura_"+idPedido+".txt"));
		
		assertEquals(pedidoCurso.generarTextoFactura(), contenido, "La factura del pedido guardado no coincide con la esperada");
	}
	
	@Test
	void testGetPedidos() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException
	{
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		assertEquals(listaPedidos, restaurante.getPedidos(), "Los pedidos obtenidos no coinciden con los esperados");
		restaurante.iniciarPedido("Lina", "Ramiriquí");
		assertEquals(listaPedidos, restaurante.getPedidos(), "Los pedidos obtenidos no coinciden con los esperados");
		
		pedidoCurso = restaurante.getPedidoEnCurso();
		listaPedidos.add(pedidoCurso);
		restaurante.cerrarYGuardarPedido();
		
		assertEquals(listaPedidos.size(), restaurante.getPedidos().size(), "No concuerda la cantidad de pedidos esperados");
		
	}
	
	private void prepararDatosDePrueba()
	{
		listaMenuEsperada = new ArrayList<ProductoMenu>( );
		listaCombosEsperada = new ArrayList<>();
		listaIngredientesEsperada = new ArrayList<>();
		
		ArrayList<ProductoMenu> comboA = new ArrayList<>();
		ArrayList<ProductoMenu> comboB = new ArrayList<>();
		
		ProductoMenu item;
		Combo combo;
		Ingrediente ingr;
		
		item = new ProductoMenu("Hamburguesa base", 14000);
		listaMenuEsperada.add(item);
		comboA.add(item);

		item = new ProductoMenu("Hamburguesa especial", 16000);
		listaMenuEsperada.add(item);
		comboB.add(item);

		item = new ProductoMenu("Papas en casco", 5500);
		listaMenuEsperada.add(item);
		comboA.add(item);
		comboB.add(item);

		item = new ProductoMenu("Manzana postobón", 5000);
		listaMenuEsperada.add(item);
		comboA.add(item);
		comboB.add(item);

		
		combo = new Combo("Combo base", 0.1, comboA);
		listaCombosEsperada.add(combo);

		combo = new Combo("Combo especial", 0.1, comboB);
		listaCombosEsperada.add(combo);

		listaIngredientesEsperada.add(new Ingrediente("lechuga", 1000));
		listaIngredientesEsperada.add(new Ingrediente("tomate", 1000));
		listaIngredientesEsperada.add(new Ingrediente("cebolla", 1000));
		listaIngredientesEsperada.add(new Ingrediente("queso mozzarella", 2500));
		listaIngredientesEsperada.add(new Ingrediente("huevo", 2500));
	}

	@Test
	void verificarCargaCompletaDeDatos() throws IOException, NumberFormatException, HamburguesaException {
		prepararDatosDePrueba();

		File archivoIngr = new File("./dataingredientes_test.txt");
		File archivoProductos = new File("tests/uniandes/dpoo/hamburguesas/tests/datosPrueba/menu_test.txt");
		File archivoCombos = new File("tests/uniandes/dpoo/hamburguesas/tests/datosPrueba/combos_test.txt");

		rest1.cargarInformacionRestaurante(archivoIngr, archivoProductos, archivoCombos);

		ArrayList<Ingrediente> ingredientesCargados = rest1.getIngredientes();
		ArrayList<ProductoMenu> menuCargado = rest1.getMenuBase();
		ArrayList<Combo> combosCargados = rest1.getMenuCombos();

		// Validación de ingredientes
		assertEquals(listaIngredientesEsperada.size(), ingredientesCargados.size(), "Número incorrecto de ingredientes");

		for (int i = 0; i < ingredientesCargados.size(); i++) {
			Ingrediente esperado = listaIngredientesEsperada.get(i);
			Ingrediente cargado = ingredientesCargados.get(i);
			assertEquals(esperado.getNombre(), cargado.getNombre(), "Nombre de ingrediente incorrecto");
			assertEquals(esperado.getCostoAdicional(), cargado.getCostoAdicional(), "Costo adicional del ingrediente incorrecto");
		}

		// Validación de productos del menú
		assertEquals(listaMenuEsperada.size(), menuCargado.size(), "Cantidad incorrecta de productos en el menú");

		for (int i = 0; i < menuCargado.size(); i++) {
			ProductoMenu esperado = listaMenuEsperada.get(i);
			ProductoMenu cargado = menuCargado.get(i);
			assertEquals(esperado.getNombre(), cargado.getNombre(), "Nombre de producto incorrecto");
			assertEquals(esperado.getPrecio(), cargado.getPrecio(), "Precio de producto incorrecto");
		}

		// Validación de combos
		assertEquals(listaCombosEsperada.size(), combosCargados.size(), "Cantidad incorrecta de combos");

		for (int i = 0; i < combosCargados.size(); i++) {
			Combo esperado = listaCombosEsperada.get(i);
			Combo cargado = combosCargados.get(i);
			assertEquals(esperado.getNombre(), cargado.getNombre(), "Nombre de combo incorrecto");
			assertEquals(esperado.getPrecio(), cargado.getPrecio(), "Precio de combo incorrecto");
		}
	}

	
	
	

}
