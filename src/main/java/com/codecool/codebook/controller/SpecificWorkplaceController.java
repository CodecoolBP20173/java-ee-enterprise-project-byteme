package com.codecool.codebook.controller;

import com.codecool.codebook.config.TemplateEngineUtil;
import com.codecool.codebook.model.Student;
import com.codecool.codebook.sql.Queries;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SpecificWorkplaceController extends HttpServlet {
    Queries queries;

    public SpecificWorkplaceController(Queries queries) {
        this.queries = queries;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long pathParameter = Long.valueOf(req.getParameter("id"));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("workplace", queries.getWorkplace(pathParameter));
        context.setVariable("jobs", queries.getAllJobsInWorkplace(pathParameter));
        context.setVariable("students", queries.getAllStudentInWorkplace(pathParameter));
        engine.process("workplace.html", context, resp.getWriter());

    }
}