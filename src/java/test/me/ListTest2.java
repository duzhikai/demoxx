package test.me;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author qzz
 */
public class ListTest2 {

    public static void main(String[] args) {
        mergeList();
    }
    /**
     * @Description: 合并两个list<map>,并将userId相同的其它属性合并
     * @Title: mergeList
     * @param: @return
     * @return: List<Map<String,Object>>
     * @throws
     */
    public static List<Ticket> mergeList(){
        List<Ticket> list1 = new ArrayList<>();
        Ticket data=new Ticket();
        data.setTicketId("100001");
        data.setTicketName("唐僧");
        list1.add(data);

        data=new Ticket();
        data.setTicketId("100002");
        data.setTicketName("八戒");
        list1.add(data);

        List<Ticket> list2 = new ArrayList<>();
        data=new Ticket();
        data.setTicketId("100001");
        data.setBatchId("5");
        list2.add(data);

        data=new Ticket();
        data.setTicketId("100002");
        data.setBatchId("1");
        list2.add(data);

        data=new Ticket();
        data.setTicketId("100003");
        data.setBatchId("1");
        list2.add(data);


        //使用stream流把list1合并到list2集合中，根据ticketId属性
        List<Ticket> list = list1.stream().filter(m -> {
            Ticket ticket = list2.stream().filter(m1 -> Objects.equals(m1.getTicketId(), m.getTicketId())).findFirst().orElse(null);
            return "5".equals(ticket.getBatchId());
        }).collect(Collectors.toList());

        for(Ticket ticket:list){
            System.out.println(ticket.getTicketId()+","+ticket.getTicketName()+","+ticket.getBatchId());
        }
        return list;
    }
}

