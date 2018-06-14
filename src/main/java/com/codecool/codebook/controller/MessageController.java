package com.codecool.codebook.controller;

import com.codecool.codebook.Password;
import com.codecool.codebook.config.TemplateEngineUtil;
import com.codecool.codebook.model.Message;
import com.codecool.codebook.model.Student;
import com.codecool.codebook.sql.Queries;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.codecool.codebook.sql.Queries;
import org.thymeleaf.exceptions.TemplateProcessingException;

@WebServlet(urlPatterns = {"/message/*"})
public class MessageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        HttpSession session = req.getSession();
        if (session.getAttribute("id") != null){
            Long receiverId = Long.valueOf(req.getParameter("id"));
            Long senderId = new Long((int) session.getAttribute("userID"));

            Student senderStudent = Queries.getStudent(senderId);
            Student recieverStudent = Queries.getStudent(receiverId);
            context.setVariable("student1", senderStudent);
            context.setVariable("student2", recieverStudent);
            context.setVariable("messages", Queries.getAllMessageBetweenUsers(senderId, receiverId));
            engine.process("message.html", context, resp.getWriter());
        } else {
            engine.process("messageerror.html", context, resp.getWriter());

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long receiverId = Long.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession();
        Long senderId = new Long((int) session.getAttribute("userID"));
        String textMessage = request.getParameter("message");
        Message message = new Message(textMessage);
        message.setReceiverStudent(Queries.getStudent(receiverId));
        message.setSenderStudent(Queries.getStudent(senderId));
        Queries.addNewMessage(message);

        response.sendRedirect("/message/?id=" + receiverId);
    }
}