package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class ComboTest {


    private ArrayList<Combo> combos;

    @BeforeEach
    void setUp() throws Exception {
    	combos = new ArrayList<Combo>();
        ArrayList<ProductoMenu> productos1 = new ArrayList<ProductoMenu>();
        ArrayList<ProductoMenu> productos2 = new ArrayList<ProductoMenu>();
        
        productos1.add(new ProductoMenu("Hamburguesa grande", 12000));
        productos1.add(new ProductoMenu("Papas en casco", 5000));
        productos1.add(new ProductoMenu("Manzana postobón", 4000)); //21000
        
        Combo combo1 = new Combo("Combo hamburguesa", 0.1, productos1);
        combos.add(combo1);
        
        productos2.add(new ProductoMenu("Perro chili", 10000));
        productos2.add(new ProductoMenu("Hamburguesa grande", 12000));
        productos2.add(new ProductoMenu("Papas", 5000));
        productos2.add(new ProductoMenu("Papas", 5000));
        productos2.add(new ProductoMenu("Gaseosa", 3000));
        productos2.add(new ProductoMenu("Gaseosa", 3000)); //38000
        
        Combo combo2 = new Combo("Combo parceros", 0.2, productos2);
        combos.add(combo2);
    }

    @Test
    void testGetNombre() throws Exception {
        assertEquals("Combo hamburguesa", combos.get(0).getNombre(), "El nombre del combo no es correcto");
        assertEquals("Combo parceros", combos.get(1).getNombre(), "El nombre del combo no es correcto");
    }

    @Test
    void testGetPrecio() throws Exception {
        assertEquals(18900, combos.get(0).getPrecio(), "El precio del combo no es correcto");
        assertEquals(30400, combos.get(1).getPrecio(), "El precio del combo no es correcto");
    }

    @Test
    void testGenerarTextoFactura() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("Combo Combo hamburguesa\n");
        sb.append(" Descuento: 0.1\n");
        sb.append("            18900\n");
        assertEquals(sb.toString(), combos.get(0).generarTextoFactura(),
                "El texto de la factura no es correcto para Combo 1");

        sb = new StringBuffer();
        sb.append("Combo Combo parceros\n");
        sb.append(" Descuento: 0.2\n");
        sb.append("            30400\n");
        assertEquals(sb.toString(), combos.get(1).generarTextoFactura(),
                "El texto de la factura no es correcto para Combo 2");
    }
 }


