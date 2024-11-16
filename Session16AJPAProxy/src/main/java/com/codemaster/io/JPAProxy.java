package com.codemaster.io;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JPAProxy implements InvocationHandler {

    private Class<?> targetClass;
    private JdbcTemplate jdbcTemplate;

    public JPAProxy(Class<?> targetClass, JdbcTemplate jdbcTemplate) {
        this.targetClass = targetClass;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static Object createProxy(Class<?> interfaceClazz, Class<?> targetClass, JdbcTemplate jdbcTemplate) {
        return Proxy.newProxyInstance(
                interfaceClazz.getClassLoader(),
                new Class<?>[]{interfaceClazz},
                new JPAProxy(targetClass, jdbcTemplate)
        );
    }

    private Object handleInsert(Object obj) throws IllegalAccessException {
        Field[] fields = targetClass.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            columns.append(field.getName());
            placeholders.append("?");
            values[i] = field.get(obj);

            if (i < fields.length - 1) {
                columns.append(", ");
                placeholders.append(", ");
            }
        }

        String tableName = targetClass.getSimpleName().toLowerCase();

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, columns, placeholders);

        jdbcTemplate.update(insertQuery, values);

        return null;
    }

    private Object handleGet(String paramName, Object paramValue) {
        Field[] fields = targetClass.getDeclaredFields();

        String tableName = targetClass.getSimpleName().toLowerCase();

        String selectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                tableName, paramName);

        return jdbcTemplate.queryForObject(selectQuery, new Object[]{paramValue}, new RowMapper<Object>() {
            @Override
            public Object mapRow(java.sql.ResultSet rs, int rowNum) {
                try {

                    Object instance = targetClass.getDeclaredConstructor().newInstance();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        field.set(instance, rs.getObject(field.getName().toLowerCase()));
                    }
                    return instance;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("insert")) {
            return handleInsert(args[0]);
        } else if (method.getName().equals("get")) {
            return handleGet(method.getParameters()[0].getName(), args[0]);
        }
        return null;
    }
}
