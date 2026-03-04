package com.example;

import java.util.List;

import org.hibernate.Session;

import com.example.entity.Product;
import com.example.util.HibernateUtil;

public class MainApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        /* -------------------------------------------------
         3. SORT PRODUCTS BY PRICE
        ------------------------------------------------- */

        // a) Price Ascending
        System.out.println("\n--- Products Sorted by Price (ASC) ---");
        List<Product> priceAsc =
                session.createQuery("FROM Product p ORDER BY p.price ASC", Product.class)
                       .list();

        for (Product p : priceAsc) {
            System.out.println(p.getName() + " | " + p.getPrice());
        }

        // b) Price Descending
        System.out.println("\n--- Products Sorted by Price (DESC) ---");
        List<Product> priceDesc =
                session.createQuery("FROM Product p ORDER BY p.price DESC", Product.class)
                       .list();

        for (Product p : priceDesc) {
            System.out.println(p.getName() + " | " + p.getPrice());
        }

        /* -------------------------------------------------
         4. SORT BY QUANTITY (HIGHEST FIRST)
        ------------------------------------------------- */
        System.out.println("\n--- Products Sorted by Quantity (DESC) ---");
        List<Product> qtyDesc =
                session.createQuery("FROM Product p ORDER BY p.quantity DESC", Product.class)
                       .list();

        for (Product p : qtyDesc) {
            System.out.println(p.getName() + " | " + p.getQuantity());
        }

        /* -------------------------------------------------
         5. PAGINATION
        ------------------------------------------------- */

        // a) First 3 products
        System.out.println("\n--- First 3 Products ---");
        List<Product> first3 =
                session.createQuery("FROM Product", Product.class)
                       .setFirstResult(0)
                       .setMaxResults(3)
                       .list();

        for (Product p : first3) {
            System.out.println(p.getName());
        }

        // b) Next 3 products
        System.out.println("\n--- Next 3 Products ---");
        List<Product> next3 =
                session.createQuery("FROM Product", Product.class)
                       .setFirstResult(3)
                       .setMaxResults(3)
                       .list();

        for (Product p : next3) {
            System.out.println(p.getName());
        }

        /* -------------------------------------------------
         6. AGGREGATE OPERATIONS
        ------------------------------------------------- */

        // a) Total products
        Long total =
                session.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                       .uniqueResult();
        System.out.println("\nTotal Products: " + total);

        // b) Quantity > 0
        Long available =
                session.createQuery(
                        "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0",
                        Long.class)
                       .uniqueResult();
        System.out.println("Products with Quantity > 0: " + available);

        // c) Count grouped by description
        System.out.println("\n--- Count Grouped by Description ---");
        List<Object[]> countByDesc =
                session.createQuery(
                        "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description")
                       .list();

        for (Object[] row : countByDesc) {
            System.out.println(row[0] + " : " + row[1]);
        }

        // d) Min & Max price
        Object[] minMax =
                (Object[]) session.createQuery(
                        "SELECT MIN(p.price), MAX(p.price) FROM Product p")
                        .uniqueResult();

        System.out.println("\nMin Price: " + minMax[0]);
        System.out.println("Max Price: " + minMax[1]);

        /* -------------------------------------------------
         7. GROUP BY DESCRIPTION
        ------------------------------------------------- */
        System.out.println("\n--- Products Grouped by Description ---");
        List<Object[]> groupDesc =
                session.createQuery(
                        "SELECT p.description, p.name FROM Product p GROUP BY p.description, p.name")
                       .list();

        for (Object[] row : groupDesc) {
            System.out.println(row[0] + " | " + row[1]);
        }

        /* -------------------------------------------------
         8. PRICE RANGE FILTER
        ------------------------------------------------- */
        System.out.println("\n--- Products between price 1000 and 10000 ---");
        List<Product> priceRange =
                session.createQuery(
                        "FROM Product p WHERE p.price BETWEEN 1000 AND 10000",
                        Product.class)
                       .list();

        for (Product p : priceRange) {
            System.out.println(p.getName() + " | " + p.getPrice());
        }

        /* -------------------------------------------------
         9. LIKE QUERIES
        ------------------------------------------------- */

        // a) Names starting with 'M'
        System.out.println("\n--- Names Starting with 'M' ---");
        session.createQuery("FROM Product p WHERE p.name LIKE 'M%'", Product.class)
               .list()
               .forEach(p -> System.out.println(p.getName()));

        // b) Names ending with 'e'
        System.out.println("\n--- Names Ending with 'e' ---");
        session.createQuery("FROM Product p WHERE p.name LIKE '%e'", Product.class)
               .list()
               .forEach(p -> System.out.println(p.getName()));

        // c) Names containing 'top'
        System.out.println("\n--- Names Containing 'top' ---");
        session.createQuery("FROM Product p WHERE p.name LIKE '%top%'", Product.class)
               .list()
               .forEach(p -> System.out.println(p.getName()));

        // d) Names with exact length = 5
        System.out.println("\n--- Names with Length 5 ---");
        session.createQuery("FROM Product p WHERE LENGTH(p.name) = 5", Product.class)
               .list()
               .forEach(p -> System.out.println(p.getName()));

        session.close();
    }
}
