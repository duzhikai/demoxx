package test.me;

import org.example.Ticket;

import java.util.ArrayList;
import java.util.Comparator;

public class Demo3 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        ticket.setTicketId("");
        ticket.setTicketName("");
        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("22");
        ticket2.setTicketName("5");
        Ticket ticket3 = new Ticket();
        ticket3.setTicketId("22");
        ticket3.setTicketName("1");
        ArrayList<Ticket> list = new ArrayList<>();
        list.add(ticket);
        list.add(ticket2);
        list.add(ticket3);

        Comparator<Ticket> ticketComparator = Comparator.comparing(Ticket::getTicketId, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparingInt(o -> o.getTicketName().charAt(0));
        list.sort(ticketComparator);
        System.out.println(list);

    }
}
