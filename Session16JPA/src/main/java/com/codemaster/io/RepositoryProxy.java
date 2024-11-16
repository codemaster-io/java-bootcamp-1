package com.codemaster.io;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryProxy implements InvocationHandler {

    private JdbcTemplate jdbcTemplate;

    private Class<?> targetClazz;

    public RepositoryProxy(Class<?> targetClazz, JdbcTemplate jdbcTemplate) {
        this.targetClazz = targetClazz;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static Object getProxyInstance(Class<?> interfaceClazz, Class<?> targetClazz, JdbcTemplate jdbcTemplate) {
        return Proxy.newProxyInstance(interfaceClazz.getClassLoader(), new Class<?>[] {interfaceClazz},
                new RepositoryProxy(targetClazz, jdbcTemplate));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method.getName() = " + method.getName());
        System.out.println("targetClazz = " + targetClazz.getSimpleName());

        if(method.getName().equals("insert")) return handleInsert(args);
        if(method.getName().equals("get")) return handleGet(args, "id");
        if(method.getName().equals("getByTitle")) return handleGet(args, "title");
        if(method.getName().equals("getByName")) return handleGet(args, "name");

        return null;
    }

    private Object handleInsert(Object[] args) throws IllegalAccessException {

        String tableName = targetClazz.getSimpleName().toLowerCase();
        Field[] fields = targetClazz.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeHolders = new StringBuilder();
        Object data = args[0];
        Object[] values = new Object[fields.length];

        for(int i=0; i<fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            values[i] = field.get(data);
            columns.append(field.getName());
            placeHolders.append("?");
            if(i < fields.length-1) {
                columns.append(", ");
                placeHolders.append(", ");
            }
        }


        String sqlQuery = String.format("insert into %s (%s) values(%s)",
                tableName, columns, placeHolders);

        jdbcTemplate.update(sqlQuery, values);

        return null;

    }

    private Object handleGet(Object[] args, String paramName) {

        String tableName = targetClazz.getSimpleName().toLowerCase();

        String sqlQuery = String.format("select * from %s where %s = ?", tableName, paramName);
        Field[] fields = targetClazz.getDeclaredFields();

        Object object = jdbcTemplate.queryForObject(sqlQuery, new Object[]{args[0]}, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    Object instance = targetClazz.getDeclaredConstructor().newInstance();
                    for(Field field : fields) {
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

        return object;

    }
}
