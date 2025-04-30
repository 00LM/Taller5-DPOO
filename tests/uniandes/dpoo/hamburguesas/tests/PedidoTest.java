package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class PedidoTest {

	private Pedido pedido;

    @BeforeEach
    void setUp() throws Exception {
        pedido = new Pedido("Juan", "Calle 123");
        pedido.agregarProducto(new ProductoMenu("Hamburguesa", 20000));

        ArrayList<ProductoMenu> productosCombo = new ArrayList<ProductoMenu>();
        productosCombo.add(new ProductoMenu("Perro caliente", 12000));
        pedido.agregarProducto(new Combo("Combo 12", 0.2, productosCombo));

        ArrayList<Ingrediente> agregados = new ArrayList<Ingrediente>();
        ArrayList<Ingrediente> eliminados = new ArrayList<Ingrediente>();
        agregados.add(new Ingrediente("Queso", 2000));
        agregados.add(new Ingrediente("Tocineta", 3000));
        eliminados.add(new Ingrediente("Tomate", 1000));
        eliminados.add(new Ingrediente("Lechuga", 500));
        ProductoAjustado productoAj = new ProductoAjustado(new ProductoMenu("Hamburguesa Deluxe", 25000));
        productoAj.setAgregados(agregados);
        productoAj.setEliminados(eliminados);
        pedido.agregarProducto(productoAj);
    }

    @Test
    void testGetIDPedido() throws Exception {
        assertEquals(1, pedido.getIdPedido(), "El ID del pedido no es correcto");
    }

    @Test
    void testGetNombreCliente() throws Exception {
        assertEquals("Juan", pedido.getNombreCliente(), "El nombre del cliente no es correcto");
    }

    @Test
    void testGetPrecioTotalPedido() throws Exception {
        int precioNeto = (int) (20000 + (12000 - (12000 * 0.2)) + 25000 + 3000 + 2000);
        int iva = (int) (precioNeto * 0.19);
        assertEquals(precioNeto + iva, pedido.getPrecioTotalPedido(), "El precio neto del pedido no es correcto");
    }

    @Test
    void testGenerarTextoFactura() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("Cliente: Juan\n");
        sb.append("Dirección: Calle 123\n");
        sb.append("-----------------" + "\n");
        sb.append("Hamburguesa" + "\n" + "            " + "20000\n");
        sb.append("Combo " + "Combo 12" + "\n");
        sb.append(" Descuento: " + "0.2" + "\n");
        sb.append("            " + "9600" + "\n");
        sb.append("Hamburguesa Deluxe");
        sb.append("    +" + "Queso");
        sb.append("                " + "2000");
        sb.append("    +" + "Tocineta");
        sb.append("                " + "3000");
        sb.append("    -" + "Tomate");
        sb.append("    -" + "Lechuga");
        sb.append("            " + "30000" + "\n");
        sb.append("-----------------" + "\n");
        sb.append("Precio neto: " + Integer.toString(59600) + "\n");
        sb.append("IVA:          " + Integer.toString(11324) + "\n");
        sb.append("Precio total: " + Integer.toString(70924) + "\n");
        String facturaEsperada = sb.toString();
        String facturaObtenida = pedido.generarTextoFactura();
        assertEquals(facturaEsperada, facturaObtenida, "La factura generada no es correcta");
    }

    @Test
    void testGuardarFactura() throws Exception {
        String directorio = System.getProperty("user.dir") + "/facturas/facturaTest.txt";
        File file = new File(directorio);
        pedido.guardarFactura(file);
        String facturaEsperada = pedido.generarTextoFactura();
        StringBuilder constructorString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineaActual;
            while ((lineaActual = br.readLine()) != null) {
                constructorString.append(lineaActual).append("\n");
            }
        }
        String facturaObtenida = constructorString.toString();
        assertEquals(facturaEsperada, facturaObtenida, "La factura guardada no es correcta");
        file.delete();
    }

}
