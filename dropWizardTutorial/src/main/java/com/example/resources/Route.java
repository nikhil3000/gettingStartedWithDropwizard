package com.example.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.example.core.User;
import com.example.db.MyDAO;
import com.google.gson.Gson;

import org.jdbi.v3.core.Jdbi;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
public class Route {

    MyDAO dao;

    public Route(Jdbi jdbi) {
        dao = jdbi.onDemand(MyDAO.class);
        try {
            dao.testQuery();
        } catch (Exception e) {
            dao.createUserTable();
        }
    }

    @POST
    @Path("/post")
    @Timed
    @Consumes({ MediaType.APPLICATION_JSON })
    public boolean setData(User user) {
        dao.insert(user.getid(), user.getName());
        return true;
    }

    @GET
    @Path("/get/{id}")
    public String getDataById(@PathParam("id") Integer id) {
        String name = dao.findNameById(id);
        return name;
    }

    @GET
    @Path("/get")
    // @Produces(MediaType.APPLICATION_JSON)
    public String getAllData() {
        List<String> list = dao.getAllNames();
        String str = new Gson().toJson(list);
        return str;
    }

    @GET
    @Path("/test")
    public String getData() {
        String s = "Hello world";
        return s;
    }

}