package servlet;

import service.EventService;
import model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/event")
public class EventServlet extends HttpServlet {
    private EventService eventService = new EventService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateEvent(request, response);
        } else if ("delete".equals(action)) {
            deleteEvent(request, response);
        } else {
            addEvent(request, response);
        }
    }

    private void addEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventName = request.getParameter("eventName");
        String date = request.getParameter("date");
        String location = request.getParameter("location");
        String organizer = request.getParameter("organizer");

        eventService.createEvent(eventName, date, location, organizer);
        response.sendRedirect("event.html");
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String eventName = request.getParameter("eventName");
            String date = request.getParameter("date");
            String location = request.getParameter("location");
            String organizer = request.getParameter("organizer");

            Event event = new Event(eventName, date, location, organizer);
            eventService.updateEvent(event);
            response.getWriter().write("Event Updated Successfully!");
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            eventService.deleteEvent(eventId);
            response.getWriter().write("Event Deleted Successfully!");
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Event> events = eventService.getAllEvents();
        request.setAttribute("events", events);
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }
}
