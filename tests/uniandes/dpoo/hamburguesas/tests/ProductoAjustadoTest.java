package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class ProductoAjustadoTest {
	
	private ProductoMenu producto1;
	private ProductoAjustado productoAjustado1;
	private Ingrediente ingrediente1;
	private Ingrediente ingrediente2;
	private Ingrediente ingrediente3;
	private Ingrediente ingrediente4;
	private Ingrediente ingrediente5;
	private Ingrediente ingrediente6;

	@BeforeEach
	void setUp() throws Exception
    {
		ingrediente1 = new Ingrediente("Tomate", 2000);
		ingrediente2 = new Ingrediente("Lechuga", 2500);
		ingrediente3 = new Ingrediente("Queso", 3000);
		ingrediente4 = new Ingrediente("Mayonesa", 1000);
		ingrediente5 = new Ingrediente("Pepinillos", 1000);
		ingrediente6 = new Ingrediente("Tocineta", 1000);
		producto1 = new ProductoMenu("Hamburguesa grande", 10000);
		productoAjustado1 = new ProductoAjustado(producto1);
		productoAjustado1.anadirAgregados(ingrediente1); //precio : 2000
		productoAjustado1.anadirAgregados(ingrediente2); // precio : 2500
		productoAjustado1.anadirEliminados(ingrediente3); // precio : 3000
		productoAjustado1.anadirEliminados(ingrediente4); //precio : 1000
		
    }
	
	@AfterEach
	void tearDown() throws Exception{
	}
	
	@Test
	void testgetNombre() throws Exception
    {
        assertEquals( "Hamburguesa grande", productoAjustado1.getNombre( ), "El nombre del producto ajustado no es el esperado." );
    }
	
	@Test
	void testgetPrecio() throws Exception
    {
        assertEquals( 14500, productoAjustado1.getPrecio( ), "El precio del producto ajustado no es el esperado." );
    }
	
	@Test
	void testGetAgregados() throws Exception {
         ArrayList<Ingrediente> agregados = productoAjustado1.getAgregados();
         assertEquals(2, agregados.size(), "El número de ingredientes agregados no es correcto");
         assertEquals("Tomate", agregados.get(0).getNombre(),
                         "El nombre del ingrediente agregado no es correcto");
         assertEquals(2000, agregados.get(0).getCostoAdicional(),
                         "El precio del ingrediente agregado no es correcto");
         assertEquals("Lechuga", agregados.get(1).getNombre(),
                         "El nombre del ingrediente agregado no es correcto");
         assertEquals(2500, agregados.get(1).getCostoAdicional(),
                         "El precio del ingrediente agregado no es correcto");
	}
	
	@Test
	void testGetEliminados() throws Exception {
        ArrayList<Ingrediente> eliminados = productoAjustado1.getEliminados();
        assertEquals(2, eliminados.size(), "El número de ingredientes agregados no es correcto");
        assertEquals("Queso", eliminados.get(0).getNombre(),
                        "El nombre del ingrediente agregado no es correcto");
        assertEquals(3000, eliminados.get(0).getCostoAdicional(),
                        "El precio del ingrediente agregado no es correcto");
        assertEquals("Mayonesa", eliminados.get(1).getNombre(),
                        "El nombre del ingrediente agregado no es correcto");
        assertEquals(1000, eliminados.get(1).getCostoAdicional(),
                        "El precio del ingrediente agregado no es correcto");
	}
	
	@Test
	void testanadirAgregados() throws Exception {
		productoAjustado1.anadirAgregados(ingrediente5);
		ArrayList<Ingrediente> agregados = productoAjustado1.getAgregados();
		assertEquals(3, agregados.size(), "El número de ingredientes agregados no es correcto");
        assertEquals("Pepinillos", agregados.get(2).getNombre(),
                        "El nombre del ingrediente agregado no es correcto");
        assertEquals(1000, agregados.get(2).getCostoAdicional(),
                        "El precio del ingrediente agregado no es correcto");
	}
	
	@Test
	void testanadirEliminados() throws Exception {
		productoAjustado1.anadirEliminados(ingrediente6);
		ArrayList<Ingrediente> eliminados = productoAjustado1.getEliminados();
		assertEquals(3, eliminados.size(), "El número de ingredientes agregados no es correcto");
        assertEquals("Tocineta", eliminados.get(2).getNombre(),
                        "El nombre del ingrediente agregado no es correcto");
        assertEquals(1000, eliminados.get(2).getCostoAdicional(),
                        "El precio del ingrediente agregado no es correcto");
	}
	
	@Test
	void testGenerarTextoFactura() throws Exception {
	    StringBuffer sb = new StringBuffer();
	    sb.append("    +Tomate                2000");
	    sb.append("    +Lechuga                2500");
	    sb.append("    -Queso");
	    sb.append("    -Mayonesa");
	    sb.append("            14500\n"); 
	    String facturaEsperada = sb.toString();
	    String facturaGenerada = productoAjustado1.generarTextoFactura();
	    assertEquals(facturaEsperada, facturaGenerada, "La factura generada no es correcta");
	}

	
	

}
