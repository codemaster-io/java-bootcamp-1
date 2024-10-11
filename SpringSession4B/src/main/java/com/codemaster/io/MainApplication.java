package com.codemaster.io;

import com.codemaster.io.filters.AuthenticationFilter;
import com.codemaster.io.filters.LoggingFilter;
import com.codemaster.io.filters.UserContextFilter;
import com.codemaster.io.litespring.ApplicationContext;
import com.codemaster.io.litespring.LiteSpringApplication;
import com.codemaster.io.litespring.annotation.PackageScan;
import jakarta.servlet.Filter;

import java.util.ArrayList;
import java.util.List;


@PackageScan(scanPackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws Exception {

        List<Filter> filterList = new ArrayList<>();
        filterList.add(new AuthenticationFilter());
        filterList.add(new LoggingFilter());
        filterList.add(new UserContextFilter());

//        ApplicationContext applicationContext = LiteSpringApplication.run(MainApplication.class);

        ApplicationContext applicationContext = LiteSpringApplication.run(MainApplication.class, filterList);

    }
}


