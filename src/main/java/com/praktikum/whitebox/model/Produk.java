package com.praktikum.whitebox.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdukTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Constructor dengan argumen
        Produk p1 = new Produk("P001", "Produk A", "Kategori A", 1000.0, 10, 2);

        assertEquals("P001", p1.getKode());
        assertEquals("Produk A", p1.getNama());
        assertEquals("Kategori A", p1.getKategori());
        assertEquals(1000.0, p1.getHarga());
        assertEquals(10, p1.getStok());
        assertEquals(2, p1.getStokMinimum());
        assertTrue(p1.isAktif());

        // Uji setter
        p1.setKode("P002");
        p1.setNama("Produk B");
        p1.setKategori("Kategori B");
        p1.setHarga(2000.0);
        p1.setStok(20);
        p1.setStokMinimum(5);
        p1.setAktif(false);

        assertEquals("P002", p1.getKode());
        assertEquals("Produk B", p1.getNama());
        assertEquals("Kategori B", p1.getKategori());
        assertEquals(2000.0, p1.getHarga());
        assertEquals(20, p1.getStok());
        assertEquals(5, p1.getStokMinimum());
        assertFalse(p1.isAktif());

        // Constructor default
        Produk p2 = new Produk();
        assertNull(p2.getKode());
        assertNull(p2.getNama());
        assertNull(p2.getKategori());
        assertEquals(0.0, p2.getHarga());
        assertEquals(0, p2.getStok());
        assertEquals(0, p2.getStokMinimum());
        assertFalse(p2.isAktif()); // default boolean = false
    }

    @Test
    void testBusinessLogicStok() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 1000, 5, 2);

        // isStokHabis
        produk.setStok(0);
        assertTrue(produk.isStokHabis());
        produk.setStok(3);
        assertFalse(produk.isStokHabis());

        // isStokMenipis
        produk.setStok(2);
        assertTrue(produk.isStokMenipis());
        produk.setStok(10);
        assertFalse(produk.isStokMenipis());

        // isStokAman
        produk.setStok(10);
        produk.setStokMinimum(5);
        assertTrue(produk.isStokAman());
        produk.setStok(3);
        assertFalse(produk.isStokAman());
    }

    @Test
    void testKurangiStokValid() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 1000, 10, 2);
        produk.kurangiStok(3);
        assertEquals(7, produk.getStok());
    }

    @Test
    void testKurangiStokInvalid() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 1000, 5, 2);

        // jumlah <= 0
        assertThrows(IllegalArgumentException.class, () -> produk.kurangiStok(0));
        assertThrows(IllegalArgumentException.class, () -> produk.kurangiStok(-1));

        // jumlah > stok
        assertThrows(IllegalArgumentException.class, () -> produk.kurangiStok(10));
    }

    @Test
    void testTambahStok() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 1000, 5, 2);
        produk.tambahStok(5);
        assertEquals(10, produk.getStok());

        assertThrows(IllegalArgumentException.class, () -> produk.tambahStok(0));
        assertThrows(IllegalArgumentException.class, () -> produk.tambahStok(-2));
    }

    @Test
    void testHitungTotalHarga() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 2000, 10, 2);

        assertEquals(4000.0, produk.hitungTotalHarga(2));

        assertThrows(IllegalArgumentException.class, () -> produk.hitungTotalHarga(0));
        assertThrows(IllegalArgumentException.class, () -> produk.hitungTotalHarga(-1));
    }

    @Test
    void testEqualsAndHashCode() {
        Produk p1 = new Produk("P001", "Produk A", "Kategori A", 1000, 10, 2);
        Produk p2 = new Produk("P001", "Produk B", "Kategori B", 2000, 5, 1);
        Produk p3 = new Produk("P002", "Produk C", "Kategori C", 3000, 1, 0);

        // objek sama
        assertTrue(p1.equals(p1));

        // null
        assertFalse(p1.equals(null));

        // beda tipe
        assertFalse(p1.equals("string"));

        // kode sama → equals true
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());

        // kode beda → equals false
        assertFalse(p1.equals(p3));
    }

    @Test
    void testToString() {
        Produk produk = new Produk("P001", "Produk A", "Kategori A", 1000, 10, 2);
        String str = produk.toString();

        assertTrue(str.contains("P001"));
        assertTrue(str.contains("Produk A"));
        assertTrue(str.contains("Kategori A"));
        assertTrue(str.contains("1000.0"));
        assertTrue(str.contains("10"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("true"));
    }
}
