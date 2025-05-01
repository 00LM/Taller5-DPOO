package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class PedidoTest {

	private Pedido pedido;

    @BeforeEach
    void setUp() throws Exception {
        pedido = new Pedido("Carlos", "Calle 26");

        //perro solo
        pedido.agregarProducto(new ProductoMenu("Perro caliente", 12000));
        
        //combo hamburguesa gsaeosa. precio : 13000 -> 11700
        ArrayList<ProductoMenu> combo = new ArrayList<ProductoMenu>();
        combo.add(new ProductoMenu("Hamburguesa grande", 10000));
        combo.add(new ProductoMenu("Gaseosa", 3000));
        pedido.agregarProducto(new Combo("Combo 1", 0.1, combo));
        
        
        //hambiurguesa ajustada. precio : 21500
        ProductoAjustado productoAjustado = new ProductoAjustado(new ProductoMenu("Hamburguesa especial", 15000));
        productoAjustado.anadirAgregados(new Ingrediente("Pepinillos", 2500));
        productoAjustado.anadirAgregados(new Ingrediente("Tocineta", 4000));
        productoAjustado.anadirEliminados(new Ingrediente("Lechuga", 1500));
        productoAjustado.anadirEliminados(new Ingrediente("Mayonesa", 500));
        pedido.agregarProducto(productoAjustado);
    }

    @Test
    void testGetIDPedido() throws Exception {
        assertEquals(1, pedido.getIdPedido(), "El ID del pedido no es correcto");
    }

    @Test
    void testGetNombreCliente() throws Exception {
        assertEquals("Carlos", pedido.getNombreCliente(), "El nombre del cliente no es correcto");
    }
    
    @Test
    void testAgregarProducto() throws Exception {
    	pedido.agregarProducto(new ProductoMenu("Papas fritas", 5000));
        assertEquals("Papas fritas", pedido.getProductosPedido().getLast().getNombre(), "El nombre del producto agregado no es correcto");
        assertEquals(5000, pedido.getProductosPedido().getLast().getPrecio(), "El precio del producto agregado no es correcto");
    }

    @Test
    void testGetPrecioTotalPedido() throws Exception {
        int precioNeto = (int) (12000 + (13000 - (13000 * 0.1)) + 15000 + 2500 + 4000);
        int iva = (int) (precioNeto * 0.19);
        assertEquals(precioNeto + iva, pedido.getPrecioTotalPedido(), "El precio neto del pedido no es correcto");
    }

    @Test
    void testGenerarTextoFactura() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("Cliente: Carlos\n");
        sb.append("Dirección: Calle 26\n");
        sb.append("----------------" + "\n");
        sb.append("Perro caliente" + "\n" + "            " + "12000\n");
        sb.append("Combo " + "Combo 1" + "\n");
        sb.append(" Descuento: " + "0.1" + "\n");
        sb.append("            " + "11700" + "\n");
        sb.append("    +" + "Pepinillos");
        sb.append("                " + "2500");
        sb.append("    +" + "Tocineta");
        sb.append("                " + "4000");
        sb.append("    -" + "Lechuga");
        sb.append("    -" + "Mayonesa");
        sb.append("            " + "21500" + "\n");
        sb.append("----------------" + "\n");
        sb.append("Precio Neto:  " + Integer.toString(45200) + "\n");
        sb.append("IVA:          " + Integer.toString(8588) + "\n");
        sb.append("Precio Total: " + Integer.toString(53788) + "\n");
        String facturaEsperada = sb.toString();
        String facturaObtenida = pedido.generarTextoFactura();
        System.out.println(facturaObtenida);
        assertEquals(facturaEsperada, facturaObtenida, "La factura generada no es correcta");
    }

    @Test
    void testGuardarFactura(@TempDir Path tempDir) throws Exception {
    	String contenido;
		File archivo = tempDir.resolve("facturas_Test.txt").toFile();
		archivo = tempDir.resolve("facturas_Test.txt").toFile();
		pedido.guardarFactura(archivo);
		contenido = Files.readString(archivo.toPath());
		assertEquals(pedido.generarTextoFactura(), contenido, "El contenido del archivo no coincide con la factura");
		archivo.deleteOnExit();
    }

}
