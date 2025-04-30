package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {
	private ProductoMenu producto1;
	
	@BeforeEach
	void setUp( ) throws Exception
    {
		producto1 = new ProductoMenu("Hamburguesa grande", 10000);
    }
	
	@AfterEach
	void tearDown( ) throws Exception{
	}
	
	@Test
	void testgetNombre( )
    {
        assertEquals( "Hamburguesa grande", producto1.getNombre( ), "El nombre del producto del menú no es el esperado." );
    }
	
	@Test
	void testgetPrecioBase( )
    {
        assertEquals( 10000, producto1.getPrecio(), "El precio del producto del menú no es el esperado." );
    }
	
	@Test
	void testGenerarTextoFactura() throws Exception{
		assertEquals("Hamburguesa grande"+"\n"+"            "+"10000\n",producto1.generarTextoFactura(),"La factura generada para el producto del menú no es correcta");
	}
 

}
