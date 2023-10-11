package tasks.model.data;

import java.util.Map;
import java.util.LinkedHashMap;

public class Task {
	
	private String group;
	
	private String id;
	private String parent_id;
	private String title;
	private String description;
	private String start_date;
	private String end_date;
	
	private String startday = "01";
	private String startmonth = "01";
	private String startyear = "1900";
	
	private String starthour = "00";
	private String startminute = "00";
	private String startsecond = "00";
	
	
	private String endday = "01";
	private String endmonth = "01";
	private String endyear = "1900";
	
	private String endhour = "00";
	private String endminute = "00";
	private String endsecond = "00";
	
	
	
	public void fillWithMap(Map<String, Object> task) {
		for (String key:task.keySet()) {
			switch (key) {
			case "group": 
				setGroup((String)task.get(key));
				break;
			case "id": 
				setId((String)task.get(key));
				break;
			case "parent_id": 
				setParent_id((String)task.get(key));
				break;
			case "title": 
				setTitle((String)task.get(key));
				break;
			case "description": 
				setDescription((String)task.get(key));
				break;
				
			case "startday": 
				setStartday((String)task.get(key));
				break;
			case "startmonth":
				setStartmonth((String)task.get(key));
                break;
			case "startyear":
				setStartyear((String)task.get(key));
                break;
                
			case "starthour": 
				setStarthour((String)task.get(key));
				break;
			case "startminute":
				setStartminute((String)task.get(key));
                break;
			case "startsecond":
				setStartsecond((String)task.get(key));
                break;
                
                
			case "endday": 
				setEndday((String)task.get(key));
				break;
			case "endmonth":
				setEndmonth((String)task.get(key));
                break;
			case "endyear":
				setEndyear((String)task.get(key));
                break;
                
			case "endhour": 
				setEndhour((String)task.get(key));
				break;
			case "endminute":
				setEndminute((String)task.get(key));
                break;
			case "endsecond":
				setEndsecond((String)task.get(key));
                break;
			}
		}
		// format: 2023-09-04T18:30:00
		setStart_date(startyear+ "-" + startmonth + "-" + startday + "T" + starthour + ":" + startminute + ":" + startsecond);
		setEnd_date(endyear+ "-" + endmonth + "-" + endday + "T"+ endhour + ":" + endminute + ":" + endsecond);
	}

	
	public Map<String, Object> getMap(){
		Map<String, Object> newMap = new LinkedHashMap<String, Object>();
		
		
		newMap.put("id", id);
		newMap.put("parent_id", parent_id);
		newMap.put("group", group);
		
		newMap.put("title", title);
		newMap.put("description", description);
		
		newMap.put("start-date", start_date);
		newMap.put("startyear", startyear);
		newMap.put("startmonth", startmonth);
		newMap.put("startday", startday);
		newMap.put("starthour", starthour);
		newMap.put("startminute", startminute);
		newMap.put("startsecond", startsecond);
		
		newMap.put("end-date", end_date);
		newMap.put("endyear", endyear);
		newMap.put("endmonth", endmonth);
		newMap.put("endday", endday);
		newMap.put("endhour", endhour);
		newMap.put("endminute", endminute);
		newMap.put("endsecond", endsecond);
		
		
		return newMap;
		
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public 	String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

	

	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getStart_date() {
		return start_date;
	}



	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}



	public String getEnd_date() {
		return end_date;
	}



	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	public String getStartday() {
		return startday;
	}


	public void setStartday(String startday) {
		this.startday = startday;
	}


	public String getStartmonth() {
		return startmonth;
	}


	public void setStartmonth(String startmonth) {
		this.startmonth = startmonth;
	}


	public String getStartyear() {
		return startyear;
	}


	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}


	public String getStarthour() {
		return starthour;
	}


	public void setStarthour(String starthour) {
		this.starthour = starthour;
	}


	public String getStartminute() {
		return startminute;
	}


	public void setStartminute(String startminute) {
		this.startminute = startminute;
	}


	public String getStartsecond() {
		return startsecond;
	}


	public void setStartsecond(String startsecond) {
		this.startsecond = startsecond;
	}


	public String getEndday() {
		return endday;
	}


	public void setEndday(String endday) {
		this.endday = endday;
	}


	public String getEndmonth() {
		return endmonth;
	}


	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}


	public String getEndyear() {
		return endyear;
	}


	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}


	public String getEndhour() {
		return endhour;
	}


	public void setEndhour(String endhour) {
		this.endhour = endhour;
	}


	public String getEndminute() {
		return endminute;
	}


	public void setEndminute(String endminute) {
		this.endminute = endminute;
	}


	public String getEndsecond() {
		return endsecond;
	}


	public void setEndsecond(String endsecond) {
		this.endsecond = endsecond;
	}



	
	
	
	
	
	
}
