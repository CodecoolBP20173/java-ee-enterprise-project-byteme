package com.codecool.codebook.controller;

import com.codecool.codebook.config.TemplateEngineUtil;
import com.codecool.codebook.sql.Queries;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import org.thymeleaf.exceptions.TemplateProcessingException;

public class IndexController extends HttpServlet {

    Queries queries;

    public IndexController(Queries queries) {
        this.queries = queries;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List students = queries.getAllStudentInfo();
        if (session.getAttribute("userID") != null) {
            try {
                Long id = new Long((int) session.getAttribute("userID"));
                context.setVariable("userName", queries.getStudent(id));
            } catch (NullPointerException e) {
                System.err.println("Error caught: " + e.toString() + " in IndexController.doGet()");
            }
        }
        context.setVariable("students", students);

        try {
            engine.process("index.html", context, resp.getWriter());
        } catch (TemplateProcessingException e) {
            resp.resetBuffer();
            context.clearVariables();
            context.setVariable("traceback", e);
            engine.process("error.html", context, resp.getWriter());
            e.printStackTrace();
        }
    }

}