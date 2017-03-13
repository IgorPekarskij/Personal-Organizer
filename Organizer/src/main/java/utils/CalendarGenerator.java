package utils;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Encoding;
import net.fortuna.ical4j.model.parameter.FmtType;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.*;
import objects.Task;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CalendarGenerator {

    public static Calendar generateCalendar (List<Task> tasks) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//IgorPekarskij"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        for (Task task : tasks) {
            LocalDate startD = ConvertData.convertStringToLocalDate(task.getStartDate());
            String startMin = ConvertData.getMin(task.getStartTime());
            String startHour = ConvertData.getHour(task.getStartTime());
            LocalDate endD = ConvertData.convertStringToLocalDate(task.getEndDate());
            String endMin = ConvertData.getMin(task.getEndTime());
            String endHour = ConvertData.getHour(task.getEndTime());

            TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
            TimeZone timezone = registry.getTimeZone("Europe/Minsk");
            VTimeZone tz = timezone.getVTimeZone();
            java.util.Calendar startDate = new GregorianCalendar();
            startDate.setTimeZone(timezone);
            startDate.set(java.util.Calendar.YEAR, startD.getYear());
            startDate.set(java.util.Calendar.MONTH, startD.getMonthValue() - 1);
            startDate.set(java.util.Calendar.DAY_OF_MONTH, startD.getDayOfMonth());
            startDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
            startDate.set(java.util.Calendar.MINUTE, Integer.parseInt(startMin));
            startDate.set(java.util.Calendar.SECOND, 0);

            java.util.Calendar endDate = new GregorianCalendar();
            endDate.setTimeZone(timezone);
            endDate.set(java.util.Calendar.YEAR, endD.getYear());
            endDate.set(java.util.Calendar.MONTH, endD.getMonthValue() - 1);
            endDate.set(java.util.Calendar.DAY_OF_MONTH, endD.getDayOfMonth());
            endDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
            endDate.set(java.util.Calendar.MINUTE, Integer.parseInt(endMin));
            endDate.set(java.util.Calendar.SECOND, 0);

            String eventName = task.getName();
            DateTime start = new DateTime(startDate.getTime());
            DateTime end = new DateTime(endDate.getTime());
            Description description = new Description(task.getDescription());
            Status status = new Status();
            if (task.isCompleted()) {
                status.setValue(Property.COMPLETED);
            } else {
                status.setValue(Property.ACTION);
            }
            ParameterList params = new ParameterList();
            params.add(Value.BINARY);
            params.add(Encoding.BASE64);
            Attach attach = new Attach(params, task.getTaskImage());
            attach.getParameters().add(new FmtType("image/jpeg"));
            VEvent meeting = new VEvent(start, end, eventName);

            meeting.getProperties().add(tz.getTimeZoneId());
            meeting.getProperties().add(description);
            meeting.getProperties().add(status);
            meeting.getProperties().add(attach);
            calendar.getComponents().add(meeting);
        }
        return calendar;
    }

    public static List<List<Task>> createTasksFromIcal(Calendar calendar) {
        System.out.println("TEst");
            List<List<Task>> tasksList = new ArrayList<>();
            List<Task> tasks = new ArrayList<>();
            List<VEvent> vEvents = calendar.getComponents(Component.VEVENT);
            Task task = null;
        for (int i = 0; i < vEvents.size(); i++) {
            System.out.println(vEvents.get(i));
        }

            if (vEvents.size() <= 1000) {
                for (int i = 0; i < vEvents.size(); i++) {
                    task = parseVevent(vEvents.get(i));
                    if (task != null) {
                        tasks.add(task);
                    }
                }
                tasksList.add(tasks);
            } else {
                int preferredSize = 0;
                for (int j = 0; j < vEvents.size(); j++) {
                    task = parseVevent(vEvents.get(j));
                    if (task != null) {
                        tasks.add(task);
                    }
                    if (++preferredSize >= 1000) {
                        tasksList.add(tasks);
                        preferredSize = 0;
                    }
                }
            }
            return tasksList;
    }

    private static Task parseVevent (VEvent vEvent) {
        DtStart start = vEvent.getStartDate();
        DtEnd end = vEvent.getEndDate();
        String startT = null;
        String endT = null;
        try {
            DateTime startTime = new DateTime(start.getValue());
            DateTime endTime = new DateTime(end.getValue());
            startT = ConvertData.convertMillisecondsInHoursAndMinutes(startTime);
            endT = ConvertData.convertMillisecondsInHoursAndMinutes(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startDate = ConvertData.convertLocalDateToString(start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        String endDate = ConvertData.convertLocalDateToString(end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        String name = vEvent.getSummary() == null ? "" : vEvent.getSummary().getValue();
        String description = vEvent.getDescription() == null ? "" : vEvent.getDescription().getValue();
        boolean status = vEvent.getStatus() == null ? false : vEvent.getStatus().getValue().equals("COMPLETED") ? true: false;
        Task newTask = new Task();
        newTask.setName(name);
        newTask.setDescription(description);
        newTask.setCompleted(status);
        newTask.setStartDate(startDate);
        newTask.setStartTime(startT);
        newTask.setEndDate(endDate);
        newTask.setEndTime(endT);
        return newTask;
    }
}
